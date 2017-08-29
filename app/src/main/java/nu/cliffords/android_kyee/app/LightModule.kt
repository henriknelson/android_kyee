package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.interfaces.LightInteractor
import nu.cliffords.android_kyee.interfaces.LightsInteractor
import nu.cliffords.android_kyee.models.LightInteractorImpl
import nu.cliffords.android_kyee.models.LightsInteractorImpl

/**
 * Created by henrik.nelson2 on 2017-08-29.
 */
@Module
class LightModule {
    @Provides
    fun providesLightInteractor(): LightInteractor.UserActionsListener {
        return LightInteractorImpl()
    }
}