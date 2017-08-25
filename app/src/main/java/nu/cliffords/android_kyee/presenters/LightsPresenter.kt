package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.interfaces.LightsInteractor
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

class LightsPresenter @Inject constructor (private val interactor: LightsInteractor.UserActionsListener): AbsPresenter<LightsInteractor.View>(){

    fun discoverLights(timeout: Int) {
        interactor.discoverLights({ lights ->
            view?.updateLights(lights)
        },timeout)
    }

}