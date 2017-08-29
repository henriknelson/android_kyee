package nu.cliffords.android_kyee.presenters

import android.graphics.Color
import nu.cliffords.android_kyee.interfaces.LightInteractor
import nu.cliffords.android_kyee.interfaces.LightsInteractor
import nu.cliffords.android_kyee.util.Helpers
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.Light.LightEffect
import nu.cliffords.kyee.interfaces.LightStateChangeListener
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-21.
 */

class LightPresenter @Inject constructor (private val interactor: LightInteractor.UserActionsListener): BasePresenter<LightInteractor.View>(){

    private var light: Light? = null

    fun setLight(light: Light) {
        interactor.setLight(light)
        view?.setName(light.name)
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



}