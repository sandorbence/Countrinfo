package android.countrinfo

import android.app.Application
import android.countrinfo.database.InfoDatabase
import androidx.room.Room

class InfoApplication : Application() {
    companion object {
        lateinit var infoDatabase: InfoDatabase
            private set
    }

    override fun onCreate(){
        super.onCreate()

        infoDatabase= Room.databaseBuilder(
            applicationContext,
            InfoDatabase::class.java,
            "info_database"
        ).build()
    }

}