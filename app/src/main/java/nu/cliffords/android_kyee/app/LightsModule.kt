package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.views.lights_fragment.LightsFragmentContract
import nu.cliffords.android_kyee.views.lights_fragment.LightsFragmentInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Module
class LightsModule {
    @Provides
    fun providesLightsFragmentInteractor(): LightsFragmentContract.UserActionsListener {
        return LightsFragmentInteractorImpl()
    }
}