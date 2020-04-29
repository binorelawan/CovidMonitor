package relawan.covidmonitor.view.indonesia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import relawan.covidmonitor.model.Indonesia

class IndonesiaModelFactory (
    private val indonesia: Indonesia?) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndonesiaViewModel::class.java)) {
            return IndonesiaViewModel(indonesia) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}