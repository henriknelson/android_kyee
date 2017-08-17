package nu.cliffords.android_kyee.Widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import nu.cliffords.android_kyee.R
import android.R.attr.onClick
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent.KEYCODE_DPAD_CENTER
import android.view.MotionEvent
import android.R.attr.onClick
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import kotlinx.android.synthetic.main.color_button.view.*
import nu.cliffords.android_kyee.R.id.imageView
import android.support.v4.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.widget.ImageView
import android.R.attr.name
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import org.jetbrains.anko.backgroundDrawable


/**
 * Created by Henrik Nelson on 2017-08-16.
 */

class SetColorButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes){

    var clickListener: OnClickListener? = null
    val myImageView = ImageView(context)

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.color_button,this,true)
        this.isClickable = true

        val myDrawable = context.resources.getDrawable(R.drawable.ic_lightbulb)
        myImageView.setImageDrawable(myDrawable)
        addView(myImageView)

        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
        lp.addRule(RelativeLayout.CENTER_IN_PARENT)
        myImageView.layoutParams = lp
        myImageView.layoutParams.height = 115
        myImageView.layoutParams.width = 115
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
            val myDrawable = context.resources.getDrawable(R.drawable.ic_lightbulb)
            myDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            myImageView.setImageDrawable(myDrawable)
            Log.i("android_kyee","puss")

        }catch (e:Exception){
            Log.e("android_kyee","Could not set color button color")
        }
    }

}