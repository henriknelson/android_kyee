package nu.cliffords.android_kyee.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import nu.cliffords.android_kyee.widgets.LightCardView
import nu.cliffords.kyee.classes.Light

/**
 * Created by Henrik Nelson on 2017-08-30.
 */

class LightsAdapter: RecyclerView.Adapter<LightsAdapter.ViewHolder>() {

    private val lightsList: MutableList<Light> = mutableListOf()

    fun addLight(light: Light){
        lightsList.add(light)
    }

    fun clearLights(){
        lightsList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val light = lightsList[viewType]
        val view = LightCardView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val light = lightsList[position]
        holder.bindLight(light)
    }

    override fun getItemCount() = lightsList.count()

    class ViewHolder(private val lightView: LightCardView) : RecyclerView.ViewHolder(lightView) {

        fun bindLight(light: Light) {
            with(light) {
                lightView.setLight(light)
            }
        }
    }
}