package nu.cliffords.android_kyee.interfaces

import nu.cliffords.android_kyee.database.Flow

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

interface FlowsInteractor {

    interface View {
        fun clearFlows()
        fun setFlows(flows: List<Flow>)
    }

    interface UserActionsListener {
        fun getFlows(listener: (List<Flow>)->Unit)
    }
}