package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.afollestad.materialdialogs.MaterialDialog
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.card_light_detail_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.util.Helpers
//import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.interfaces.LightStateChangeListener

/**
 * Created by Henrik Nelson on 2017-08-20.
 */

class LightCardDetailView(context: Context) : RelativeLayout(context), LightStateChangeListener {

    var cardLight: Light? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.card_light_detail_view,this,true)
    }

    fun setLight(light:Light) {
        cardLight = light
        updateGui()


        light_card_detail_nameView.setOnClickListener {

            MaterialDialog.Builder(context)
                    .title("Choose device name")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("", cardLight!!.name, { dialog, input ->
                        val newName = input.toString()
                        if (newName != cardLight!!.name) {
                            cardLight!!.setName(newName, {
                                cardLight!!.name = newName
                                light_card_detail_nameView.text = newName
                            })
                            dialog.dismiss()
                        }
                    }).show()
        }

        light_card_detail_toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(cardLight!!.power != isChecked)
                cardLight!!.setPower(isChecked,Light.LightEffect.SMOOTH,500,{

                })
        }

        light_card_detail_brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(brightnessSeekBar: SeekBar?, brightness: Int, p2: Boolean) { }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                val brightness = p0!!.progress
                cardLight!!.setBrightness(brightness,Light.LightEffect.SUDDEN,30,{

                })
            }
        })

        light_card_detail_changeColorButton.setOnClickListener {
            val colorPickerDialog = buildColorPickerDialog()
            colorPickerDialog.setOnColorChangedListener { selectedColor ->

                val pixelHSV = FloatArray(3)
                Color.colorToHSV(selectedColor,pixelHSV)
                light.setHSV(pixelHSV[0].toInt(),(pixelHSV[1]*100).toInt(),Light.LightEffect.SMOOTH,100,{
                    light_card_detail_changeColorButton.setButtonBackground(selectedColor)
                })

            }.build().show()
        }

    }

    private fun updateGui() {

        if(cardLight == null)
            return

        //Light name
        light_card_detail_nameView.text = cardLight!!.name
        if(light_card_detail_nameView.text.isEmpty())
            light_card_detail_nameView.text = "Unknown"

        //Light toggle state
        light_card_detail_toggleSwitch.isChecked = cardLight!!.power

        //Light brightness
        light_card_detail_brightnessSeekBar.progress = cardLight!!.brightness

        //Light color
        when {
            cardLight!!.color_mode == 1 -> {
                light_card_detail_changeColorButton.setButtonBackgroundRGB(cardLight!!.rgb)
            }
            cardLight!!.color_mode == 2 -> {
                val newColor = Helpers.getRGBFromK(cardLight!!.ct)
                light_card_detail_changeColorButton.setButtonBackground(newColor)
            }
            cardLight!!.color_mode == 3 -> {
                val newColor = Helpers.HsvToColor(cardLight!!.hue.toFloat(),(cardLight!!.saturation.toFloat() / 100),3.0F)
                light_card_detail_changeColorButton.setButtonBackground(newColor)
            }
        }
        updateFlowList()
    }

    private fun updateFlowList() {
        light_card_detail_flowList.removeAllViews()
        /*val flows = FlowDatabase.getDatabase(context).flowDao().getAll()
        flows.forEach{ flow ->
            val flowPlayView = FlowCardPlayView(context,flow,{
                //If user signals they want this flow played or stopped
                cardLight!!.startColorFlow(flow.count, Light.FlowAction.fromInt(flow.action)!!,flow.flow_states!!.flowStates!!,{
                })
            },{
                cardLight!!.stopColorFlow {  }
            })
            light_card_detail_flowList.addView(flowPlayView)
        }*/
    }

    override fun onStateChanged(params: Map<String, Any>) {
        updateGui()
    }

    private fun buildColorPickerDialog(): ColorPickerDialogBuilder {
        return ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(Color.WHITE)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(10)
                .lightnessSliderOnly()
    }

}