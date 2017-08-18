package nu.cliffords.android_kyee.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import nu.cliffords.android_kyee.R
import org.jetbrains.anko.layoutInflater
import android.widget.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import nu.cliffords.android_kyee.widgets.SelectColorButton
import nu.cliffords.kyee.classes.FlowState
import nu.cliffords.kyee.classes.Light


/**
 * Created by Henrik Nelson on 2017-08-18.
 */
class FlowStateDialog private constructor(context: Context) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context, 0);
    var flowDurationEditText: EditText? = null
    var flowModeSpinner: Spinner? = null
    var selectFlowColorBtn: SelectColorButton? = null
    var setFlowBrightnessSeekbar: SeekBar? = null

    var colorValue: Int = 0

    var alertDialog: AlertDialog? = null
    var saveListener: ((FlowState) -> Unit) = {}



    companion object {
        fun with(context: Context): FlowStateDialog {
            return FlowStateDialog(context)
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
                    .density(12)
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
        this.saveListener = listener
        return this
    }

    fun build() : FlowStateDialog {
        alertDialog = builder.create()
        return this
    }

    fun show() {
        alertDialog?.show()
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
        val brightness = if (setFlowBrightnessSeekbar != null) setFlowBrightnessSeekbar?.progress else 100
        val flowState = FlowState(if(duration != null) duration else 0,flowMode,colorValue,if(brightness != null) brightness else 100)
        saveListener(flowState)
        alertDialog?.dismiss()
    }

}