package nu.cliffords.android_kyee.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

@Dao
interface FlowDao {

    @Query("SELECT * FROM flows")
    fun getAll(): List<Flow>

    @Insert
    fun insertFlow(flow:Flow)

    @Insert
    fun insertAll(vararg flows: Flow)

    @Delete
    fun delete(flow: Flow)

}
