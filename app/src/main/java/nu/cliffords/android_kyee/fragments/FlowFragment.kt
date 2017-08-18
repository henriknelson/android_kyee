package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.jmedeisis.draglinearlayout.DragLinearLayout
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.dialogs.FlowStateDialog
import nu.cliffords.android_kyee.widgets.FlowStateCardView
import nu.cliffords.kyee.classes.FlowState
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager
import org.jetbrains.anko.childrenSequence

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

//Represents one 'Flow'

class FlowFragment: Fragment() {

    var nameInput: EditText? = null
    var countInput: EditText? = null
    var flowList: DragLinearLayout? = null
    var createFlowStateButton: FloatingActionButton? = null
    var saveButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_flow, container, false)
        setupGUI(rootView)
        return rootView
    }

    fun setupGUI(view: View?) {

        nameInput = view?.findViewById<EditText>(R.id.input_name)
        countInput = view?.findViewById<EditText>(R.id.input_count)
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

            var flowStates: MutableList<FlowState> = mutableListOf<FlowState>()
            flowList?.childrenSequence()?.forEach { flowStateView ->
                val flowState = (flowStateView as FlowStateCardView).cardFlowState
                if (flowState != null)
                    flowStates.add(flowState)
            }
            
            val flowStatesStr = flowStates.joinToString(";")

            val flow = Flow(name,count,0,flowStatesStr)

            FlowDatabase.getDatabase(context).flowDao().insertFlow(flow)
            fragmentManager.popBackStackImmediate()

            /*LightManager.instance.getLights({ lights ->
                lights.forEach { light ->
                    light.startColorFlow(count, Light.FlowStopAction.LED_RECOVERY,flowStates,{

                    })

                }
            })*/
        }



    }

}