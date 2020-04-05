package studio.honidot.linetv.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import studio.honidot.linetv.data.*

/**
 * Concrete implementation to load LineTV sources.
 */
class DefaultLineTVRepository(
    private val lineTVRemoteDataSource: LineTVDataSource,
    private val lineTVLocalDataSource: LineTVDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LineTVRepository {

    override suspend fun getDramas(): Result<DramaResult> {
        return lineTVRemoteDataSource.getDramas()
    }

    override fun getDramasInLocal(): LiveData<List<Drama>> {
        return lineTVLocalDataSource.getDramasInLocal()
    }

    override suspend fun insertDrama(drama: Drama) {
        lineTVLocalDataSource.insertDrama(drama)
    }

}
