package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import nu.cliffords.android_kyee.views.light_cardview.LightCardViewContract
import nu.cliffords.android_kyee.views.light_cardview.LightCardViewInteractorImpl

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

@Module
class LightModule() {
    @Provides
    fun providesLightCardViewInteractor(): LightCardViewContract.UserActionsListener {
        return LightCardViewInteractorImpl()
    }
}