package nu.cliffords.android_kyee.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.afollestad.materialdialogs.MaterialDialog
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.fragment_light.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.app.App
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.interfaces.FlowsContract
import nu.cliffords.android_kyee.interfaces.LightContract
import nu.cliffords.android_kyee.presenters.FlowsPresenter
import nu.cliffords.android_kyee.presenters.LightPresenter
import nu.cliffords.android_kyee.widgets.FlowCardPlayView
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-17.
 */

class LightFragment : Fragment(), LightContract.View, FlowsContract.View {

    private var lightPresenter: LightPresenter? = null
    private var flowsPresenter: FlowsPresenter? = null

    @Inject
    fun setLightPresenter(presenter: LightPresenter) {
        this.lightPresenter = presenter
    }

    @Inject
    fun setFlowsPresenter(presenter: FlowsPresenter) {
        this.flowsPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).component.inject(this)
        lightPresenter?.bind(this)
        flowsPresenter?.bind(this)
    }

    override fun onStop() {
        super.onStop()
        lightPresenter?.unbind()
        flowsPresenter?.unbind()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_light, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Light"
        setupGUI()
        if(arguments?.getString("id") != null) {
            val lightId = arguments.getString("id")
            val light = LightManager.instance.getLightFromId(lightId)
            if(light != null)
                setLight(light)
        }
        flowsPresenter?.getFlows()
    }

    private fun setupGUI() {
        light_card_detail_nameView.isClickable = true
        light_card_detail_nameView.setOnClickListener {
            val oldName = light_card_detail_nameView.text
            MaterialDialog.Builder(context)
                    .title("Choose device name")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("", oldName, { dialog, input ->
                        val newName = input.toString()
                        if (newName != oldName) {
                            lightPresenter?.setName(newName)
                            dialog.dismiss()
                        }
                    }).show()
        }

        light_card_detail_changeColorButton.setOnClickListener {
            val colorPickerDialog = ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Choose color")
                    .initialColor(Color.WHITE)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(10)
                    .lightnessSliderOnly()

            colorPickerDialog.setOnColorChangedListener { selectedColor ->
                lightPresenter?.setColor(selectedColor)
            }.build().show()
        }

        light_card_detail_brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(brightnessSeekBar: SeekBar?, brightness: Int, p2: Boolean) { }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                val brightness = p0!!.progress
                lightPresenter?.setBrightness(brightness)
            }
        })

        light_card_detail_toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            lightPresenter?.setPower(isChecked)
        }
    }

    private fun setLight(light: Light) {
        lightPresenter?.setLight(light)
    }

    override fun setName(name: String) {
        light_card_detail_nameView.text = name
    }

    override fun setBrightness(brightness: Int) {
        light_card_detail_brightnessSeekBar.progress = brightness
    }

    override fun setColor(color: Int) {
        light_card_detail_changeColorButton.setButtonBackground(color)
    }

    override fun setPower(powered: Boolean) {
        if(light_card_detail_toggleSwitch != null)
            light_card_detail_toggleSwitch.isChecked = powered
    }

    override fun setFlowStarted() {

    }

    override fun setFlowStopped() {

    }

    //Flows

    override fun setFlows(flows: List<Flow>) {
        flows.forEach { flow ->
            val flowPlayView = FlowCardPlayView(context, flow, {
                //If user signals they want this flow played or stopped
                lightPresenter?.startColorFlow(flow.count, Light.FlowAction.fromInt(flow.action)!!, flow.flow_states!!.flowStates!!)
            }, {
                lightPresenter?.stopColorFlow()
            })
            light_card_detail_flowList.addView(flowPlayView)
        }
    }

    override fun openFlowDetails(flow: Flow) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFlowRemoved() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearFlows() {
        light_card_detail_flowList.removeAllViews()
    }



}