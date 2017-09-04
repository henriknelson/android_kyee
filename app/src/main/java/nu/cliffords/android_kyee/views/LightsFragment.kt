package nu.cliffords.android_kyee.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lights.*
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.contracts.LightsContract
import nu.cliffords.android_kyee.di.App
import nu.cliffords.android_kyee.presenters.LightsPresenter
import nu.cliffords.kyee.classes.Light
import javax.inject.Inject


/**
 * Created by Henrik Nelson on 2017-08-14.
 */

class LightsFragment : Fragment(), LightsContract.View {

    private var mListAdapter: LightsAdapter? = LightsAdapter()
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

    override fun onResume() {
        super.onResume()
        mPresenter?.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unbind()  //prevent leaking activity in case presenter is orchestrating a long running task
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_lights, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshView.setOnRefreshListener {
            mPresenter?.discoverLights(timeout=2)
        }
        val layoutManager = LinearLayoutManager(context)
        lightsListView.layoutManager = layoutManager
        lightsListView.adapter = mListAdapter
    }
    //endregion


    //region View
    override fun setLights(lights: List<Light>) {
        mListAdapter?.clearLights()
        mListAdapter?.addLights(lights.sortedBy { it.name })
        mListAdapter?.notifyDataSetChanged()
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        refreshView?.isRefreshing = isRefreshing
    }
    //endregion

    //region LightsAdapter
    class LightsAdapter : RecyclerView.Adapter<LightsAdapter.ViewHolder>() {

        private val mLights: MutableList<Light> = mutableListOf()

        fun addLights(lights: List<Light>){
            mLights.addAll(lights)
        }

        fun clearLights() {
            mLights.clear()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowView = LightCardView(parent.context)
            return ViewHolder(rowView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val light = mLights[position]
            holder.bindLight(light)
        }

        override fun getItemCount() = mLights.count()

        class ViewHolder(private val lightView: LightCardView) : RecyclerView.ViewHolder(lightView) {

            fun bindLight(light: Light) {
                with(light) {
                    lightView.setLight(light)
                }
            }
        }

    }
    //endregion


}