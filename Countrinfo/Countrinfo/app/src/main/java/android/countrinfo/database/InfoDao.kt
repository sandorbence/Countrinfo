package android.countrinfo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInfo(info: RoomInfo)

    @Query("SELECT * FROM info")
    fun getAllInfos(): LiveData<List<RoomInfo>>

    @Query("SELECT * FROM info WHERE title==:name")
    fun getInfobyName(name: String?): RoomInfo?

    @Query("DELETE FROM info")
    fun deleteAllInfos()

    @Update
    fun updateInfo(info: RoomInfo): Int

    @Delete
    fun deleteInfo(info: RoomInfo)

}