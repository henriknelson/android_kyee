package nu.cliffords.android_kyee.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            light.getBrightness { brightness ->
                Toast.makeText(context, brightness.toString(), Toast.LENGTH_SHORT).show()
                Log.d("android_kyee", brightness.toString())
            }
        }
    })


}