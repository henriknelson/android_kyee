package nu.cliffords.android_kyee.classes

import android.graphics.Color
import android.support.v4.app.Fragment
import org.jetbrains.anko.bundleOf

/**
 * Created by Henrik Nelson on 2017-08-17.
 */

class Helpers {

    companion object {

        inline fun <reified T : Fragment> instanceOf(vararg params: Pair<String, Any>): T = T::class.java.newInstance().apply {
            arguments = bundleOf(*params)
        }

        fun getRGBFromK(temperature: Int): Int {
            // Used this: https://gist.github.com/paulkaplan/5184275 at the beginning
            // based on http://stackoverflow.com/questions/7229895/display-temperature-as-a-color-with-c
            // this answer: http://stackoverflow.com/a/24856307
            // (so, just interpretation of pseudocode in Java)

            var x = temperature / 1000.0
            if (x > 40) {
                x = 40.0
            }
            var red: Double
            var green: Double
            var blue: Double

            // R
            red = if (temperature < 6527) {
                1.0
            } else {
                val redpoly = doubleArrayOf(4.93596077e0, -1.29917429e0, 1.64810386e-01, -1.16449912e-02, 4.86540872e-04, -1.19453511e-05, 1.59255189e-07, -8.89357601e-10)
                poly(redpoly, x)

            }
            // G
            when {
                temperature < 850 -> green = 0.0
                temperature <= 6600 -> {
                    val greenpoly = doubleArrayOf(-4.95931720e-01, 1.08442658e0, -9.17444217e-01, 4.94501179e-01, -1.48487675e-01, 2.49910386e-02, -2.21528530e-03, 8.06118266e-05)
                    green = poly(greenpoly, x)
                }
                else -> {
                    val greenpoly = doubleArrayOf(3.06119745e0, -6.76337896e-01, 8.28276286e-02, -5.72828699e-03, 2.35931130e-04, -5.73391101e-06, 7.58711054e-08, -4.21266737e-10)

                    green = poly(greenpoly, x)
                }
            }
            // B
            when {
                temperature < 1900 -> blue = 0.0
                temperature < 6600 -> {
                    val bluepoly = doubleArrayOf(4.93997706e-01, -8.59349314e-01, 5.45514949e-01, -1.81694167e-01, 4.16704799e-02, -6.01602324e-03, 4.80731598e-04, -1.61366693e-05)
                    blue = poly(bluepoly, x)
                }
                else -> blue = 1.0
            }

            red = clamp(red, 0.toDouble(), 255.toDouble())
            blue = clamp(blue, 0.toDouble(), 255.toDouble())
            green = clamp(green, 0.toDouble(), 255.toDouble())
            return getIntFromColor(red.toInt(),green.toInt(),blue.toInt())
        }

        fun HsvToColor(hue: Float, saturation: Float,value:Float) : Int {
            val pixelHSV = FloatArray(3)
            pixelHSV[0] = hue
            pixelHSV[1] = saturation
            pixelHSV[2] = value
            return Color.HSVToColor(pixelHSV)
        }

        private fun poly(coefficients: DoubleArray, x: Double): Double {
            var result = coefficients[0]
            var xn = x
            for (i in 1 until coefficients.size) {
                result += xn * coefficients[i]
                xn *= x

            }
            return result
        }

        private fun clamp(x: Double, min: Double, max: Double): Double {
            if (x < min) {
                return min
            }
            return if (x > max) {
                max
            } else x
        }

        private fun getIntFromColor(Red: Int, Green: Int, Blue: Int): Int {
            val r = Red shl 16 and 0x00FF0000 //Shift red 16-bits and mask out other stuff
            val g = Green shl 8 and 0x0000FF00 //Shift Green 8-bits and mask out other stuff
            val b = Blue and 0x000000FF //Mask out anything not blue.

            return 0x000000 or r or g or b //0xFF000000 for 100% Alpha. Bitwise OR everything together.
        }


    }

}