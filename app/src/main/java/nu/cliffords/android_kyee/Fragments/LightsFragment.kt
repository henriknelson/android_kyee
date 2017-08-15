package nu.cliffords.android_kyee.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.android_kyee.Adapters.LightsAdapter
import nu.cliffords.android_kyee.Interfaces.LightsAdapterListener
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.Light

/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_lights, container, false)
        //Initialize group list view
        val lightsListView: RecyclerView? = rootView?.findViewById(R.id.lightListView)
        //Use a linear layout manager
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context)

        lightsListView?.layoutManager = layoutManager
        lightsListView?.adapter = lightsAdapter
        return rootView
    }

    override fun onStart() {
        super.onStart()
        lightsAdapter.clearLights()
        Light.getLights ({ lightList ->
            lightList.forEach { light ->
                lightsAdapter.addLight(light)
            }
        })


    }

       private val lightsAdapter: LightsAdapter = LightsAdapter(object: LightsAdapterListener {
        override fun onToggle(light: Light, state: Boolean) {
            light.setPower(state,{ json ->
                Log.d("kYee",json)
            })
            //bridge.setLightState(lightId = light.lightId, on=state,brightness = light.state.bri, transitiontime = 1, listener = { json -> })
        }

        override fun onBrighnessChange(light: Light, brightness: Int) {
            //bridge.setLightState(lightId = light.lightId, brightness = brightness, transitiontime = 1, listener = { json -> })
        }
    })


}