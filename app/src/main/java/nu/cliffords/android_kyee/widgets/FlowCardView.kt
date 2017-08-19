package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.classes.Helpers
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.database.FlowDatabase
import nu.cliffords.android_kyee.fragments.FlowFragment

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowCardView(context: Context, val flow: Flow, val flowDeleteListener: (Int) -> Unit) : RelativeLayout(context){

    var nameView: TextView? = null
    var editFlowButton: RoundButton? = null
    var removeFlowButton: RoundButton? = null

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.card_flow_view,this,true)
        updateGUI(rootView)
        /*setOnClickListener {
            LightManager.instance.getLights({ lights ->
                lights.forEach { light ->
                    var flowAction = Light.FlowAction.LED_RECOVERY
                    when(flow.action){
                        0 -> flowAction = Light.FlowAction.LED_RECOVERY
                        1 -> flowAction = Light.FlowAction.LED_STAY
                        2 -> flowAction = Light.FlowAction.LED_TURNOFF
                    }

                    var stateList: MutableList<FlowState> = mutableListOf()
                    flow.flow_states.split(";").forEach { flowStateStr ->
                        val flowStateParams = flowStateStr.split(",")
                        if(flowStateParams.size == 4) {
                            val duration = flowStateParams[0].toInt()
                            var flowStateMode = FlowState.FlowStateMode.COLOR
                            when (flowStateParams[1].toInt()) {
                                1 -> flowStateMode = FlowState.FlowStateMode.COLOR
                                2 -> flowStateMode = FlowState.FlowStateMode.COLOR_TEMPERATURE
                                7 -> flowStateMode = FlowState.FlowStateMode.SLEEP
                            }
                            val value = flowStateParams[2].toInt()
                            var brightness = flowStateParams[3].toInt()
                            val flowState = FlowState(duration, flowStateMode, value, brightness)
                            stateList.add(flowState)
                        }
                    }
                    light.startColorFlow(flow.count,flowAction,stateList.toList(),{

                    })
                }
            })
        }*/

    }

    private fun updateGUI(view: View) {
        nameView = view?.findViewById(R.id.flowNameText)
        nameView?.text = flow.name

        editFlowButton = view?.findViewById(R.id.editFlowButton)
        editFlowButton?.setOnClickListener {
            val flowFragment: Fragment = Helpers.instanceOf<FlowFragment>("id" to flow.id!!)
            (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,flowFragment).addToBackStack("flow_fragment").commit()
        }

        removeFlowButton = view?.findViewById(R.id.removeFlowButton)
        removeFlowButton?.setOnClickListener {
            flowDeleteListener(flow.id)
        }
    }


}