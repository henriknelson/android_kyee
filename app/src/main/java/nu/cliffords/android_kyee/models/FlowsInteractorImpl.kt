package nu.cliffords.android_kyee.models

import nu.cliffords.android_kyee.interfaces.FlowsInteractor
import org.jetbrains.anko.doAsync

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

class FlowsInteractorImpl: FlowsInteractor.UserActionsListener {

    override fun getFlows(listener: (Unit) -> Unit) {
        doAsync {

        }
    }

}