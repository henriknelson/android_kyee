package nu.cliffords.android_kyee.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

@Module
class AppModule(val app: App) {
    @Provides @Singleton fun provideApp() = app
}