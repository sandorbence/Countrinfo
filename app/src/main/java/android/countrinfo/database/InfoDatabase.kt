package android.countrinfo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomInfo::class]
)
abstract class InfoDatabase : RoomDatabase() {

    abstract fun infoDao(): InfoDao

}