package studio.honidot.linetv

import android.content.Context

object UserManager {

    private const val USER_DATA = "user_data"
    private const val USER_SEARCH = "user_search"


    var userSearch: String? = null
        get() = LineTVApplication.INSTANCE
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_SEARCH, null)
        set(value) {
            field = when (value) {
                null -> {
                    LineTVApplication.INSTANCE
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_SEARCH)
                        .apply()
                    null
                }
                else -> {
                    LineTVApplication.INSTANCE
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_SEARCH, value)
                        .apply()
                    value
                }
            }
        }

}