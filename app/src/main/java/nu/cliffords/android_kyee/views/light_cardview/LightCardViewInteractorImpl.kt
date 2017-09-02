package nu.cliffords.android_kyee.views.light_cardview

import android.graphics.Color
import nu.cliffords.android_kyee.views.light_cardview.LightCardViewContract
import nu.cliffords.kyee.classes.Flow
import nu.cliffords.kyee.classes.Light
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

class LightCardViewInteractorImpl: LightCardViewContract.UserActionsListener{

    private var light: Light? = null

    override fun setLight(light: Light){
        this.light = light
    }

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

    override fun setName(name: String, listener: (String) -> Unit) {
        doAsync {
            light?.setName(name,{
                uiThread {
                    light?.name = name
                    listener(name)
                }
            })
        }
    }

    /*override fun startColorFlow(count: Int, action: Light.FlowAction, states:List<Flow.FlowState>, listener: (Boolean) -> Unit) {
        doAsync {
            light?.startColorFlow(count,action,states,{
                uiThread {
                    listener(true)
                }
            })
        }
    }

    override fun stopColorFlow(listener: (Boolean) -> Unit) {
        doAsync {
            light?.stopColorFlow {
                uiThread {
                    listener(true)
                }
            }
        }
    }*/

}