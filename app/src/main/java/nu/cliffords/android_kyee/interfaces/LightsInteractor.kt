package nu.cliffords.android_kyee.interfaces

import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

interface LightsInteractor {

    interface View {
        fun setLights(lights: List<Light>)
        fun setRefreshing(isRefreshing: Boolean)
    }

    interface UserActionsListener {
        fun discoverLights(listener: (List<Light>) -> Unit, timeout: Int)
    }
}