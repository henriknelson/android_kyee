package nu.cliffords.android_kyee.database

import android.arch.persistence.room.*

/**
 * Created by Henrik Nelson on 2017-08-18.
 */

@Dao
interface FlowDao {

    @Query("SELECT * FROM flows")
    fun getAll(): List<Flow>

    @Query("SELECT * FROM flows WHERE id LIKE :arg0")
    fun get(id: Int): Flow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlow(flow:Flow)

    @Insert
    fun insertAll(vararg flows: Flow)

    @Delete
    fun delete(flow: Flow)

}
