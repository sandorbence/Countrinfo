package android.countrinfo.repository

import android.countrinfo.database.InfoDao
import android.countrinfo.database.RoomInfo
import android.countrinfo.model.Info
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val infoDao: InfoDao) {

    fun getAllInfos(): LiveData<List<Info>> {
        return infoDao.getAllInfos()
            .map { roomInfos ->
                roomInfos.map { roomInfo ->
                    roomInfo.toDomainModel()
                }
            }
    }

    suspend fun insert(info: Info) = withContext(Dispatchers.IO) {
        infoDao.insertInfo(info.toRoomModel())
    }

    private fun RoomInfo.toDomainModel(): Info {
        return Info(
            title = title,
            description = description
        )
    }

    private fun Info.toRoomModel(): RoomInfo {
        return RoomInfo(
            title = title,
            description = description
        )
    }

    suspend fun delete(info: Info) = withContext(Dispatchers.IO) {
        val roomInfo = infoDao.getInfobyName(info.title) ?: return@withContext
        infoDao.deleteInfo(roomInfo)
    }

    suspend fun deleteAllInfos() = withContext(Dispatchers.IO) {
        infoDao.deleteAllInfos()
    }

    suspend fun updateInfo(info: Info) = withContext(Dispatchers.IO) {
        infoDao.updateInfo(info.toRoomModel())
    }

}