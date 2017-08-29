package nu.cliffords.android_kyee.models

import android.graphics.Color
import nu.cliffords.android_kyee.interfaces.LightInteractor
import nu.cliffords.kyee.classes.Light
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

class LightInteractorImpl: LightInteractor.UserActionsListener{

    private var light: Light? = null

    override fun setLight(light: Light){
        this.light = light
    }



    /*init {
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

    fun updateUI(){

    }*/

    override fun setColor(color: Int, listener: (Int) -> Unit) {
        val pixelHSV = FloatArray(3)
        Color.colorToHSV(color,pixelHSV)
        val hue = pixelHSV[0].toInt()
        val saturation = (pixelHSV[1] * 100).toInt()
        doAsync {
            light?.setHSV(hue, saturation, Light.LightEffect.SMOOTH, 100, {
                uiThread {
                    listener(color)
                }
            })
        }
    }

    override fun setBrightness(brightness: Int, listener: (Int) -> Unit) {
        doAsync {
            light?.setBrightness(brightness, Light.LightEffect.SUDDEN, 30, {
                uiThread {
                    listener(brightness)
                }
            })
        }
    }

    override fun setPower(powered: Boolean, listener: (Boolean) -> Unit) {
        if(light?.power != powered) {
            doAsync {
                light?.setPower(powered, Light.LightEffect.SMOOTH, 500, {
                    uiThread {
                        listener(powered)
                    }
                })
            }
        }
    }

    /*override fun onStateChanged(params: Map<String, Any>) {
        if(params.containsKey("power"))
            view.setPower(light.power)
        if(params.containsKey("bright"))
            view.setBrightness(light.brightness)
        if((params.containsKey("hue")) || (params.containsKey("sat"))) {
            val newColor = Helpers.HsvToColor(light.hue.toFloat(), (light.saturation.toFloat() / 100), 3.0F)
            view.setColor(newColor)
        }
    }*/

}