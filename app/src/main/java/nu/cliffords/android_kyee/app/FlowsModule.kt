package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.interfaces.FlowsInteractor
import nu.cliffords.android_kyee.models.FlowsInteractorImpl
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-28.
 */

@Module
class FlowsModule(val app: App) {
    @Provides
    fun providesFlowsInteractor(): FlowsInteractor.UserActionsListener {
        val context = app.applicationContext
        val flowDao = FlowDatabase.getDatabase(context).flowDao()
        return FlowsInteractorImpl(flowDao)
    }
}