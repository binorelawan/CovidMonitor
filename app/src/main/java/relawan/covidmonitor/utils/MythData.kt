package relawan.covidmonitor.utils

import android.app.Application
import android.content.Context
import relawan.covidmonitor.R
import relawan.covidmonitor.model.Myth

class MythData (val context: Context){

    fun getListData(): ArrayList<Myth> {
        val list: ArrayList<Myth> = arrayListOf()
        val title = context.resources.getStringArray(R.array.myth)
        val detail = context.resources.getStringArray(R.array.myth_detail)
        for (i in title.indices) {
            list.add(Myth(title[i], detail[i]))
        }

        return list
    }


}