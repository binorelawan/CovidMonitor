package relawan.covidmonitor.model


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Indonesia(
    val active: Int?,
    val cases: Int?,
    val casesPerOneMillion: Int?,
    val continent: String?,
    val country: String?,
    val countryInfo: IndonesiaInfo?,
    val critical: Int?,
    val deaths: Int?,
    val deathsPerOneMillion: Int?,
    val recovered: Int?,
    val tests: Int?,
    val testsPerOneMillion: Int?,
    val todayCases: Int?,
    val todayDeaths: Int?,
    val updated: Long?
) : Parcelable