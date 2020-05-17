package relawan.covidmonitor.view.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import relawan.covidmonitor.repository.Repository

class SplashViewModel(private val repository: Repository) : ViewModel() {

    private val _globalData = MutableLiveData<Global>()
    val globalData : LiveData<Global>
        get() = _globalData

    private val _indonesiaData = MutableLiveData<Indonesia>()
    val indonesiaData : LiveData<Indonesia>
        get() = _indonesiaData


    init {
        getGlobalData()
        getIndonesiaData()
    }

    fun getGlobalData() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getGlobalDataRepo()
                    Log.d(TAG, "global = ${response.affectedCountries}")
                    _globalData.postValue(response)
                } catch (e: Exception) {
                    Log.d(TAG, e.message!!)
                    _globalData.postValue(null)
                }
            }
        }
    }

    fun getIndonesiaData() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getIndonesiaDataRepo()
                    Log.d(TAG, "indonesia = ${response.country}")
                    _indonesiaData.postValue(response)
                } catch (e: Exception) {
                    Log.d(TAG, e.message!!)
                    _indonesiaData.postValue(null)
                }
            }
        }
    }

    companion object {
        private val TAG = SplashViewModel::class.java.simpleName
    }
}