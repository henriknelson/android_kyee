package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.interfaces.LightsInteractor
import nu.cliffords.android_kyee.models.LightsInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Module
class LightsModule {
    @Provides
    fun providesLightsInteractor(): LightsInteractor.UserActionsListener {
        return LightsInteractorImpl()
    }
}