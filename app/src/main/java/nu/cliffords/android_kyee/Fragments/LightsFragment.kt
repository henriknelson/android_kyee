package nu.cliffords.android_kyee.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.graphics.ColorUtils
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import nu.cliffords.android_kyee.Adapters.LightsAdapter
import nu.cliffords.android_kyee.Interfaces.LightViewListener
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager

/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment() {

    val lightManager: LightManager = LightManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        //lightManager.unregisterListener(this)
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_lights, container, false)

        //Initialize swipe-to-refresh view
        val refreshView: SwipeRefreshLayout? = rootView?.findViewById(R.id.refreshView)
        refreshView?.setOnRefreshListener {
            lightsAdapter.clearLights()
            lightManager.getLights({ lights ->
                lights.forEach {  light ->
                    lightsAdapter.addLight(light)
                }
                refreshView.setRefreshing(false);
            })
        }

        //Initialize group list view
        val lightsListView: RecyclerView? = rootView?.findViewById(R.id.lightListView)
        //Use a linear layout manager
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context)

        lightsListView?.layoutManager = layoutManager
        lightsListView?.adapter = lightsAdapter
        return rootView
    }

       private val lightsAdapter: LightsAdapter = LightsAdapter(object: LightViewListener {
        override fun onToggle(light: Light, state: Boolean) {
            light.setPower(state, Light.LightEffect.SUDDEN,1000,{ json ->
                //Log.d("android_khue",json.toString())
            })
        }

        override fun onBrighnessChange(light: Light, brightness: Int) {
            light.setBrightness(brightness, Light.LightEffect.SMOOTH,30,{ json ->
                //Log.d("android_khue",json.toString())
            })
        }

        override fun onClick(light: Light) {

            ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Välj färg")
                    .initialColor(Color.WHITE)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setOnColorSelectedListener { selectedColor ->
                        val pixelHSV = FloatArray(3)
                        /*
                      * pixelHSV[0] : Hue (0 .. 360)
                      * pixelHSV[1] : Saturation (0...1)
                      * pixelHSV[2] : Value (0...1)
                      */
                        val newColor = Color.colorToHSV(selectedColor,pixelHSV)
                        light.setHSV(pixelHSV[0].toInt(),(pixelHSV[1]*100).toInt(),Light.LightEffect.SMOOTH,100,{

                        })

                    }.build()
                    .show()
        }
    })


}