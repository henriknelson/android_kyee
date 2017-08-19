package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.card_flow_state_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.dialogs.FlowStateDialog
import nu.cliffords.kyee.classes.Flow.FlowState

/**
 * Created by Henrik Nelson on 2017-08-18.
 */


//Represents a card view that displays the configuration of one single flow state
class FlowStateCardView(context: Context): RelativeLayout(context){

    var cardFlowState: FlowState? = null
    private var editFlowStateBtn: RoundButton? = null
    private var removeFlowStateBtn: RoundButton? = null
    private var flowStateRemoveListener: (FlowStateCardView) -> Unit = {}

    constructor(context: Context, flowState: FlowState, flowStateRemoveListener: (FlowStateCardView) -> Unit): this(context) {
        this.flowStateRemoveListener = flowStateRemoveListener
        setFlowState(flowState)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.card_flow_state_view,this,true)
        setupGUI()
        setupActionListeners()
    }

    private fun setupGUI() {
        editFlowStateBtn = rootView.findViewById(R.id.editFlowStateButton)
        removeFlowStateBtn = rootView.findViewById(R.id.removeFlowStateButton)
    }

    private fun setupActionListeners() {
        editFlowStateBtn?.setOnClickListener {
            FlowStateDialog
                    .with(context,cardFlowState!!)
                    .setTitle("Edit Flow")
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
        durationText.text = "duration: ${cardFlowState?.duration!!} ms"
        var modeStr = ""
        when(cardFlowState?.mode) {
            FlowState.FlowStateMode.COLOR -> modeStr = "mode: Color"
            FlowState.FlowStateMode.COLOR_TEMPERATURE -> modeStr = "mode: White"
            FlowState.FlowStateMode.SLEEP -> modeStr ="mode: Sleep"
        }
        modeText.text = modeStr
        brightText.text = "bri: ${cardFlowState?.brightness!!}"
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