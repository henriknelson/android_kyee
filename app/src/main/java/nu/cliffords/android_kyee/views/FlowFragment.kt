package nu.cliffords.android_kyee.views

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
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
    private var nameInput: EditText? = null
    private var countInput: EditText? = null
    private var actionSpinner: Spinner? = null
    private var flowList: LinearLayout? = null
    private var createFlowStateButton: FloatingActionButton? = null
    private var saveButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater?.inflate(R.layout.fragment_flow, container, false)
        setupGUI(rootView)
        setupActionListeners()

        // If this is not a new Flow, but a Flow to be modified,
        // the flows database id should be passed as an argument
        if(arguments?.getInt("id") != null) {
            val id = arguments.getInt("id")
            val flowToChange = FlowDatabase.getDatabase(context).flowDao().get(id)
            setFlow(flowToChange)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(flowToModify != null) {
            (activity as AppCompatActivity).supportActionBar!!.title = "Flow: ${flowToModify!!.name}"
        }else{
            (activity as AppCompatActivity).supportActionBar!!.title = "Create flow"
        }
    }

    private fun setupGUI(view: View?) {

        nameInput = view?.findViewById(R.id.input_name)
        countInput = view?.findViewById(R.id.input_count)
        actionSpinner = view?.findViewById(R.id.spinner_flow_actions)
        flowList = view?.findViewById(R.id.flowsList)
        createFlowStateButton = view?.findViewById(R.id.createFlowStateBtn)
        saveButton = view?.findViewById(R.id.btn_save)

    }

    private fun setupActionListeners() {

        //Floating button to create a new flow state
        createFlowStateButton?.setOnClickListener {
            FlowStateDialog
                .with(context)
                .setTitle("Add Flow")
                .setOnClickListener { flowState ->
                    val flowView = FlowStateCardView(context,flowState,{ flowViewToRemove ->
                        //If someone deletes the flow state
                        flowList?.removeView(flowViewToRemove)
                    })
                    flowList?.addView(flowView)
                }
                .build()
                .show()
        }

        //Button that saves a flow
        saveButton?.setOnClickListener {

            //Get name, count and action for this Flow
            val name = nameInput?.text.toString()
            val count = countInput?.text.toString().toInt()
            val action = actionSpinner?.selectedItemPosition!!

            //Get a list of flow states for this Flow
            val flowStates: MutableList<nu.cliffords.kyee.classes.Flow.FlowState> = mutableListOf()
            flowList?.childrenSequence()?.forEach { flowStateView ->
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
                flowList?.removeView(flowViewToRemove)
            })
            flowList?.addView(flowView)
        }
    }
}