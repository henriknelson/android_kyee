package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.card_flow_state_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.classes.FlowState
import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-18.
 */
class FlowCardView(context: Context) : RelativeLayout(context){

    var cardFlowState: FlowState? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.card_flow_state_view,this,true)
        this.isClickable = true
        setOnClickListener {
            Toast.makeText(context,this.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun setFlowState(flowState: FlowState) {
        cardFlowState = flowState
        updateGUI()
    }

    fun updateGUI() {
        if(cardFlowState == null)
            return

        flowStateColor.setButtonBackgroundRGB(cardFlowState?.value!!)
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

        return "${cardFlowState!!.duration},$flowMode,${cardFlowState!!.value},${cardFlowState!!.brightness}}"

    }

}