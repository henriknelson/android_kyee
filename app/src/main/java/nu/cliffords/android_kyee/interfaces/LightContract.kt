package nu.cliffords.android_kyee.interfaces

/**
 * Created by Henrik Nelson on 2017-08-21.
 */

interface LightContract {

    interface View {
        fun setName(name: String)
        fun setColor(color: Int)
        fun setBrightness(brightness: Int)
        fun setPower(powered: Boolean)
    }

    interface UserActionsListener {
        fun setBrightness(brightness: Int)
        fun setPower(powered: Boolean)
        fun setColor(color: Int)
    }
}