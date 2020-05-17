package relawan.covidmonitor.view.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import relawan.covidmonitor.repository.Repository
import relawan.covidmonitor.view.home.HomeViewModel

class SplashModelFactory (
    private val repository: Repository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}