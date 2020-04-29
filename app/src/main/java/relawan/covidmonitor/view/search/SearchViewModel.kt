package relawan.covidmonitor.view.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.repository.Repository
import relawan.covidmonitor.repository.SearchRepoCallback

class SearchViewModel(val repository: Repository): ViewModel() {

    private val _searchData = MutableLiveData<Country>()
    val searchData : LiveData<Country>
        get() = _searchData

    fun getSearchData(country: String) : LiveData<Country> {

        return repository.getSearchDataRepo(country, object : SearchRepoCallback {
            override fun onError() {
                Log.d(TAG, "search error")
                _searchData.value = null
            }

            override fun onSuccess(country: Country) {
                Log.d(TAG, country.country)
                _searchData.value = country
            }

        })
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }
}