package studio.honidot.linetv.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import studio.honidot.linetv.LineTVApplication

object Util {

    /**
     * Determine and monitor the connectivity status
     *
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     */
    fun isInternetConnected(): Boolean {
        val cm = LineTVApplication.INSTANCE
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return LineTVApplication.INSTANCE.getString(resourceId)
    }

    fun getColor(resourceId: Int): Int {
        return LineTVApplication.INSTANCE.getColor(resourceId)
    }
}