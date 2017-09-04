package nu.cliffords.android_kyee.presenters

import nu.cliffords.android_kyee.contracts.FlowContract
import nu.cliffords.kyee.classes.Flow
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

class FlowPresenter @Inject constructor (private val interactor: FlowContract.UserActionsListener): BasePresenter<FlowContract.View>() {

    fun editFlow(flow: Flow) {

    }

    fun removeFlow(flow: Flow){

    }

}