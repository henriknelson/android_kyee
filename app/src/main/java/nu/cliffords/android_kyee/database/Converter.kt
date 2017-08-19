package nu.cliffords.android_kyee.database

import android.arch.persistence.room.TypeConverter
import nu.cliffords.android_kyee.classes.FlowStates
import nu.cliffords.kyee.classes.Flow
import nu.cliffords.kyee.classes.Flow.FlowState
import nu.cliffords.kyee.classes.Flow.FlowState.FlowStateMode

/**
 * Created by Henrik Nelson on 2017-08-19.
 */
class Converter {

        @TypeConverter
        fun fromFlowStates(flowStates: FlowStates) : String {
            return flowStates.flowStates.joinToString(";")
        }

        @TypeConverter
        fun toFlowStates(flowStateStr: String) : FlowStates {

            val flowStateList = mutableListOf<FlowState>()

            flowStateStr.split(";").forEach { flowStateStr ->
                val flowStateParams = flowStateStr.split(",")
                if(flowStateParams.size == 4){
                    val duration = flowStateParams[0].toInt()
                    var flowStateMode = FlowStateMode.COLOR
                    when(flowStateParams[1].toInt()) {
                        1 -> flowStateMode = FlowStateMode.COLOR
                        2 -> flowStateMode = FlowStateMode.COLOR_TEMPERATURE
                        7 -> flowStateMode = FlowStateMode.SLEEP
                    }
                    val value = flowStateParams[2].toInt()
                    var brightness = flowStateParams[3].toInt()
                    val flowState = FlowState(duration,flowStateMode,value,brightness)
                    flowStateList.add(flowState)
                }
            }
            return FlowStates(flowStateList)
        }

}