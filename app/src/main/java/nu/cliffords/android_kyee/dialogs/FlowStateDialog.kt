package nu.cliffords.android_kyee.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import nu.cliffords.android_kyee.R
import org.jetbrains.anko.layoutInflater
import android.widget.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import nu.cliffords.android_kyee.widgets.RectangleButton
import nu.cliffords.kyee.classes.Flow.FlowState
import org.intellij.lang.annotations.Flow


/**
 * Created by Henrik Nelson on 2017-08-18.
 */

//Dialog to use when one wants to either create or modify a flow state
//Set a onSaveBtnClickListener to receive callback of how the new/modified state looks like

class FlowStateDialog private constructor(context: Context) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context, 0);
    var flowDurationEditText: EditText? = null
    var flowModeSpinner: Spinner? = null
    var selectFlowColorBtn: RectangleButton? = null
    var setFlowBrightnessSeekbar: SeekBar? = null

    var colorValue: Int = 0

    var alertDialog: AlertDialog? = null
    var onSaveButtonClickListener: ((FlowState) -> Unit) = {}

    companion object {
        //Only way to instantiate..
        fun with(context: Context): FlowStateDialog {
            return FlowStateDialog(context)
        }

        fun with(context: Context, flowState: FlowState) : FlowStateDialog {
            return FlowStateDialog(context)
                .setDuration(flowState.duration!!)
                .setMode(flowState.mode!!)
                .setValue(flowState.value!!)
                .setBrightness(flowState.brightness!!)
        }
    }

    init {
        val flowStateView = context.layoutInflater.inflate(R.layout.dialog_create_flowstate,null)

        flowDurationEditText = flowStateView?.findViewById(R.id.input_flow_duration)

        flowModeSpinner = flowStateView?.findViewById(R.id.spinner_flow_mode)

        selectFlowColorBtn = flowStateView?.findViewById(R.id.button_select_flow_color)
        selectFlowColorBtn?.setOnClickListener {
            ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Välj färg")
                    .initialColor(Color.WHITE)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(10)
                    .lightnessSliderOnly()
                    .setOnColorChangedListener { selectedColor ->
                        colorValue = selectedColor and 0x00FFFFFF
                        selectFlowColorBtn!!.setButtonBackgroundRGB(colorValue)
                    }.build()
                    .show()
        }

        setFlowBrightnessSeekbar = flowStateView?.findViewById(R.id.seekbar_flow_brightness)

        val saveBtn = flowStateView?.findViewById<Button>(R.id.btn_save)
        saveBtn?.setOnClickListener { this.onSaveButtonClick() }

        builder.setView(flowStateView)
    }

    fun setTitle(title: String) : FlowStateDialog {
        builder.setTitle(title)
        return this
    }

    fun setOnClickListener(listener: (FlowState) -> Unit): FlowStateDialog {
        this.onSaveButtonClickListener = listener
        return this
    }

    fun build() : FlowStateDialog {
        alertDialog = builder.create()
        return this
    }

    fun show() {
        alertDialog?.show()
    }

    private fun setDuration(duration: Int) : FlowStateDialog {
        flowDurationEditText?.setText(duration.toString())
        return this
    }

    private fun setMode(mode: FlowState.FlowStateMode) : FlowStateDialog {
        when(mode) {
            FlowState.FlowStateMode.COLOR -> flowModeSpinner?.setSelection(0)
            FlowState.FlowStateMode.COLOR_TEMPERATURE -> flowModeSpinner?.setSelection(1)
            FlowState.FlowStateMode.SLEEP -> flowModeSpinner?.setSelection(2)
        }
        return this
    }

    private fun setValue(value: Int): FlowStateDialog {
        colorValue = value
        selectFlowColorBtn!!.setButtonBackgroundRGB(colorValue)
        return this
    }

    private fun setBrightness(brightness: Int): FlowStateDialog {
        setFlowBrightnessSeekbar?.progress = brightness
        return this
    }

    private fun onSaveButtonClick() {

        val duration: Int = flowDurationEditText?.text.toString().toInt()

        val spinnerPosition = flowModeSpinner?.getSelectedItemPosition()
        var flowMode: FlowState.FlowStateMode = FlowState.FlowStateMode.SLEEP
        when(spinnerPosition) {
            0 -> flowMode = FlowState.FlowStateMode.COLOR
            1 -> flowMode = FlowState.FlowStateMode.COLOR_TEMPERATURE
            2 -> flowMode = FlowState.FlowStateMode.SLEEP
        }
        val colorValue = colorValue

        var brightness = if (setFlowBrightnessSeekbar != null) setFlowBrightnessSeekbar?.progress else 100
        //Brightness can't be 0..
        if(brightness == 0)
            brightness = 1

        val flowState = FlowState(if(duration != null) duration else 0,flowMode,colorValue,if(brightness != null) brightness else 100)

        onSaveButtonClickListener(flowState)

        alertDialog?.dismiss()

    }

}