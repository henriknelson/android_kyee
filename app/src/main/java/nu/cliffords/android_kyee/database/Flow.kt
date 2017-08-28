package nu.cliffords.android_kyee.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

/**
 * Created by Henrik Nelson on 2017-08-18.
 */


@Entity(tableName = "flows")
@TypeConverters(Converter::class)
class Flow(@Ignore
    @ColumnInfo(name="name") var name: String = "",
    @ColumnInfo(name="count") var count: Int = 0,
    @ColumnInfo(name="action") var action: Int = 0,
    @ColumnInfo(name="flow_states") var flow_states: FlowStates? = FlowStates(emptyList())
    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}