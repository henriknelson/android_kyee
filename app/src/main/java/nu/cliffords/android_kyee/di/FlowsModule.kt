package nu.cliffords.android_kyee.di

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.contracts.FlowsContract
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.models.FlowsInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-28.
 */

@Module
class FlowsModule(val app: App) {
    @Provides
    fun providesFlowsFragmentInteractor(): FlowsContract.UserActionsListener {
        val context = app.applicationContext
        val flowDao = FlowDatabase.getDatabase(context).flowDao()
        return FlowsInteractorImpl(flowDao)
    }
}