package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.contracts.FlowsContract
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

class FlowsPresenter @Inject constructor (private val interactor: FlowsContract.UserActionsListener): BasePresenter<FlowsContract.View>(){

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