package nu.cliffords.android_kyee.classes

import android.graphics.Color

/**
 * Created by Henrik Nelson on 2017-08-17.
 */

class Helpers {

    companion object {
        fun ColorToHSV(hue: Float, saturation: Float,value:Float) : Int {
            val pixelHSV = FloatArray(3)
            pixelHSV[0] = hue
            pixelHSV[1] = saturation
            pixelHSV[2] = value
            val newColor = Color.HSVToColor(pixelHSV)
            return newColor
        }
    }

}