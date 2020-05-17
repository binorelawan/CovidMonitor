package relawan.covidmonitor.model


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Country (
    val active: Int,
    val cases: Int,
    val casesPerOneMillion: Double,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo?,
    val critical: Int,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val recovered: Int,
    val tests: Int,
    val testsPerOneMillion: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val updated: Long
) :Parcelable