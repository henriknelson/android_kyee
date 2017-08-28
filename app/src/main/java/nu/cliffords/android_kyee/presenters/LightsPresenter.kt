package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.interfaces.LightsInteractor
import javax.inject.Inject

class LightsPresenter @Inject constructor (private val interactor: LightsInteractor.UserActionsListener): AbsPresenter<LightsInteractor.View>(){

    fun discoverLights(timeout: Int) {
        interactor.discoverLights({ lights ->
            view?.updateLights(lights)
        },timeout)
    }

}