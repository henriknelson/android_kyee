package nu.cliffords.android_kyee.models

import nu.cliffords.android_kyee.interfaces.LightsInteractor
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Henrik Nelson on 2017-08-24.
 */
class LightsInteractorImpl: LightsInteractor.UserActionsListener {

    override fun discoverLights(listener: (List<Light>) -> Unit, timeout: Int) {
        doAsync {
            val lights = LightManager.instance.getLights()
            uiThread {
                listener(lights)
            }
        }
    }

}