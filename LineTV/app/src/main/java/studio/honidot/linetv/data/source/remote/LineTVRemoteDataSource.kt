package studio.honidot.linetv.data.source.remote

import androidx.lifecycle.LiveData
import studio.honidot.linetv.R
import studio.honidot.linetv.data.*
import studio.honidot.linetv.data.source.LineTVDataSource
import studio.honidot.linetv.network.LineTVApi
import studio.honidot.linetv.utility.Logger
import studio.honidot.linetv.utility.Util.getString
import studio.honidot.linetv.utility.Util.isInternetConnected

/**
 * Implementation of the LineTV source that from network.
 */
object LineTVRemoteDataSource : LineTVDataSource {

    override suspend fun getDramas(): Result<DramaResult> {

        if (!isInternetConnected()) {
            return Result.Fail(getString(R.string.internet_not_connected))
        }
        // Get the Deferred object for our Retrofit request
        val getResultDeferred = LineTVApi.RETROFIT_SERVICE.getDramas()
        return try {
            // this will run on a thread managed by Retrofit
            val listResult = getResultDeferred.await()

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override fun getDramasInLocal(): LiveData<List<Drama>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertDrama(drama: Drama) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
