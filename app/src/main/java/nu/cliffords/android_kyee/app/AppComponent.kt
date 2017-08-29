package nu.cliffords.android_kyee.app

import dagger.Component
import nu.cliffords.android_kyee.fragments.FlowFragment
import nu.cliffords.android_kyee.fragments.FlowsFragment
import nu.cliffords.android_kyee.fragments.LightFragment
import nu.cliffords.android_kyee.fragments.LightsFragment
import nu.cliffords.android_kyee.widgets.LightCardView
import javax.inject.Singleton

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Singleton
@Component(modules=arrayOf(AppModule::class,LightsModule::class,LightModule::class,FlowsModule::class, FlowModule::class))
interface AppComponent {
    fun inject(app: AppModule)
    fun inject(fragment: LightsFragment)
    fun inject(fragment: LightFragment)
    fun inject(fragment: FlowsFragment)
    fun inject(fragment: FlowFragment)
    fun inject(lightCardView: LightCardView)
}