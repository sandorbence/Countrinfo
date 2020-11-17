package android.countrinfo.viewmodel

import android.countrinfo.InfoApplication
import android.countrinfo.model.Info
import android.countrinfo.repository.Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InfoViewModel : ViewModel() {

    private val repository: Repository

    val allInfos: LiveData<List<Info>>

    init {
        val infoDao = InfoApplication.infoDatabase.infoDao()
        repository = Repository(infoDao)
        allInfos = repository.getAllInfos()
    }

    fun insert(info: Info) = viewModelScope.launch {
        repository.insert(info)
    }

    fun delete(info: Info) = viewModelScope.launch {
        repository.delete(info)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllInfos()
    }

    fun update(info: Info) = viewModelScope.launch {
        repository.updateInfo(info)
    }

}