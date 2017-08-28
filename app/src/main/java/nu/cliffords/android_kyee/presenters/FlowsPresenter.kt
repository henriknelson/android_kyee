package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.interfaces.FlowsInteractor
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

class FlowsPresenter @Inject constructor (private val interactor: FlowsInteractor.UserActionsListener): AbsPresenter<FlowsInteractor.View>(){

    fun getFlows(listener: (Unit)-> Unit) {
        interactor.getFlows({ flows ->
            view?.updateFlows()
        })
    }

}