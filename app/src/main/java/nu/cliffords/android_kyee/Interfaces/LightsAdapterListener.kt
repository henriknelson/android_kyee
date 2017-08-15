package nu.cliffords.android_kyee.Interfaces

import nu.cliffords.kyee.Light

/**
 * Created by Henrik Nelson on 2017-08-11.
 */
interface LightsAdapterListener {
    fun onToggle(light: Light, state: Boolean)
    fun onBrighnessChange(light: Light, brightness: Int)
}