package relawan.covidmonitor.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import relawan.covidmonitor.repository.GlobalRepoCallback
import relawan.covidmonitor.repository.IndonesiaRepoCallback
import relawan.covidmonitor.repository.Repository

class HomeViewModel(private val repository: Repository): ViewModel() {

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

        repository.getGlobalDataRepo(object : GlobalRepoCallback {
            override fun onError() {
                Log.d(TAG, "global error")
                _globalData.value = null
            }

            override fun onSuccess(global: Global) {
                Log.d(TAG, global.updated.toString())
                _globalData.value = global
            }

        })
    }

    fun getIndonesiaData() {

        repository.getIndonesiaDataRepo(object : IndonesiaRepoCallback {
            override fun onError() {
                Log.d(TAG, "indonesia error")
                _indonesiaData.value = null
            }

            override fun onSuccess(indonesia: Indonesia) {
                Log.d(TAG, indonesia.countryInfo.flag)
                _indonesiaData.value = indonesia
            }

        })
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}