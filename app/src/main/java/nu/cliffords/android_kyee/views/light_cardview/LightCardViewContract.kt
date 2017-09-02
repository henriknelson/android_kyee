package nu.cliffords.android_kyee.views.light_cardview

import nu.cliffords.kyee.classes.Flow
import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-21.
 */

interface LightCardViewContract {

    interface View {
        fun setName(name: String)
        fun setColor(color: Int)
        fun setBrightness(brightness: Int)
        fun setPower(powered: Boolean)
    }

    interface UserActionsListener {
        fun setLight(light: Light)
        fun setBrightness(brightness: Int, listener: (Int) -> Unit)
        fun setPower(powered: Boolean, listener: (Boolean) -> Unit)
        fun setColor(color: Int, listener: (Int) -> Unit)
        fun setName(name: String, listener: (String) -> Unit)
        //fun startColorFlow(count: Int, action: Light.FlowAction, states:List<Flow.FlowState>, listener: (Boolean) -> Unit)
        //fun stopColorFlow(listener: (Boolean) -> Unit)
    }
}