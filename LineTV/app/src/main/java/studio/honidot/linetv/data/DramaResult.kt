package studio.honidot.linetv.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DramaResult(
    val error: String? = null,
    @Json(name = "data") val dramaList: List<Drama>? = null
) : Parcelable

