package nu.cliffords.android_kyee.Interfaces

import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-11.
 */
interface LightViewListener {
    fun onToggle(light: Light, state: Boolean)
    fun onBrighnessChange(light: Light, brightness: Int)
    fun onClick(light: Light)
}