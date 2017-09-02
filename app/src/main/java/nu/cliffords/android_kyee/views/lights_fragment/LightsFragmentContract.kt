package nu.cliffords.android_kyee.views.lights_fragment

import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

interface LightsFragmentContract {

    interface View {
        fun setLights(lights: List<Light>)
        fun setRefreshing(isRefreshing: Boolean)
    }

    interface UserActionsListener {
        fun discoverLights(listener: (List<Light>) -> Unit, timeout: Int)
    }
}