package nu.cliffords.android_kyee.models

import nu.cliffords.android_kyee.interfaces.LightsContract
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LightsInteractorImpl: LightsContract.UserActionsListener {

    override fun discoverLights(listener: (List<Light>) -> Unit, timeout: Int) {

        doAsync {
            val lights = LightManager.instance.getLights(timeoutTime = timeout)
            uiThread {
                listener(lights)
            }
        }
    }
}