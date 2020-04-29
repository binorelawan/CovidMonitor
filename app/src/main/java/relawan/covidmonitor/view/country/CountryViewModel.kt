package relawan.covidmonitor.view.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.model.CountryItem
import relawan.covidmonitor.network.CoronaApi
import relawan.covidmonitor.repository.CountryRepoCallback
import relawan.covidmonitor.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryViewModel(private val repository: Repository): ViewModel() {

    private val _countryData = MutableLiveData<List<Country>>()
    val countryData : LiveData<List<Country>>
        get() = _countryData

    init {
        getCountryData()
//        noRepository()
    }

    fun getCountryData() {

        repository.getCountryDataRepo(object : CountryRepoCallback {
            override fun onError() {
                Log.d(TAG, "country error")
                _countryData.value = null
            }

            override fun onSuccess(country: List<Country>) {
                Log.d(TAG, "${country.size}")
                _countryData.value = country
            }

        })
    }


    companion object {
        private val TAG = CountryViewModel::class.java.simpleName
    }
}