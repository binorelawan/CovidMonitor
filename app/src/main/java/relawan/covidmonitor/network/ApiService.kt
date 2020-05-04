package relawan.covidmonitor.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import relawan.covidmonitor.BuildConfig.BASE_URL
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


fun getInterceptor(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okhttp = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    return okhttp
}

private val retrofit = Retrofit.Builder()
    .client(getInterceptor())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    // getGlobal
    @GET("all")
    suspend fun getGlobalData():
            Global

    // getCountry
    @GET("countries")
    suspend fun getCountryData():
            List<Country>

    // getIndonesia
    @GET("countries/indonesia")
    suspend fun getIndonesiaData():
            Indonesia

    // getSearch
    @GET("countries/{country}")
    suspend fun getSearchData(
        @Path("country") country: String):
            Country

}


object CoronaApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}