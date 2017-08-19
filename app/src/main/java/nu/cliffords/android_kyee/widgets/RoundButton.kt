package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import nu.cliffords.android_kyee.R

/**
 * Created by Henrik Nelson on 2017-08-16.
 */

class RoundButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes){

    private var myImageView: ImageView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.button_round,this,true)
        this.isClickable = true
        myImageView = findViewById(R.id.imgRoundButton)
        if(attrs != null) {
            val attributes = getContext().obtainStyledAttributes(attrs,R.styleable.RoundButton)
            val myDrawable = attributes.getDrawable(R.styleable.RoundButton_android_drawable)
            myImageView?.setImageDrawable(myDrawable)
            attributes.recycle()
        }
    }

    fun setButtonBackground(color: Int){
        try {
            myImageView!!.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        }catch (e:Exception){
            Log.e("android_kyee","Could not set color button color")
        }
    }

    fun setButtonBackgroundRGB(color: Int){
        setButtonBackground(color or 0xFF000000.toInt())
        this.invalidate()
    }

}