package relawan.covidmonitor.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import relawan.covidmonitor.BuildConfig.BASE_URL
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

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
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    // getGlobal
    @GET("all")
    fun getGlobalData():
            Call<Global>

    // getCountry
    @GET("countries")
    fun getCountryData():
            Call<List<Country>>

    // getIndonesia
    @GET("countries/indonesia")
    fun getIndonesiaData():
            Call<Indonesia>

    // getSearch
    @GET("countries/{country}")
    fun getSearchData(
        @Path("country") country: String):
            Call<Country>

}


object CoronaApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}