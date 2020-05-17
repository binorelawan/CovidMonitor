package relawan.covidmonitor.view.indonesia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import relawan.covidmonitor.model.Indonesia

class IndonesiaViewModel(indonesia: Indonesia?): ViewModel() {

    private val _indonesiaData = MutableLiveData<Indonesia>()
    val indonesiaData: LiveData<Indonesia>
        get() = _indonesiaData

    private val _coordinate = MutableLiveData<List<Double>>()
    val coordinate: LiveData<List<Double>>
        get() = _coordinate


    init {
        _indonesiaData.value = indonesia

        _coordinate.value = listOf(indonesia?.countryInfo?.lat?.toDouble()!!, indonesia.countryInfo.long?.toDouble()!!)
    }
}