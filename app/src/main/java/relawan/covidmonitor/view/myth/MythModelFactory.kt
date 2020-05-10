package relawan.covidmonitor.view.myth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import relawan.covidmonitor.model.Myth

class MythModelFactory (
    private val myth: List<Myth>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MythViewModel::class.java)) {
            return MythViewModel(myth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}