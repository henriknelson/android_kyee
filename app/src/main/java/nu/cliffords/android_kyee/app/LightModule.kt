package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.interfaces.LightInteractor
import nu.cliffords.android_kyee.models.LightInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

@Module
class LightModule {
    @Provides
    fun providesLightInteractor(): LightInteractor.UserActionsListener {
        return LightInteractorImpl()
    }
}