package studio.honidot.linetv.data.source

import androidx.lifecycle.LiveData
import studio.honidot.linetv.data.*

/**
 * Main entry point for accessing LineTV sources.
 */
interface LineTVDataSource {

    suspend fun getDramas(): Result<DramaResult>

    fun getDramasInLocal(): LiveData<List<Drama>>

    suspend fun insertDrama(drama: Drama)
}
