package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.jmedeisis.draglinearlayout.DragLinearLayout
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.dialogs.FlowStateDialog
import nu.cliffords.android_kyee.widgets.FlowStateCardView
import nu.cliffords.kyee.classes.FlowState
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.find

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

//Represents one 'Flow'

class FlowFragment(): Fragment() {

    var flowToModify: Flow? = null
    var nameInput: EditText? = null
    var countInput: EditText? = null
    var actionSpinner: Spinner? = null
    var flowList: DragLinearLayout? = null
    var createFlowStateButton: FloatingActionButton? = null
    var saveButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_flow, container, false)
        setupGUI(rootView)
        if(arguments?.getInt("id") != null) {
            val id = arguments.getInt("id")
            val flowToChange = FlowDatabase.getDatabase(context).flowDao().get(id)
            setFlow(flowToChange)
        }
        return rootView
    }

    private fun setupGUI(view: View?) {

        nameInput = view?.findViewById<EditText>(R.id.input_name)
        countInput = view?.findViewById<EditText>(R.id.input_count)
        actionSpinner = view?.findViewById<Spinner>(R.id.spinner_flow_actions)
        flowList = view?.findViewById<DragLinearLayout>(R.id.flowsList)
        createFlowStateButton = view?.findViewById<FloatingActionButton>(R.id.createFlowStateBtn)
        saveButton = view?.findViewById<Button>(R.id.btn_save)

        //Floating button to create a new flowstate
        createFlowStateButton?.setOnClickListener {
            FlowStateDialog
                .with(context)
                .setTitle("Lägg till flow")
                .setOnClickListener { flowState ->
                    val flowView = FlowStateCardView(context,flowState)
                        flowList?.addDragView(flowView,flowView)
                    }
                .build()
                .show()
        }

        //Button that saves a flow
        saveButton?.setOnClickListener {
            val name = nameInput?.text.toString()
            val count = countInput?.text.toString().toInt()
            val action = actionSpinner?.selectedItemPosition!!

            var flowStates: MutableList<FlowState> = mutableListOf<FlowState>()
            flowList?.childrenSequence()?.forEach { flowStateView ->
                val flowState = (flowStateView as FlowStateCardView).cardFlowState
                if (flowState != null)
                    flowStates.add(flowState)
            }
            
            val flowStatesStr = flowStates.joinToString(";")

            if (flowToModify != null) {
                flowToModify!!.name = name
                flowToModify!!.count = count
                flowToModify!!.action = action
                flowToModify!!.flow_states = flowStatesStr
                FlowDatabase.getDatabase(context).flowDao().insertFlow(flowToModify!!)
            }
            else
            {
                val flow = Flow(name,count,0,flowStatesStr)
                FlowDatabase.getDatabase(context).flowDao().insertFlow(flow)
            }

            fragmentManager.popBackStackImmediate()
        }
    }

    private fun setFlow(flow: Flow) {

        flowToModify = flow

        nameInput?.setText(flow.name)
        countInput?.setText(flow.count.toString())
        actionSpinner?.setSelection(flow.action)
        flow.flow_states.split(";").forEach { flowStateStr ->
            val flowStateParams = flowStateStr.split(",")
            if(flowStateParams.size == 4){
                val duration = flowStateParams[0].toInt()
                var flowStateMode = FlowState.FlowStateMode.COLOR
                when(flowStateParams[1].toInt()) {
                    1 -> flowStateMode = FlowState.FlowStateMode.COLOR
                    2 -> flowStateMode = FlowState.FlowStateMode.COLOR_TEMPERATURE
                    7 -> flowStateMode = FlowState.FlowStateMode.SLEEP
                }
                val value = flowStateParams[2].toInt()
                var brightness = flowStateParams[3].toInt()
                val flowState = FlowState(duration,flowStateMode,value,brightness)
                val flowView = FlowStateCardView(context,flowState)
                flowList?.addDragView(flowView,flowView)
            }
        }
    }
}