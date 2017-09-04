package nu.cliffords.android_kyee.di

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.contracts.FlowContract
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