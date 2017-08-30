package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.interfaces.LightContract
import nu.cliffords.android_kyee.models.LightCardInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

@Module
class LightModule() {
    @Provides
    fun providesLightInteractor(): LightContract.UserActionsListener {
        return LightCardInteractorImpl()
    }
}