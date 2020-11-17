package android.countrinfo.network

import android.countrinfo.events.ErrorEvent
import android.countrinfo.events.GetCountriesResponseEvent
import android.countrinfo.events.GetCountrybyNameResponseEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class CountriesInteractor {

    private val countriesApi: CountriesAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(CountriesAPI.ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.countriesApi = retrofit.create(CountriesAPI::class.java)
    }

    fun getCountries() {
        Thread {
            try {
                val result = countriesApi.getCountries().execute().body()!!
                EventBus.getDefault().post(GetCountriesResponseEvent(result))
            } catch (e: Exception) {
                e.printStackTrace()
                EventBus.getDefault().post(ErrorEvent(e.toString()))
            }
        }.start()

    }

    fun getCountryByName(name: String) {
        Thread {
            try {
                val result = countriesApi.getCountryByName(name).execute().body()!!
                EventBus.getDefault().post(GetCountrybyNameResponseEvent(result))
            } catch (e: Exception) {
                e.printStackTrace()
                EventBus.getDefault().post(ErrorEvent(e.toString()))
            }
        }.start()

    }
}