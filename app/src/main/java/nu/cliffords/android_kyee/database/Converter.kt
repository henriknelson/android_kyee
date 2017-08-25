package nu.cliffords.android_kyee.database

/**
 * Created by Henrik Nelson on 2017-08-19.
 */
class Converter {
/*
        @TypeConverter
        fun fromFlowStates(flowStates: FlowStates) : String {
            return flowStates.flowStates.joinToString(";")
        }

        @TypeConverter
        fun toFlowStates(flowStatesStr: String) : FlowStates {

            val flowStateList = mutableListOf<FlowState>()

            flowStatesStr.split(";").forEach { flowStateStr ->
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
                    val brightness = flowStateParams[3].toInt()
                    val flowState = FlowState(duration,flowStateMode,value,brightness)
                    flowStateList.add(flowState)
                }
            }
            return FlowStates(flowStateList)
        }
*/
}