package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.widgets.LightCardView
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.classes.LightManager
import nu.cliffords.kyee.interfaces.LightStateChangeListener

/**
 * Created by Henrik Nelson on 2017-08-17.
 */

class LightFragment : Fragment(), LightStateChangeListener {

    var myLight: Light? = null
    var lightContainerLayout: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_light, container, false)
        setupGUI(rootView!!)

        if(arguments?.getInt("id") != null) {
            val lightId = arguments.getString("id")
            val light = LightManager.instance.getLightFromId(lightId)
            if(light != null)
                setLight(light)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Light"
    }

    private fun setupGUI(view: View){
        lightContainerLayout = view.findViewById(R.id.light_containerLayout)
    }

    private fun setLight(light: Light) {
        this.myLight = light
        val lightCardView = LightCardView(context)
        lightCardView.setLight(light)
        light.registerStateListener(lightCardView)
        light.registerStateListener(this)
        lightContainerLayout?.addView(lightCardView)
        updateGUI()
    }

    private fun updateGUI(){

    }

    override fun onStateChanged(params: Map<String, Any>) {

    }
}