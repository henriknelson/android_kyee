package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.card_light_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.app.App
import nu.cliffords.android_kyee.fragments.LightFragment
import nu.cliffords.android_kyee.interfaces.LightInteractor
import nu.cliffords.android_kyee.presenters.LightPresenter
import nu.cliffords.android_kyee.util.Helpers
import nu.cliffords.kyee.classes.Light
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-15.
 */

class LightCardView(context: Context) : RelativeLayout(context), LightInteractor.View {

    private var cardLight: Light? = null
    private var presenter: LightPresenter? = null

    @Inject
    fun setPresenter(presenter: LightPresenter) {
        this.presenter = presenter
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.card_light_view,this,true)
        (context.applicationContext as App).component.inject(this)
        presenter?.bind(this)
        setupGUI()
    }

    fun setupGUI() {
        cardLayout.isClickable = true
        cardLayout.setOnClickListener {
            val lightFragment: Fragment = Helpers.instanceOf<LightFragment>("id" to cardLight!!.id)
            (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, lightFragment).addToBackStack("light_fragment").commit()
        }

        light_card_changeColorButton.setOnClickListener {
            val colorPickerDialog = ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Choose color")
                    .initialColor(Color.WHITE)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(10)
                    .lightnessSliderOnly()

            colorPickerDialog.setOnColorChangedListener { selectedColor ->
                presenter?.setColor(selectedColor)
            }.build().show()
        }

        light_card_brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(brightnessSeekBar: SeekBar?, brightness: Int, p2: Boolean) { }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                val brightness = p0!!.progress
                presenter?.setBrightness(brightness)
            }
        })

        light_card_toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter?.setPower(isChecked)
        }
    }

    fun setLight(light: Light) {
        cardLight = light
        presenter?.setLight(light)
    }

    override fun setName(name: String) {
        light_card_nameView.text = name
    }

    override fun setBrightness(brightness: Int) {
        light_card_brightnessSeekBar.progress = brightness
    }

    override fun setColor(color: Int) {
        light_card_changeColorButton.setButtonBackground(color)
    }

    override fun setPower(powered: Boolean) {
        light_card_toggleSwitch.isChecked = powered
    }

    override fun setFlowStarted() {

    }

    override fun setFlowStopped() {

    }

}