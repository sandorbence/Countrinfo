package android.countrinfo.network

import android.countrinfo.model.Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesAPI {

    companion object {
        const val ENDPOINT_URL = "https://restcountries.eu/rest/v2/"
    }

    @GET("all")
    fun getCountries(): Call<List<Base>>

    @GET("name/{name}")
    fun getCountryByName(
        @Path("name") name: String
    ): Call<List<Base>>
}
