package nu.cliffords.android_kyee.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Henrik Nelson on 2017-08-18.
 */
@Database(entities = arrayOf(Flow::class), version = 1)
abstract class FlowDatabase : RoomDatabase() {
    abstract fun flowDao(): FlowDao

    companion object {
        private var INSTANCE: FlowDatabase? = null
        @JvmStatic fun getDatabase(context: Context): FlowDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, FlowDatabase::class.java).allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

}
