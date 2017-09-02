package nu.cliffords.android_kyee.views.lights_fragment

import nu.cliffords.android_kyee.presenters.BasePresenter
import javax.inject.Inject

class LightsFragmentPresenter @Inject constructor (private val interactor: LightsFragmentContract.UserActionsListener): BasePresenter<LightsFragmentContract.View>(){

    fun discoverLights(timeout: Int) {
        view?.setRefreshing(true)
        interactor.discoverLights({ lights ->
            view?.setLights(lights)
            view?.setRefreshing(false)
        },timeout)
    }
}