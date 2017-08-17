package nu.cliffords.android_kyee.Adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import nu.cliffords.android_kyee.Interfaces.LightViewListener
import nu.cliffords.android_kyee.Widgets.LightCardView
import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-11.
 */

class LightsAdapter(val listener: LightViewListener) : RecyclerView.Adapter<LightsAdapter.ViewHolder>() {

    private val lightsList: MutableList<Light> = mutableListOf<Light>()

    fun addLight(light: Light){
        lightsList.add(light)
        notifyDataSetChanged()
    }

    fun clearLights(){
        lightsList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LightCardView(parent.context)
        return ViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val light = lightsList[position]
        holder.bindLight(light)
    }

    override fun getItemCount() = lightsList.count()

    class ViewHolder(val lightView: LightCardView, val listener: LightViewListener) : RecyclerView.ViewHolder(lightView) {

        fun bindLight(light: Light) {
            with(light) {
                light.registerStateListener(lightView)
                lightView.setLight(light)
            }
        }
    }
}