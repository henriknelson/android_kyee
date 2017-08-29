package nu.cliffords.android_kyee.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

/**
 * Created by Henrik Nelson on 2017-08-18.
 */


@Entity(tableName = "flows")
@TypeConverters(Converter::class)
data class Flow(
        var name: String,
        var count: Int,
        var action: Int,
        var flow_states: FlowStates?
) {
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
}

/*@Entity(tableName = "my_flows")
@TypeConverters(Converter::class)

class Flow(
            @ColumnInfo(name="name") var name: String,
            @ColumnInfo(name="count") var count: Int,
            @ColumnInfo(name="action") var action: Int,
            @ColumnInfo(name="flow_states") var flow_states: FlowStates?
    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}*/