package studio.honidot.linetv.data.source

import studio.honidot.linetv.data.*

/**
 * Main entry point for accessing LineTV sources.
 */
interface LineTVDataSource {

    suspend fun getDramas(): Result<DramaResult>

}
