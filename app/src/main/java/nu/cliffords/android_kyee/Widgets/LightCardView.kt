package nu.cliffords.android_kyee.Widgets

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import kotlinx.android.synthetic.main.light_card.view.*
import nu.cliffords.android_kyee.Interfaces.LightViewListener
import nu.cliffords.android_kyee.R
import nu.cliffords.kyee.classes.Light
import nu.cliffords.kyee.interfaces.LightStateChangeListener

/**
 * Created by Henrik Nelson on 2017-08-15.
 */

class LightCardView(context: Context) : RelativeLayout(context), LightStateChangeListener {

    var cardLight: Light? = null
    var cardListener: LightViewListener? = null

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.light_card,this,true)
    }

    fun setLightViewListener(listener: LightViewListener) {
        cardListener = listener
    }

    fun setLight(light:Light) {
        cardLight = light
        updateGui()
    }

    fun updateGui() {

        if((cardLight == null) || (cardListener == null))
            return

        lightNameView.text = cardLight!!.name
        lightToggleSwitch.isChecked = cardLight!!.power
        lightToggleSwitch.setOnCheckedChangeListener { buttonView, isChecked -> cardListener!!.onToggle(cardLight!!, isChecked) }
        lightSeekBar.progress = cardLight!!.brightness
        lightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                cardLight!!.brightness = p1
                cardListener!!.onBrighnessChange(cardLight!!, p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        changeColorBtn.setOnClickListener {
            cardListener!!.onClick(cardLight!!)
        }
    }

    override fun onStateChanged(params: Map<String, Any>) {
        updateGui()
    }
}