package android.countrinfo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class RoomInfo(
    @PrimaryKey
    val title: String,
    var description: String
)