package nu.cliffords.android_kyee.app

import dagger.Component
import nu.cliffords.android_kyee.fragments.LightsFragment
import javax.inject.Singleton

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Singleton
@Component(modules=arrayOf(AppModule::class,LightsModule::class))
interface AppComponent {
    fun inject(fragment: LightsFragment)
}