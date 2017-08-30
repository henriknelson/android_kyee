package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.interfaces.LightsContract
import nu.cliffords.kyee.classes.Light
import javax.inject.Inject

class LightsPresenter @Inject constructor (private val interactor: LightsContract.UserActionsListener): BasePresenter<LightsContract.View>(){

    fun discoverLights(timeout: Int) {
        view?.setRefreshing(true)
        interactor.discoverLights({ lights ->
            view?.setLights(lights)
            view?.setRefreshing(false)
        },timeout)
    }
}