package nu.cliffords.android_kyee.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import nu.cliffords.android_kyee.adapters.LightsAdapter
import nu.cliffords.android_kyee.interfaces.LightViewListener
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager

/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment() {

    val lightManager: LightManager = LightManager.instance
    var refreshView: SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_lights, container, false)

        //Initialize swipe-to-refresh view
        refreshView = rootView?.findViewById(R.id.refreshView)
        refreshView?.setOnRefreshListener {
            updateLights()
        }

        //Initialize group list view
        val lightsListView: RecyclerView? = rootView?.findViewById(R.id.lightListView)
        //Use a linear layout manager
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context)

        lightsListView?.layoutManager = layoutManager
        lightsListView?.adapter = lightsAdapter
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateLights()
    }

    fun updateLights() {
        lightsAdapter.clearLights()
        lightManager.getLights({ lights ->
            lights.forEach {  light ->
                lightsAdapter.addLight(light)
            }
            refreshView!!.setRefreshing(false);
            lightsAdapter.notifyDataSetChanged()
        })
    }

       private val lightsAdapter: LightsAdapter = LightsAdapter(object: LightViewListener {
        override fun onToggle(light: Light, state: Boolean) {
            light.setPower(state, Light.LightEffect.SUDDEN,1000, { json ->

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
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .lightnessSliderOnly()
                    .setOnColorChangedListener { selectedColor ->
                        val pixelHSV = FloatArray(3)
                        Color.colorToHSV(selectedColor,pixelHSV)
                        light.setHSV(pixelHSV[0].toInt(),(pixelHSV[1]*100).toInt(),Light.LightEffect.SMOOTH,100,{

                        })

                    }.build()
                    .show()
        }
    })


}