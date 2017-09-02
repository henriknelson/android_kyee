package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.card_flow_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.views.FlowFragment
import nu.cliffords.android_kyee.presenters.FlowPresenter
import nu.cliffords.android_kyee.util.Helpers
import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowCardView(context: Context) : RelativeLayout(context){

    private var removeFlowButton: RoundButton? = null
    private var flow: Flow? = null

    private var presenter: FlowPresenter? = null

    @Inject
    fun setPresenter(presenter: FlowPresenter) {
        this.presenter = presenter
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.card_flow_view,this,true)
    }

    fun setFlow(flow: Flow) {
        this.flow = flow
        updateGUI()
    }

    private fun updateGUI() {
        flowNameText.text = flow!!.name

        editFlowButton.setOnClickListener {
            val flowFragment: Fragment = Helpers.instanceOf<FlowFragment>("id" to flow!!.id)
            (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,flowFragment).addToBackStack("flow_fragment").commit()
        }

        removeFlowButton = rootView.findViewById(R.id.removeFlowButton)
        removeFlowButton?.setOnClickListener {
            //flowDeleteListener(flow!!.id)
        }
    }


}