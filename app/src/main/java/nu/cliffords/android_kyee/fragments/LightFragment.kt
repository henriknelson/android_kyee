package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.android_kyee.R

/**
 * Created by henrik.nelson2 on 2017-08-17.
 */
class LightFragment(): Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_light, container, false)



        return rootView
    }
}