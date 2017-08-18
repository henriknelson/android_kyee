package nu.cliffords.android_kyee.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Henrik Nelson on 2017-08-18.
 */


@Entity(tableName = "flow")
class Flow {

    /*class FlowState {
        var duration: Int = 0
        var mode: Int = 0
        var value: Int = 0
        var brightness: Int = 0
    }*/

    @PrimaryKey//(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var count: Int = 0
    var action: Int = 0
    var flow_expression: Int = 0

}