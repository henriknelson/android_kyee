package nu.cliffords.android_kyee.database

/**
 * Created by Henrik Nelson on 2017-08-18.

@Database(entities = arrayOf(Flow::class), version = 1)
abstract class FlowDatabase : RoomDatabase() {

    abstract fun flowDao(): FlowDao

    companion object {
        private var INSTANCE: FlowDatabase? = null
        @JvmStatic fun getDatabase(context: Context): FlowDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, FlowDatabase::class.java,"android_kyee").allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }

    }

}
*/