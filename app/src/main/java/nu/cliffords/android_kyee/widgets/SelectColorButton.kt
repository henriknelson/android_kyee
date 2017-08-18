package nu.cliffords.android_kyee.widgets

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import nu.cliffords.android_kyee.R

/**
 * Created by Henrik Nelson on 2017-08-18.
 */
class SelectColorButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes){

    var clickListener: OnClickListener? = null
    var myImageView: ImageView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.select_color_button,this,true)
        this.isClickable = true

        myImageView = findViewById(R.id.imgSelectLightBulbView)

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(clickListener != null)
                clickListener!!.onClick(this);
        }
        return super.dispatchTouchEvent(event);
    }

    override fun setOnClickListener(listener: View.OnClickListener?) {
        this.clickListener = listener
    }

    fun setButtonBackground(color: Int){
        try {
            myImageView!!.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }catch (e:Exception){
            Log.e("android_kyee","Could not select color button color")
        }
    }

    fun setButtonBackgroundRGB(color: Int){
        setButtonBackground(color or 0xFF000000.toInt())
    }

}