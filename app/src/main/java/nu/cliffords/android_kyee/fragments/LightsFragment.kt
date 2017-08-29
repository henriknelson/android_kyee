package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lights.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.adapters.LightsAdapter
import nu.cliffords.android_kyee.app.App
import nu.cliffords.android_kyee.interfaces.LightsInteractor
import nu.cliffords.android_kyee.presenters.LightsPresenter
import nu.cliffords.kyee.classes.Light
import javax.inject.Inject


/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment(), LightsInteractor.View {

    private val lightsAdapter: LightsAdapter = LightsAdapter()
    private var presenter: LightsPresenter? = null

    @Inject
    fun setPresenter(presenter: LightsPresenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).component.inject(this)
        presenter?.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_lights, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Lights"
        refreshView.setOnRefreshListener {
            presenter?.discoverLights(timeout=2)
        }
        val layoutManager = LinearLayoutManager(context)
        lightsListView.layoutManager = layoutManager
        lightsListView.adapter = lightsAdapter
    }

    override fun setLights(lights: List<Light>) {
        lightsAdapter.clearLights()
        lights.sortedBy { it.name }.forEach {  light ->
            lightsAdapter.addLight(light)
        }
        lightsAdapter.notifyDataSetChanged()
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        refreshView?.isRefreshing = isRefreshing
    }

}