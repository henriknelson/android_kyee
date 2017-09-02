package nu.cliffords.android_kyee.views.flows_fragment

import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.presenters.BasePresenter
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

class FlowsFragmentPresenter @Inject constructor (private val interactor: FlowsFragmentContract.UserActionsListener): BasePresenter<FlowsFragmentContract.View>(){

    fun getFlows() {
        view?.clearFlows()
        interactor.getFlows({ flows ->
            view?.setFlows(flows)
        })
    }

    fun editFlow(flow: Flow) {
        view?.openFlowDetails(flow)
    }

    fun removeFlow(flow: Flow) {
        interactor.removeFlow(flow,{
            view?.onFlowRemoved()
        })
    }

}