package relawan.covidmonitor.repository

import relawan.covidmonitor.network.ApiService
import relawan.covidmonitor.network.CoronaApi

class Repository  {

    private var apiService: ApiService = CoronaApi.retrofitService

    // getGlobalData
    suspend fun getGlobalDataRepo() = apiService.getGlobalData()

    // getCountryData
    suspend fun getCountryDataRepo() = apiService.getCountryData()

    // getSearchData
    suspend fun getSearchDataRepo(country: String) = apiService.getSearchData(country)

    // getIndonesiaData
    suspend fun getIndonesiaDataRepo() = apiService.getIndonesiaData()



    companion object {
        private val TAG = Repository::class.java.simpleName
    }
}



