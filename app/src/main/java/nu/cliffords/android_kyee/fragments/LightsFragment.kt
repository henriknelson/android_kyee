package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lights.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.adapters.LightsAdapter
import nu.cliffords.kyee.classes.LightManager


/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment() {

    private val lightsAdapter: LightsAdapter = LightsAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_lights, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshView.setOnRefreshListener {
            updateLights()
        }
        val layoutManager = LinearLayoutManager(context)
        lightsListView.layoutManager = layoutManager
        lightsListView.adapter = lightsAdapter

    }

    private fun updateLights() {
        LightManager.instance.getLights({ lights ->
            lightsAdapter.clearLights()
            lights.sortedBy { it.name }.forEach {  light ->
                lightsAdapter.addLight(light)
            }
            lightsAdapter.notifyDataSetChanged()
            refreshView!!.isRefreshing = false
        })
    }

}