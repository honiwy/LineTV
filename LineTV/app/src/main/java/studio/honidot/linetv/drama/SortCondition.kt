package studio.honidot.linetv.drama

import studio.honidot.linetv.LineTVApplication
import studio.honidot.linetv.R

enum class SortCondition(val value: String) {
    DEFAULT(LineTVApplication.INSTANCE.getString(R.string.origin)),
    RATING(LineTVApplication.INSTANCE.getString(R.string.rating)),
    TOTAL_VIEW(LineTVApplication.INSTANCE.getString(R.string.total_view))
}
