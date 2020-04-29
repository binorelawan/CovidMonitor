package relawan.covidmonitor.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.model.CountryItem
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import relawan.covidmonitor.network.ApiService
import relawan.covidmonitor.network.CoronaApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository  {

    private var apiService: ApiService = CoronaApi.retrofitService

    // getGlobalData
    fun getGlobalDataRepo(callback: GlobalRepoCallback): MutableLiveData<Global> {

        val globalData = MutableLiveData<Global>()
        val call = apiService.getGlobalData()
        call.enqueue(object : Callback<Global> {
            override fun onFailure(call: Call<Global>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(call: Call<Global>, response: Response<Global>) {

                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {global ->
                        callback.onSuccess(global)
                    } ?: callback.onError()
                } else {
                    callback.onError()
                }
            }

        })

        return globalData
    }

    // getCountryData
    fun getCountryDataRepo(callback: CountryRepoCallback): MutableLiveData<Country> {

        val countryData = MutableLiveData<Country>()
        val call = apiService.getCountryData()
        call.enqueue(object : Callback<List<Country>> {
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.d(TAG, t.message)
                callback.onError()
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {

                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {country ->
                        callback.onSuccess(country)
                    } ?: callback.onError()
                } else {
                    callback.onError()
                }
            }

        })

        return countryData
    }

    // getSearchData
    fun getSearchDataRepo(country: String, callback: SearchRepoCallback): MutableLiveData<Country> {

        val searchData = MutableLiveData<Country>()
        val call = apiService.getSearchData(country)
        call.enqueue(object : Callback<Country> {
            override fun onFailure(call: Call<Country>, t: Throwable) {
                Log.d(TAG, t.message)
                callback.onError()
            }

            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {country ->
                        callback.onSuccess(country)
                    } ?: callback.onError()
                } else {
                    callback.onError()
                }
            }

        })

        return searchData
    }

    // getIndonesiaData
    fun getIndonesiaDataRepo(callback: IndonesiaRepoCallback): MutableLiveData<Indonesia> {

        val indonesiaData = MutableLiveData<Indonesia>()
        val call = apiService.getIndonesiaData()
        call.enqueue(object : Callback<Indonesia> {
            override fun onFailure(call: Call<Indonesia>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(call: Call<Indonesia>, response: Response<Indonesia>) {

                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {indonesia ->
                        callback.onSuccess(indonesia)
                    } ?: callback.onError()
                } else {
                    callback.onError()
                }
            }

        })

        return indonesiaData
    }



    companion object {
        private val TAG = Repository::class.java.simpleName
    }
}

interface GlobalRepoCallback {
    fun onError()
    fun onSuccess(global: Global)
}

interface CountryRepoCallback {
    fun onError()
    fun onSuccess(country: List<Country>)
}

interface SearchRepoCallback {
    fun onError()
    fun onSuccess(country: Country)
}

interface IndonesiaRepoCallback {
    fun onError()
    fun onSuccess(indonesia: Indonesia)
}

