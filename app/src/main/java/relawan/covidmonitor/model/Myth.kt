package relawan.covidmonitor.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Myth (
    var title : String? = null,
    var detail : String? = null
):Parcelable {
    // State of the item
    @IgnoredOnParcel
    var expanded = false

}