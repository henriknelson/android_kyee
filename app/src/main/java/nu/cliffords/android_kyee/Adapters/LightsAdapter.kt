package nu.cliffords.android_kyee.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.light_card.view.*
import nu.cliffords.android_kyee.Interfaces.LightsAdapterListener
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.Light

/**
 * Created by Henrik Nelson on 2017-08-11.
 */

class LightsAdapter(val listener: LightsAdapterListener) : RecyclerView.Adapter<LightsAdapter.ViewHolder>() {

    private val lightsList: MutableList<Light> = mutableListOf<Light>()

    fun addLight(light: Light){
        lightsList.add(light)
        notifyDataSetChanged()
    }

    fun clearLights(){
        lightsList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.light_card, parent, false)
        return ViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindLight(lightsList[position])
    }

    override fun getItemCount() = lightsList.count()

    class ViewHolder(val view: View, val listener: LightsAdapterListener) : RecyclerView.ViewHolder(view) {

        fun bindLight(light: Light) {
            with(light) {
                view.lightNameView.text = light.location
                view.lightToggleSwitch.isChecked = light.power
                view.lightToggleSwitch.setOnCheckedChangeListener{ buttonView, isChecked -> listener.onToggle(light,isChecked)  }
                /*view.lightSeekBar.progress = light.state.bri
                view.lightSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        light.state.bri = p1
                        listener.onBrighnessChange(light,p1)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                } )*/
            }
        }
    }
}