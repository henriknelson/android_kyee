package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.android_kyee.R
import android.support.design.widget.FloatingActionButton
import android.widget.LinearLayout
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.widgets.FlowCard


/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowsFragment(): Fragment() {

    private var flowListView: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_flows, container, false)
        setupGUI(rootView)
        return rootView
    }

    fun setupGUI(view:View?) {
        flowListView = view?.findViewById<LinearLayout>(R.id.flowsList)

        val fab = view?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frame_container,FlowFragment()).addToBackStack("flow_fragment").commit()
        }

    }

    override fun onResume() {
        super.onResume()
        flowListView?.removeAllViews()
        val flows = FlowDatabase.getDatabase(context).flowDao().getAll()
        flows.forEach{ flow ->
            val flowView = FlowCard(context,flow)
            flowListView?.addView(flowView)
        }
    }

}