package nu.cliffords.android_kyee.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Henrik Nelson on 2017-08-18.
 */


@Entity(tableName = "flows")
class Flow(
    @ColumnInfo(name="name") var name: String = "",
    @ColumnInfo(name="count") var count: Int = 0,
    @ColumnInfo(name="action") var action: Int = 0,
    @ColumnInfo(name="flow_states") var flow_states: String = ""
    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}