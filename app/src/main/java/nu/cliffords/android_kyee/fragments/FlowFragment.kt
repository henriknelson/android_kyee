package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jmedeisis.draglinearlayout.DragLinearLayout
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.dialogs.FlowStateDialog
import nu.cliffords.android_kyee.widgets.FlowCardView

/**
 * Created by Henrik Nelson on 2017-08-18.
 */
class FlowFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_flow, container, false)
        setupGUI(rootView)
        return rootView
    }

    fun setupGUI(view: View?) {
        val createFlowStateBtn = view?.findViewById<FloatingActionButton>(R.id.createFlowStateBtn)
        val flowList = view?.findViewById<DragLinearLayout>(R.id.flowsList)
        createFlowStateBtn?.setOnClickListener {
            FlowStateDialog
                    .with(context)
                    .setTitle("Edit flow state")
                    .setOnClickListener { flowState ->
                        val flowView = FlowCardView(context)
                        flowView.setFlowState(flowState)
                        flowList?.addDragView(flowView,flowView)
                    }
                    .build()
                    .show()
        }
    }

}