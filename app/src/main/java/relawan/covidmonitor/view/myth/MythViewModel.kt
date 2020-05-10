package relawan.covidmonitor.view.myth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import relawan.covidmonitor.model.Myth

class MythViewModel(val myth: List<Myth>) : ViewModel() {

    private val _mythList = MutableLiveData<List<Myth>>()
    val mythList: LiveData<List<Myth>>
        get() = _mythList

    init {
        _mythList.value = myth
    }
}