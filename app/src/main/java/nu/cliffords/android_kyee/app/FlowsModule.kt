package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.views.flows_fragment.FlowsFragmentContract
import nu.cliffords.android_kyee.views.flows_fragment.FlowsFragmentInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-28.
 */

@Module
class FlowsModule(val app: App) {
    @Provides
    fun providesFlowsFragmentInteractor(): FlowsFragmentContract.UserActionsListener {
        val context = app.applicationContext
        val flowDao = FlowDatabase.getDatabase(context).flowDao()
        return FlowsFragmentInteractorImpl(flowDao)
    }
}