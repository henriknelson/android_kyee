package nu.cliffords.android_kyee.di

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.contracts.LightContract
import nu.cliffords.android_kyee.models.LightInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

@Module
class LightModule() {
    @Provides
    fun providesLightCardViewInteractor(): LightContract.UserActionsListener {
        return LightInteractorImpl()
    }
}