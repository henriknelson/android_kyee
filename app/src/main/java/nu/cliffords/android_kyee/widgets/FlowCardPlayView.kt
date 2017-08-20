package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.card_flow_play_view.view.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow



/**
 * Created by Henrik Nelson on 2017-08-20.
 */

class FlowCardPlayView(context: Context) : RelativeLayout(context){

    private var isPlaying: Boolean = false
    var flow: Flow? = null
    var flowPlayListener: (Flow) -> Unit = {}
    var flowPausListener: (Flow) -> Unit = {}

    var playIcon = resources.getDrawable(R.drawable.ic_play_flow)
    var pausIcon = resources.getDrawable(R.drawable.ic_pause_flow)


    constructor(context: Context, flow: Flow,flowPlayListener: (Flow) -> Unit, flowPauseListener: (Flow) -> Unit) : this(context) {
        this.flow = flow
        this.flowPlayListener = flowPlayListener
        this.flowPausListener = flowPauseListener
        updateGUI()
        toggleImage()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.card_flow_play_view,this,true)

    }

    private fun updateGUI() {
        if(flow != null)
            flowNameText.text = flow!!.name

        playFlowButton.setOnClickListener {
            if(flow != null)
            {
                if(!isPlaying)
                {
                    flowPlayListener(flow!!)
                    isPlaying = true
                    toggleImage()
                }
                else
                {
                    flowPausListener(flow!!)
                    isPlaying = false
                    toggleImage()
                }
            }

        }
    }

    private fun toggleImage() {
        if(!isPlaying)
            playFlowButton.setImageDrawable(playIcon)
        else
            playFlowButton.setImageDrawable(pausIcon)
    }


}