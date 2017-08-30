package nu.cliffords.android_kyee.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kyleduo.switchbutton.SwitchButton
import kotlinx.android.synthetic.main.fragment_lights.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.adapters.LightsAdapter
import nu.cliffords.android_kyee.app.App
import nu.cliffords.android_kyee.interfaces.LightsContract
import nu.cliffords.android_kyee.presenters.LightsPresenter
import nu.cliffords.android_kyee.widgets.LightCardView
import nu.cliffords.kyee.classes.Light
import org.jetbrains.anko.find
import javax.inject.Inject


/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment(), LightsContract.View {

    private var mListAdapter: LightsAdapter = LightsAdapter()
    private var mPresenter: LightsPresenter? = null

    //region Dagger injections
    @Inject
    fun setPresenter(presenter: LightsPresenter) {
        this.mPresenter = presenter
    }
    //endregion

    //region Android lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_lights, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Lights"
        refreshView.setOnRefreshListener {
            mPresenter?.discoverLights(timeout=2)
        }
        val layoutManager = LinearLayoutManager(context)
        lightsListView.layoutManager = layoutManager
        lightsListView.adapter = mListAdapter
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unbind() //prevent leak in case presenter orchestrates a long running task..
    }
    //endregion


    //region View
    override fun setLights(lights: List<Light>) {
        mListAdapter?.clearLights()
        lights.sortedBy { it.name }.forEach {  light ->
            mListAdapter?.addLight(light)
        }
        mListAdapter?.notifyDataSetChanged()
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        refreshView?.isRefreshing = isRefreshing
    }
    //endregion





    /*
    //region Interfaces
    interface LightItemListener {
        fun onPowerButtonPressed(on: Boolean)
        //fun onEditFlowClick(flowToEdit: Flow)
        //fun onRemoveFlowClick(flowToRemove: Flow)
    }
    //endregion

    //region Itemlistener
    var mItemListener: LightItemListener = object : LightItemListener {

        override fun onPowerButtonPressed(on: Boolean) {
            //mPresenter?.setPower
        }
    }
    //endregion

    //region LightsAdapter
    class LightsAdapter(val lightItemListener: LightItemListener) : RecyclerView.Adapter<LightsAdapter.ViewHolder>() {

        private val mLights: MutableList<Light> = mutableListOf()

        fun addLight(light: Light) {
            mLights.add(light)
        }

        fun clearLights() {
            mLights.clear()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowView = LightCardView(parent.context)
            return ViewHolder(rowView,lightItemListener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val light = mLights[position]
            holder.bindLight(light)
        }

        override fun getItemCount() = mLights.count()

        class ViewHolder(private val lightView: LightCardView, private val itemListener: LightItemListener) : RecyclerView.ViewHolder(lightView) {

            fun bindLight(light: Light) {
                with(light) {

                    lightView.setLight(light)

                    val toggleSwitch = lightView.find<SwitchButton>(R.id.light_card_toggleSwitch)
                    toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
                        itemListener.onPowerButtonPressed(isChecked)
                    }
                    /*val flowName = flowView.find<TextView>(R.id.flowNameText)
                    flowName.text = flow.name

                    val editFlowButton = flowView.find<RoundButton>(R.id.editFlowButton)
                    editFlowButton.setOnClickListener {
                        itemListener.onEditFlowClick(flow)
                    }

                    val removeFlowButton = flowView.find<RoundButton>(R.id.removeFlowButton)
                    removeFlowButton.setOnClickListener {
                        itemListener.onRemoveFlowClick(flow)
                    }*/
                    //lightView.setLight(light)
                }
            }
        }

    }
    //endregion
    */

}