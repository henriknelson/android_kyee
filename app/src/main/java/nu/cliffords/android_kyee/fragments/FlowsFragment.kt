package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.android_kyee.R
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.widget.Toast
import com.jmedeisis.draglinearlayout.DragLinearLayout
import nu.cliffords.android_kyee.classes.Helpers
import nu.cliffords.android_kyee.database.Flow
import nu.cliffords.android_kyee.database.FlowDatabase


/**
 * Created by Henrik Nelson on 2017-08-18.
 */

class FlowsFragment(): Fragment() {

    private var flowListView: DragLinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_flows, container, false)
        setupGUI(rootView)
        return rootView
    }

    fun setupGUI(view:View?) {
        flowListView = view?.findViewById<DragLinearLayout>(R.id.flowsList)
        val fab = view?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {

            /*val myFlow = Flow()
            myFlow.name = "PoliceSirens"
            myFlow.count = 100
            myFlow.action = 0

            val state1 = Flow.FlowState()
            state1.duration = 200
            state1.mode = 1
            state1.value = Helpers.getIntFromColor(255,0,0)
            state1.brightness = 10

            val state2 = Flow.FlowState()
            state2.duration = 200
            state2.mode = 1
            state2.value = Helpers.getIntFromColor(0,0,255)
            state2.brightness = 10

            myFlow.flow_expression = 1// arrayOf(state1,state2)

            FlowDatabase.getDatabase(context).flowDao().insertAll(myFlow)*/

            fragmentManager.beginTransaction().replace(R.id.frame_container,FlowFragment()).addToBackStack("flow_fragment").commit()

        }
    }

    override fun onResume() {
        super.onResume()
        val myDb = FlowDatabase.getDatabase(context)
        myDb.flowDao().getAll().forEach{ flow ->
            Log.d("android_kyee",flow.name)
        }
    }

}