package nu.cliffords.android_kyee.di

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.contracts.LightsContract
import nu.cliffords.android_kyee.models.LightsInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Module
class LightsModule {
    @Provides
    fun providesLightsFragmentInteractor(): LightsContract.UserActionsListener {
        return LightsInteractorImpl()
    }
}