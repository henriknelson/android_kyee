package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.card_light_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.classes.Helpers
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.interfaces.LightStateChangeListener
import com.afollestad.materialdialogs.MaterialDialog
import android.text.InputType

/**
 * Created by Henrik Nelson on 2017-08-15.
 */

class LightCardView(context: Context) : RelativeLayout(context), LightStateChangeListener {

    var cardLight: Light? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.card_light_view,this,true)
    }

    fun setLight(light:Light) {
        cardLight = light
        updateGui()

        lightNameView.setOnClickListener {
            val name = ""
            MaterialDialog.Builder(context)
                    .title("Choose device name")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("", cardLight!!.name, MaterialDialog.InputCallback { dialog, input ->
                        val newName = input.toString()
                        if(!newName.equals(cardLight!!.name)) {
                            cardLight!!.setName(newName,{
                                cardLight!!.name = newName
                                lightNameView.text = newName
                            })
                            dialog.dismiss()
                        }
                    }).show()
        }

        lightToggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            cardLight!!.setPower(isChecked,Light.LightEffect.SMOOTH,500,{

            })
        }

        lightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

        changeColorBtn.setOnClickListener {
            val colorPickerDialog = buildColorPickerDialog()
            colorPickerDialog.setOnColorChangedListener { selectedColor ->
                /*val newColor = selectedColor and 0x00FFFFFF.toInt()
                cardLight!!.setRGB(newColor,Light.LightEffect.SMOOTH,100, {

                })*/
                val pixelHSV = FloatArray(3)
                Color.colorToHSV(selectedColor,pixelHSV)
                light.setHSV(pixelHSV[0].toInt(),(pixelHSV[1]*100).toInt(),Light.LightEffect.SMOOTH,100,{
                    changeColorBtn.setButtonBackground(selectedColor)
                })
            }.build().show()
        }

        this.setOnClickListener {
            //TODO
        }
    }

    fun updateGui() {

        if(cardLight == null)
            return

        //Light name
        lightNameView.text = cardLight!!.name
        if(lightNameView.text.isEmpty())
            lightNameView.text = "Unknown"

        //Light toggle state
        if(cardLight!!.power!=null)
            lightToggleSwitch.isChecked = cardLight!!.power!!

        //Light brightness
        if(cardLight!!.brightness!=null)
            lightSeekBar.progress = cardLight!!.brightness!!

        if(cardLight!!.color_mode == 1) {
            if (cardLight!!.rgb != null) {
                changeColorBtn.setButtonBackgroundRGB(cardLight!!.rgb!!)
            }
        }
        else if(cardLight!!.color_mode == 2) {
            if (cardLight!!.ct != null) {
                val newColor = Helpers.getRGBFromK(cardLight!!.ct!!)
                changeColorBtn.setButtonBackground(newColor)
            }
        }
        else if(cardLight!!.color_mode == 3) {

            if ((cardLight!!.hue != null) && (cardLight!!.saturation != null)) {
                val newColor = Helpers.HsvToColor(cardLight!!.hue!!.toFloat(),(cardLight!!.saturation!!.toFloat() / 100),3.0F)
                changeColorBtn.setButtonBackground(newColor)
            }

        }
    }

    fun buildColorPickerDialog(): ColorPickerDialogBuilder {
        val colorPickerDialog =
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Välj färg")
                        .initialColor(Color.WHITE)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .lightnessSliderOnly()
        return colorPickerDialog
    }

    override fun onStateChanged(params: Map<String, Any>) {
        updateGui()
    }
}