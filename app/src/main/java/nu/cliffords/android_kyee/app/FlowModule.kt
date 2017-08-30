package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.interfaces.FlowContract
import nu.cliffords.android_kyee.models.FlowInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

@Module
class FlowModule{
    @Provides
    fun providesFlowInteractor(): FlowContract.UserActionsListener {
        return FlowInteractorImpl()
    }
}