package studio.honidot.linetv.utility

import android.content.Context
import androidx.annotation.VisibleForTesting
import studio.honidot.linetv.data.source.DefaultLineTVRepository
import studio.honidot.linetv.data.source.LineTVDataSource
import studio.honidot.linetv.data.source.LineTVRepository
import studio.honidot.linetv.data.source.local.LineTVLocalDataSource
import studio.honidot.linetv.data.source.remote.LineTVRemoteDataSource

/**
 * A Service Locator for the [StylishRepository].
 */
object ServiceLocator {

    @Volatile
    var lineTVRepository: LineTVRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): LineTVRepository {
        synchronized(this) {
            return lineTVRepository
                ?: lineTVRepository
                ?: createLineTVRepository(context)
        }
    }

    private fun createLineTVRepository(context: Context): LineTVRepository {
        return DefaultLineTVRepository(
            LineTVRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): LineTVDataSource {
        return LineTVLocalDataSource(context)
    }
}