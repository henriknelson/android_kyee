package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.card_flow_state_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.dialogs.FlowStateDialog
import nu.cliffords.kyee.classes.Flow.FlowState

/**
 * Created by Henrik Nelson on 2017-08-18.
 */


//Represents a card view that displays the configuration of one single flow state
class FlowStateCardView(context: Context, val flowStateRemoveListener: (FlowStateCardView) -> Unit) : RelativeLayout(context){

    var cardFlowState: FlowState? = null
    var editFlowStateBtn: RoundButton? = null
    var removeFlowStateBtn: RoundButton? = null

    constructor(context: Context, flowState: FlowState, flowStateRemoveListener: (FlowStateCardView) -> Unit): this(context, flowStateRemoveListener) {
        setFlowState(flowState)
    }

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.card_flow_state_view,this,true)

        setupGUI(rootView)
        setupActionListeners()
    }

    private fun setupGUI(view: View) {
        editFlowStateBtn = rootView.findViewById(R.id.editFlowStateButton)
        removeFlowStateBtn = rootView.findViewById(R.id.removeFlowStateButton)
    }

    private fun setupActionListeners() {
        editFlowStateBtn?.setOnClickListener {
            FlowStateDialog
                    .with(context,cardFlowState!!)
                    .setTitle("Redigera Flow")
                    .setOnClickListener { flowState ->
                        setFlowState(flowState)
                    }
                    .build()
                    .show()
        }

        removeFlowStateBtn?.setOnClickListener {
            flowStateRemoveListener(this)
        }
    }

    private fun setFlowState(flowState: FlowState) {
        cardFlowState = flowState
        updateGUI()
    }

    private fun updateGUI() {
        if(cardFlowState == null)
            return

        editFlowStateBtn?.setButtonBackgroundRGB(cardFlowState?.value!!)
        durationText.setText("duration: ${cardFlowState?.duration!!} ms")
        var modeStr = ""
        when(cardFlowState?.mode) {
            FlowState.FlowStateMode.COLOR -> modeStr = "mode: Color"
            FlowState.FlowStateMode.COLOR_TEMPERATURE -> modeStr = "mode: White"
            FlowState.FlowStateMode.SLEEP -> modeStr ="mode: Sleep"
        }
        modeText.setText(modeStr)
        brightText.setText("bri: ${cardFlowState?.brightness!!.toString()}")
    }


    override fun toString(): String {
        var flowMode = 7
        when(cardFlowState?.mode){
            FlowState.FlowStateMode.COLOR -> flowMode = 1
            FlowState.FlowStateMode.COLOR_TEMPERATURE -> flowMode = 2
            FlowState.FlowStateMode.SLEEP -> flowMode = 7
        }

        return "${cardFlowState!!.duration},$flowMode,${cardFlowState!!.value},${cardFlowState!!.brightness}"

    }

}