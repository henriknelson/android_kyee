package nu.cliffords.android_kyee.widgets

import nu.cliffords.android_kyee.database.Flow
import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import nu.cliffords.android_kyee.R

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowCardView(context: Context) : RelativeLayout(context){

    private var nameView: TextView? = null
    private var editFlowButton: RoundButton? = null
    private var removeFlowButton: RoundButton? = null
    private var flow: Flow? = null
    //var flowDeleteListener: (Int) -> Unit = {}


    /*constructor(context: Context, flow: Flow): this(context) {//, flowDeleteListener: (Int) -> Unit) : this(context) {
        this.flow = flow
        //this.flowDeleteListener = flowDeleteListener
        updateGUI()
    }*/

    init {
        LayoutInflater.from(context).inflate(R.layout.card_flow_view,this,true)
    }

    fun setFlow(flow: Flow) {
        this.flow = flow
        updateGUI()
    }

    private fun updateGUI() {
        nameView = rootView.findViewById(R.id.flowNameText)
        nameView?.text = flow!!.name

        editFlowButton = rootView.findViewById(R.id.editFlowButton)
        editFlowButton?.setOnClickListener {
            //val flowFragment: Fragment = Helpers.instanceOf<FlowFragment>("id" to flow!!.id)
            //(context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,flowFragment).addToBackStack("flow_fragment").commit()
        }

        removeFlowButton = rootView.findViewById(R.id.removeFlowButton)
        removeFlowButton?.setOnClickListener {
            //flowDeleteListener(flow!!.id)
        }
    }


}