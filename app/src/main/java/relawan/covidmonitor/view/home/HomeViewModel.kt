package relawan.covidmonitor.view.home

import android.app.Activity
import android.content.Context
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
import relawan.covidmonitor.model.Myth
import relawan.covidmonitor.repository.Repository
import relawan.covidmonitor.utils.MythData

class HomeViewModel(val context: Context?, private val repository: Repository): ViewModel() {

    private val _globalData = MutableLiveData<Global>()
    val globalData : LiveData<Global>
        get() = _globalData

    private val _indonesiaData = MutableLiveData<Indonesia>()
    val indonesiaData : LiveData<Indonesia>
        get() = _indonesiaData

    private val _mythBuster = MutableLiveData<List<Myth>>()
    val mythBuster : LiveData<List<Myth>>
        get() = _mythBuster

    init {
        getGlobalData()
        getIndonesiaData()
        getMythData()
    }

    fun getGlobalData() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getGlobalDataRepo()
                    Log.d(TAG, response.affectedCountries.toString())
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
                    Log.d(TAG, response.country)
                    _indonesiaData.postValue(response)
                } catch (e: Exception) {
                    Log.d(TAG, e.message!!)
                    _indonesiaData.postValue(null)
                }
            }
        }
    }

    fun getMythData() {

        val listMyth = context?.let { MythData(it).getListData() }
        Log.d(TAG, "list myth = ${listMyth?.size}")
        _mythBuster.value = listMyth
    }


    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}