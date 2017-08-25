package nu.cliffords.android_kyee.fragments

//import nu.cliffords.android_kyee.database.FlowDatabase
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import nu.cliffords.android_kyee.R




/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowsFragment : Fragment() {

    private var flowListView: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_flows, container, false)
        setupGUI(rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Flows"
    }

    private fun setupGUI(view:View?) {
        flowListView = view?.findViewById(R.id.flowsList)

        val fab = view?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frame_container,FlowFragment()).addToBackStack("flow_fragment").commit()
        }
    }

    private fun updateFlowList() {
        flowListView?.removeAllViews()
        /*val flows = FlowDatabase.getDatabase(context).flowDao().getAll()
        flows.forEach{ flow ->
            val flowView = FlowCardView(context,flow,{
                //If user signals they want this flow removed
                FlowDatabase.getDatabase(context).flowDao().delete(flow)
                updateFlowList()
            })
            flowListView?.addView(flowView)
        }*/
    }

    override fun onResume() {
        super.onResume()
        updateFlowList()
    }

}