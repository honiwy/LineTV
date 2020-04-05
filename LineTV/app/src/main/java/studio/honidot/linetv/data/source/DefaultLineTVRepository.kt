package studio.honidot.linetv.data.source

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


}
