package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.interfaces.LightsInteractor
import javax.inject.Inject

class LightsPresenter @Inject constructor (private val interactor: LightsInteractor.UserActionsListener): BasePresenter<LightsInteractor.View>(){

    fun discoverLights(timeout: Int) {
        view?.setRefreshing(true)
        interactor.discoverLights({ lights ->
            view?.setLights(lights)
            view?.setRefreshing(false)
        },timeout)
    }


}