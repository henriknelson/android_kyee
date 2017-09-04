package nu.cliffords.android_kyee.di

import dagger.Component
import nu.cliffords.android_kyee.views.*
import javax.inject.Singleton

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Singleton
@Component(modules=arrayOf(AppModule::class,LightsModule::class, LightModule::class,FlowsModule::class, FlowModule::class))
interface AppComponent {
    fun inject(app: AppModule)
    fun inject(fragment: LightsFragment)
    fun inject(fragment: LightFragment)
    fun inject(fragment: FlowsFragment)
    fun inject(fragment: FlowFragment)
    fun inject(lightCardView: LightCardView)
}