package nu.cliffords.android_kyee.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_flow.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.database.FlowStates
import nu.cliffords.android_kyee.dialogs.FlowStateDialog
import nu.cliffords.android_kyee.widgets.FlowStateCardView
import org.jetbrains.anko.childrenSequence

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

//Represents a Fragment that displays a single 'Flow'

class FlowFragment : Fragment() {

    private var flowToModify: Flow? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // If this is not a new Flow, but a Flow to be modified,
        // the flows database id should be passed as an argument
        if(arguments?.getInt("id") != null) {
            val id = arguments.getInt("id")
            val flowToChange = FlowDatabase.getDatabase(context).flowDao().get(id)
            setFlow(flowToChange)
        }

        setupActionListeners()

        if(flowToModify != null) {
            (activity as AppCompatActivity).supportActionBar!!.title = "Flow: ${flowToModify!!.name}"
        }else{
            (activity as AppCompatActivity).supportActionBar!!.title = "Create flow"
        }
    }


    private fun setupActionListeners() {

        //Floating button to create a new flow state
        createFlowStateBtn.setOnClickListener {
            FlowStateDialog
                .with(context)
                .setTitle("Add Flow")
                .setOnClickListener { flowState ->
                    val flowView = FlowStateCardView(context,flowState,{ flowViewToRemove ->
                        //If someone deletes the flow state
                        flowsList?.removeView(flowViewToRemove)
                    })
                    flowsList?.addView(flowView)
                }
                .build()
                .show()
        }

        //Button that saves a flow
        saveButton.setOnClickListener {

            //Get name, count and action for this Flow
            val name = nameInput.text.toString()
            val count = countInput.text.toString().toInt()
            val action = actionSpinner.selectedItemPosition

            //Get a list of flow states for this Flow
            val flowStates: MutableList<nu.cliffords.kyee.classes.Flow.FlowState> = mutableListOf()
            flowsList.childrenSequence().forEach { flowStateView ->
                val flowState = (flowStateView as FlowStateCardView).cardFlowState
                if (flowState != null)
                    flowStates.add(flowState)
            }

            // If this is a completely new Flow:
            if (flowToModify != null) {
                flowToModify!!.name = name
                flowToModify!!.count = count
                flowToModify!!.action = action
                flowToModify!!.flow_states = FlowStates(flowStates)
                FlowDatabase.getDatabase(context).flowDao().insertFlow(flowToModify!!)
            }
            // ..or a modified one:
            else
            {
                val flow = Flow(name,count,0, FlowStates(flowStates))
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

        flow.flow_states?.flowStates?.forEach { flowState ->
            val flowView = FlowStateCardView(context,flowState,{ flowViewToRemove ->
                //If someone deletes the flow state
                flowsList?.removeView(flowViewToRemove)
            })
            flowsList?.addView(flowView)
        }
    }
}