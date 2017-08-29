package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_flow.*
import kotlinx.android.synthetic.main.fragment_flows.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.adapters.FlowsAdapter
import nu.cliffords.android_kyee.app.App
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.interfaces.FlowsInteractor
import nu.cliffords.android_kyee.presenters.FlowsPresenter
import nu.cliffords.android_kyee.widgets.FlowCardView
import javax.inject.Inject


/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowsFragment : Fragment(), FlowsInteractor.View {

    private val flowsAdapter: FlowsAdapter = FlowsAdapter()
    private var presenter: FlowsPresenter? = null

    @Inject
    fun setPresenter(presenter: FlowsPresenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).component.inject(this)
        presenter?.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_flows, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Flows"
        flowsListView.layoutManager = LinearLayoutManager(context)
        flowsListView.adapter = flowsAdapter
        fab?.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frame_container,FlowFragment()).addToBackStack("flow_fragment").commit()
        }
    }

    override fun onResume() {
        super.onResume()
        getFlows()
    }

    override fun clearFlows() {
        flowsAdapter.clearFlows()
    }

    override fun setFlows(flows: List<Flow>) {
        flows.forEach{ flow ->
            /*val flowView = FlowCardView(context,flow,{
                //If user signals they want this flow removed
                // FlowDatabase.getDatabase(context).flowDao().delete(flow)
                //updateFlowList()
            })*/
            flowsAdapter.addFlow(flow)
        }
        flowsAdapter.notifyDataSetChanged()
    }

    private fun getFlows() {
        flowsList?.removeAllViews()
        presenter?.getFlows()
    }

}