package nu.cliffords.android_kyee.presenters

import android.graphics.Color
import nu.cliffords.android_kyee.interfaces.LightContract
import nu.cliffords.android_kyee.util.Helpers
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.Light.LightEffect
import nu.cliffords.kyee.interfaces.LightStateChangeListener

/**
 * Created by Henrik Nelson on 2017-08-21.
 */

class LightPresenter(private val view: LightContract.View, private val light: Light): LightContract.UserActionsListener, LightStateChangeListener {

    init {
        light.registerStateListener(this)



        when(light.color_mode) {
            1 -> view.setColor(light.rgb)
            2 -> {
                val newColor = Helpers.getRGBFromK(light.ct)
                view.setColor(newColor)
            }
            3 -> {
                val newColor = Helpers.HsvToColor(light.hue.toFloat(),(light.saturation.toFloat() / 100),3.0F)
                view.setColor(newColor)
            }
        }

        var name = light.name
        if(name.isEmpty())
            name = "Unknown"
        view.setName(name)

        view.setBrightness(light.brightness)
        view.setPower(light.power)
    }

    override fun setColor(color: Int) {
        val pixelHSV = FloatArray(3)
        Color.colorToHSV(color,pixelHSV)
        val hue = pixelHSV[0].toInt()
        val saturation = (pixelHSV[1] * 100).toInt()
        light.setHSV(hue,saturation,LightEffect.SMOOTH,100,{
            //this.setColor(selectedColor)
        })
    }

    override fun setBrightness(brightness: Int) {
        light.setBrightness(brightness,LightEffect.SUDDEN,30,{
            //view.setBrightness(brightness)
        })
    }

    override fun setPower(powered: Boolean) {
        if(light.power != powered) {
            light.setPower(powered, LightEffect.SMOOTH, 500, {
                //view.setPower(powered)
            })
        }
    }

    override fun onStateChanged(params: Map<String, Any>) {
        if(params.containsKey("power"))
            view.setPower(light.power)
        if(params.containsKey("bright"))
            view.setBrightness(light.brightness)
        if((params.containsKey("hue")) || (params.containsKey("sat"))) {
            val newColor = Helpers.HsvToColor(light.hue.toFloat(), (light.saturation.toFloat() / 100), 3.0F)
            view.setColor(newColor)
        }
    }


}