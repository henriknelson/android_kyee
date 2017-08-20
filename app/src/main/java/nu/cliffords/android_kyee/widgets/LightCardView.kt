package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.kyleduo.switchbutton.SwitchButton
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.classes.Helpers
import nu.cliffords.android_kyee.fragments.LightFragment
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.interfaces.LightStateChangeListener

/**
 * Created by Henrik Nelson on 2017-08-15.
 */

class LightCardView(context: Context) : RelativeLayout(context), LightStateChangeListener {

    var cardLight: Light? = null

    var nameTextView: TextView? = null
    var changeColorButton: RoundButton? = null
    var toggleSwitch: SwitchButton? = null
    var brightnessBar: SeekBar? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.card_light_view,this,true)

        val clickLayout = rootView.findViewById<RelativeLayout>(R.id.cardLayout)
        nameTextView = rootView.findViewById(R.id.light_card_nameView)
        changeColorButton = rootView.findViewById(R.id.light_card_changeColorButton)
        toggleSwitch = rootView.findViewById(R.id.light_card_toggleSwitch)
        brightnessBar = rootView.findViewById(R.id.light_card_brightnessSeekBar)

        clickLayout.isClickable = true
        clickLayout.setOnClickListener {
            val lightFragment: Fragment = Helpers.instanceOf<LightFragment>("id" to cardLight!!.id)
            (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, lightFragment).addToBackStack("light_fragment").commit()
        }
    }

    fun setLight(light:Light) {
        cardLight = light
        updateGui()

            /*
            MaterialDialog.Builder(context)
                    .title("Choose device name")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("", cardLight!!.name, { dialog, input ->
                        val newName = input.toString()
                        if(newName != cardLight!!.name) {
                            cardLight!!.setName(newName,{
                                cardLight!!.name = newName
                                lightNameView.text = newName
                            })
                            dialog.dismiss()
                        }
                    }).show()*/

        toggleSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if(cardLight!!.power != isChecked)
                cardLight!!.setPower(isChecked,Light.LightEffect.SMOOTH,500,{

            })
        }

        brightnessBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(brightnessSeekBar: SeekBar?, brightness: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //Nuffin'
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                val brightness = p0!!.progress
                cardLight!!.setBrightness(brightness,Light.LightEffect.SUDDEN,30,{

                })
            }
        })

        changeColorButton?.setOnClickListener {
            val colorPickerDialog = buildColorPickerDialog()
            colorPickerDialog.setOnColorChangedListener { selectedColor ->
                /*val newColor = selectedColor and 0x00FFFFFF.toInt()
                cardLight!!.setRGB(newColor,Light.LightEffect.SMOOTH,100, {

                })*/
                val pixelHSV = FloatArray(3)
                Color.colorToHSV(selectedColor,pixelHSV)
                light.setHSV(pixelHSV[0].toInt(),(pixelHSV[1]*100).toInt(),Light.LightEffect.SMOOTH,100,{
                    changeColorButton?.setButtonBackground(selectedColor)
                })
            }.build().show()
        }

        this.setOnClickListener {
            //TODO
        }
    }

    private fun updateGui() {

        if(cardLight == null)
            return

        //Light name
        nameTextView?.text = cardLight!!.name
        if(nameTextView?.text!!.isEmpty())
            nameTextView?.text = "Unknown"

        //Light toggle state
        toggleSwitch?.isChecked = cardLight!!.power

        //Light brightness
        brightnessBar?.progress = cardLight!!.brightness

        when {
            cardLight!!.color_mode == 1 -> changeColorButton?.setButtonBackgroundRGB(cardLight!!.rgb)
            cardLight!!.color_mode == 2 -> {
                val newColor = Helpers.getRGBFromK(cardLight!!.ct)
                changeColorButton?.setButtonBackground(newColor)
            }
            cardLight!!.color_mode == 3 -> {
                val newColor = Helpers.HsvToColor(cardLight!!.hue.toFloat(),(cardLight!!.saturation.toFloat() / 100),3.0F)
                changeColorButton?.setButtonBackground(newColor)
            }
        }
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

    override fun onStateChanged(params: Map<String, Any>) {
        updateGui()
    }
}