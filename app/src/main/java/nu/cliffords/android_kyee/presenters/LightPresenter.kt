package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.contracts.LightContract
import nu.cliffords.android_kyee.util.Helpers
import nu.cliffords.kyee.classes.Flow
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.interfaces.LightStateChangeListener
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-21.
 */

class LightPresenter
    @Inject constructor (private val interactor: LightContract.UserActionsListener):
        BasePresenter<LightContract.View>(), LightStateChangeListener{

    private var light: Light? = null

    fun setLight(light: Light) {
        this.light = light
        interactor.setLight(light)
        light.registerStateListener(this)
        updateView()
    }

    fun setColor(color: Int) {
        interactor.setColor(color,{ selectedColor ->
            view?.setColor(selectedColor)
        })
    }

    fun setPower(power: Boolean) {
        interactor.setPower(power,{ isPowered ->
            view?.setPower(isPowered)
        })
    }

    fun setBrightness(brightness: Int) {
        interactor.setBrightness(brightness,{ newBrightness ->
            view?.setBrightness(brightness)
        })
    }

    fun setName(name: String) {
        interactor.setName(name,{
            view?.setName(name)
        })
    }

    fun startColorFlow(count: Int, action: Light.FlowAction, states:List<Flow.FlowState>) {
        interactor.startColorFlow(count,action,states,{
            view?.setFlowPlaying()
        })
    }

    fun stopColorFlow() {
        interactor.stopColorFlow {
            view?.setFlowStopped()
        }
    }

    private fun updateView() {
        if(light!=null) {
            view?.setPower(light!!.power)
            view?.setBrightness(light!!.brightness)
            view?.setColor(light!!.rgb)
            if(light!!.name.isEmpty())
                view?.setName("Unknown")
            else
                view?.setName(light!!.name)
            when(light?.color_mode) {
                1 -> view?.setColor(light!!.rgb)
                2 -> {
                    val newColor = Helpers.getRGBFromK(light!!.ct)
                    view?.setColor(newColor)
                }
                3 -> {
                    val newColor = Helpers.HsvToColor(light!!.hue.toFloat(),(light!!.saturation.toFloat() / 100),3.0F)
                    view?.setColor(newColor)
                }
            }
        }
    }

    override fun onStateChanged(params: Map<String, Any>) {
        updateView()
    }



}