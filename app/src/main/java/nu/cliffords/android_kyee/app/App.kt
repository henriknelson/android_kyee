package nu.cliffords.android_kyee.app

import android.app.Application

/**
 * Created by Henrik Nelson on 2017-08-24.
 */
class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .flowsModule(FlowsModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}