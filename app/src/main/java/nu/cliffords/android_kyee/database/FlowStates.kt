package nu.cliffords.android_kyee.database

import nu.cliffords.kyee.classes.Flow.FlowState

/**
 * Created by Henrik Nelson on 2017-08-19.
 */

// Small hack to be able to persist a list of flow states in app database
// This class can be converted to and from a string (using type-converter in Converter class


class FlowStates(val flowStates: List<FlowState>) {
}