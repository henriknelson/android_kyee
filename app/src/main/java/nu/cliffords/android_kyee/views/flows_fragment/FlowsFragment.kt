package nu.cliffords.android_kyee.views.flows_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_flow.*
import kotlinx.android.synthetic.main.fragment_flows.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.app.App
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.util.Helpers
import nu.cliffords.android_kyee.views.FlowFragment
import nu.cliffords.android_kyee.widgets.RoundButton
import org.jetbrains.anko.find
import javax.inject.Inject


/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowsFragment : Fragment(), FlowsFragmentContract.View {

    private var mListAdapter: FlowsAdapter? = null
    private var mPresenter: FlowsFragmentPresenter? = null

    //region Dagger injections
    @Inject
    fun setPresenter(presenter: FlowsFragmentPresenter) {
        this.mPresenter = presenter
    }
    //endregion

    //region Android lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).component.inject(this)
        mListAdapter = FlowsAdapter(mItemListener)
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.bind(this)
        getFlows()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unbind()  //prevent leaking activity in case presenter is orchestrating a long running task
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_flows, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Flows"
        flowsListView.layoutManager = LinearLayoutManager(context)
        flowsListView.adapter = mListAdapter
        fab?.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frame_container, FlowFragment()).addToBackStack("flow_fragment").commit()
        }
    }
    //endregion

    //region View
    override fun setFlows(flows: List<Flow>) {
        flows.forEach{ flow ->
            mListAdapter?.addFlow(flow)
        }
        mListAdapter?.notifyDataSetChanged()
    }

    override fun openFlowDetails(flow: Flow) {
        val flowFragment: Fragment = Helpers.instanceOf<FlowFragment>("id" to flow!!.id)
        (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,flowFragment).addToBackStack("flow_fragment").commit()
    }

    override fun onFlowRemoved() {
        getFlows()
    }

    override fun clearFlows() {
        mListAdapter?.clearFlows()
    }
    //endregion

    //region Actions
    private fun getFlows() {
        flowsList?.removeAllViews()
        mPresenter?.getFlows()
    }
    //endregion

    //region Interfaces
    interface FlowItemListener {
        fun onEditFlowClick(flowToEdit: Flow)
        fun onRemoveFlowClick(flowToRemove: Flow)
    }
    //endregion

    //region Itemlistener
    var mItemListener: FlowItemListener = object : FlowItemListener {
        override fun onEditFlowClick(flowToEdit: Flow) {
            mPresenter?.editFlow(flowToEdit)
        }

        override fun onRemoveFlowClick(flowToRemove: Flow) {
            mPresenter?.removeFlow(flowToRemove)
        }
    }
    //endregion

    //region FlowsAdapter
    class FlowsAdapter(val flowItemListener: FlowItemListener) : RecyclerView.Adapter<FlowsAdapter.ViewHolder>() {

        private val mFlows: MutableList<Flow> = mutableListOf()

        fun addFlow(flow: Flow) {
            mFlows.add(flow)
        }

        fun clearFlows() {
            mFlows.clear()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent?.getContext())
            val rowView = inflater.inflate(R.layout.card_flow_view, parent, false)
            return ViewHolder(rowView, flowItemListener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val flow = mFlows[position]
            holder.bindLight(flow)
        }

        override fun getItemCount() = mFlows.count()

        class ViewHolder(private val flowView: View, private val itemListener: FlowItemListener) : RecyclerView.ViewHolder(flowView) {

            fun bindLight(flow: Flow) {
                with(flow) {

                    val flowName = flowView.find<TextView>(R.id.flowNameText)
                    flowName.text = flow.name

                    val editFlowButton = flowView.find<RoundButton>(R.id.editFlowButton)
                    editFlowButton.setOnClickListener {
                        itemListener.onEditFlowClick(flow)
                    }

                    val removeFlowButton = flowView.find<RoundButton>(R.id.removeFlowButton)
                    removeFlowButton.setOnClickListener {
                        itemListener.onRemoveFlowClick(flow)
                    }
                    //lightView.setLight(light)
                }
            }
        }

    }
    //endregion

}