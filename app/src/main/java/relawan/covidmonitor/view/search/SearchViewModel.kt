package relawan.covidmonitor.view.search

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
import java.lang.Exception

class SearchViewModel(val repository: Repository): ViewModel() {

    private val _searchData = MutableLiveData<Country>()
    val searchData : LiveData<Country>
        get() = _searchData

    fun getSearchData(country: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getSearchDataRepo(country)
                    Log.d(TAG, response.country)
                    _searchData.postValue(response)
                } catch (e: Exception) {
                    Log.d(TAG, e.message!!)
                    _searchData.postValue(null)
                }
            }
        }
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }
}