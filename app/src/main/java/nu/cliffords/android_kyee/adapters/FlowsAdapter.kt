package nu.cliffords.android_kyee.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import nu.cliffords.android_kyee.widgets.FlowCardView
import nu.cliffords.android_kyee.database.Flow

/**
 * Created by Henrik Nelson on 2017-08-29.
 */

class FlowsAdapter : RecyclerView.Adapter<FlowsAdapter.ViewHolder>() {

    private val flowsList: MutableList<Flow> = mutableListOf()

    fun addFlow(flow: Flow){
        flowsList.add(flow)
    }

    fun clearFlows(){
        flowsList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FlowCardView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flow = flowsList[position]
        holder.bindLight(flow)
    }

    override fun getItemCount() = flowsList.count()

    class ViewHolder(private val flowView: FlowCardView) : RecyclerView.ViewHolder(flowView) {

        fun bindLight(flow: Flow) {
            with(flow) {
                flowView.setFlow(flow)
            }
        }
    }
}