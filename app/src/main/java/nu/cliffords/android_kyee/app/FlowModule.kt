package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.interfaces.FlowInteractor
import nu.cliffords.android_kyee.models.FlowInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

@Module
class FlowModule(val app: App) {
    @Provides
    fun providesFlowInteractor(): FlowInteractor.UserActionsListener {
        val context = app.applicationContext
        val flowDao = FlowDatabase.getDatabase(context).flowDao()
        return FlowInteractorImpl(flowDao)
    }
}