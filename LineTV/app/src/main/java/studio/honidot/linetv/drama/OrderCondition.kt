package studio.honidot.linetv.drama

import studio.honidot.linetv.LineTVApplication
import studio.honidot.linetv.R

enum class OrderCondition(val value: String) {
    RATING(LineTVApplication.INSTANCE.getString(R.string.rating)),
    CREATED_TIME(LineTVApplication.INSTANCE.getString(R.string.created_at)),
    TOTAL_VIEW(LineTVApplication.INSTANCE.getString(R.string.total_view))
}
