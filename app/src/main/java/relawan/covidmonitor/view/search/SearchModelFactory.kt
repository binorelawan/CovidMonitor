package relawan.covidmonitor.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import relawan.covidmonitor.repository.Repository

class SearchModelFactory (
    private val repository: Repository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}