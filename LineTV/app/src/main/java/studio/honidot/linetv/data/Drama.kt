package studio.honidot.linetv.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Drama(
    val dramaId: Long,
    val thumb: String,
    val name: String,
    val rating: String,
    val createdAt: String,
    val totalViews: Long
) : Parcelable