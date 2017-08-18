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
import nu.cliffords.android_kyee.adapters.LightsAdapter
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.classes.LightManager

/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment() {

    private val lightManager: LightManager = LightManager.instance
    private val lightsAdapter: LightsAdapter = LightsAdapter()
    private var refreshView: SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_lights, container, false)

        refreshView = rootView?.findViewById(R.id.refreshView)
        refreshView?.setOnRefreshListener {
            updateLights()
        }

        val lightsListView: RecyclerView? = rootView?.findViewById(R.id.lightListView)
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

}