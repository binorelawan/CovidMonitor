package relawan.covidmonitor.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import relawan.covidmonitor.model.Country

class DetailViewModel(country: Country?) : ViewModel() {

    private val _detailData = MutableLiveData<Country>()
    val detailData: LiveData<Country>
        get() = _detailData

    private val _coordinate = MutableLiveData<List<Double>>()
    val coordinate: LiveData<List<Double>>
        get() = _coordinate

    init {
        _detailData.value = country

        _coordinate.value = listOf(country?.countryInfo?.lat!!, country.countryInfo.long!!)
    }
}