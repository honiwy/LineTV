package studio.honidot.linetv.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import studio.honidot.linetv.data.source.local.LineTVConverters

@Entity(tableName = "dramas_in_cart_table", primaryKeys = ["drama_id","created_at"])
@TypeConverters(LineTVConverters::class)
@Parcelize
data class Drama(
    @ColumnInfo(name = "drama_id")
    @Json(name = "drama_id")val dramaId: Long,
    @ColumnInfo(name = "thumb")
    val thumb: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "created_at")
    @Json(name = "created_at")val createdAt: String,
    @ColumnInfo(name = "total_views")
    @Json(name = "total_views")val totalViews: Long
) : Parcelable