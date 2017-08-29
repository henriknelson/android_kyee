package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.fragments.FlowFragment
import nu.cliffords.android_kyee.util.Helpers

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowCardView(context: Context) : RelativeLayout(context){

    private var nameView: TextView? = null
    private var editFlowButton: RoundButton? = null
    private var removeFlowButton: RoundButton? = null
    private var flow: Flow? = null

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
        editFlowButton?.setOnClickListener { val flowFragment: Fragment = Helpers.instanceOf<FlowFragment>("id" to flow!!.id)
        (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,flowFragment).addToBackStack("flow_fragment").commit()
        }

        removeFlowButton = rootView.findViewById(R.id.removeFlowButton)
        removeFlowButton?.setOnClickListener {
            //flowDeleteListener(flow!!.id)
        }
    }


}