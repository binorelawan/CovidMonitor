package relawan.covidmonitor.view.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import relawan.covidmonitor.repository.Repository

class HomeModelFactory (
    private val context: Context?,
    private val global: Global?,
    private val indonesia: Indonesia?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(context, global, indonesia) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}