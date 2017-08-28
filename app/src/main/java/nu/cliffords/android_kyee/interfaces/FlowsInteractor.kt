package nu.cliffords.android_kyee.interfaces

/**
 * Created by Henrik Nelson on 2017-08-26.
 */
interface FlowsInteractor {

    interface View {
        fun updateFlows()
    }

    interface UserActionsListener {
        fun getFlows(listener: (Unit)->Unit)
    }
}