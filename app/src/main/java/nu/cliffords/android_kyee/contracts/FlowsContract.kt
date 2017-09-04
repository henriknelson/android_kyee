package nu.cliffords.android_kyee.contracts

import nu.cliffords.android_kyee.database.Flow

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

interface FlowsContract {

    interface View {
        fun clearFlows()
        fun setFlows(flows: List<Flow>)
        fun openFlowDetails(flow: Flow)
        fun onFlowRemoved()
    }

    interface UserActionsListener {
        fun getFlows(listener: (List<Flow>)->Unit)
        fun editFlow(flow: Flow)
        fun removeFlow(flow: Flow, listener: (Unit)->Unit)
    }
}