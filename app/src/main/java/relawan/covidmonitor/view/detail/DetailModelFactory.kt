package relawan.covidmonitor.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import relawan.covidmonitor.model.Country

class DetailModelFactory (
    private val country: Country?) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(country) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}