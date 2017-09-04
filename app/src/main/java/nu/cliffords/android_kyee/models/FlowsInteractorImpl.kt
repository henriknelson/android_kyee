package nu.cliffords.android_kyee.models

import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.database.FlowDao
import nu.cliffords.android_kyee.contracts.FlowsContract
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Henrik Nelson on 2017-08-26.
 */

class FlowsInteractorImpl(val flowDao: FlowDao) : FlowsContract.UserActionsListener {

    override fun getFlows(listener: (List<Flow>) -> Unit) {
        doAsync {
            val flows = flowDao.getAll()
            uiThread {
                listener(flows)
            }
        }
    }

    override fun editFlow(flow: Flow) {

    }

    override fun removeFlow(flow: Flow, listener: (Unit) -> Unit) {
        doAsync {
            flowDao.delete(flow)
            uiThread {
                listener(Unit)
            }
        }
    }
}