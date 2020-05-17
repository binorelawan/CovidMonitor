package relawan.covidmonitor.model


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class IndonesiaInfo(
    val _id: Int?,
    val flag: String?,
    val iso2: String?,
    val iso3: String?,
    val lat: Int?,
    val long: Int?
) : Parcelable