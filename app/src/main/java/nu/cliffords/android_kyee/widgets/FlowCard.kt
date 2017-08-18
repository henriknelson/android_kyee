package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.dialogs.FlowStateDialog

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowCard(context: Context, val flow: Flow) : RelativeLayout(context){

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.card_flow_view,this,true)
        this.isClickable = true
        setOnClickListener {
        }
        updateGUI(rootView)
    }

    private fun updateGUI(view: View) {

    }

}