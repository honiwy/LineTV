package studio.honidot.linetv.data.source

import androidx.lifecycle.LiveData
import studio.honidot.linetv.data.*

/**
 * Interface to the LineTV layers.
 */
interface LineTVRepository {

    suspend fun getDramas(): Result<DramaResult>

    fun getDramasInLocal(): LiveData<List<Drama>>

    suspend fun insertDrama(drama: Drama)

}
