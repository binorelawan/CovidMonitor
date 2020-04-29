package relawan.covidmonitor.model


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Global(
    val active: Int?,
    val affectedCountries: Int?,
    val cases: Int?,
    val casesPerOneMillion: Int?,
    val critical: Int?,
    val deaths: Int?,
    val deathsPerOneMillion: Int?,
    val recovered: Int?,
    val tests: Int?,
    val testsPerOneMillion: Double?,
    val todayCases: Int?,
    val todayDeaths: Int?,
    val updated: Long?
) : Parcelable