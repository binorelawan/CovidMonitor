package relawan.covidmonitor.view.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.repository.Repository

class CountryViewModel(private val repository: Repository): ViewModel() {

    private val _countryData = MutableLiveData<List<Country>>()
    val countryData : LiveData<List<Country>>
        get() = _countryData

    init {
        getCountryData()
    }

    fun getCountryData() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getCountryDataRepo()
                    Log.d(TAG, response.size.toString())
                    _countryData.postValue(response)
                } catch (e: Exception) {
                    Log.d(TAG, e.message!!)
                    _countryData.postValue(null)
                }
            }
        }
    }


    companion object {
        private val TAG = CountryViewModel::class.java.simpleName
    }
}