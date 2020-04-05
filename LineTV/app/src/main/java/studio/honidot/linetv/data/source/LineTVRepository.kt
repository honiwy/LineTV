package studio.honidot.linetv.data.source

import studio.honidot.linetv.data.*

/**
 * Interface to the LineTV layers.
 */
interface LineTVRepository {

    suspend fun getDramas(): Result<DramaResult>

}
