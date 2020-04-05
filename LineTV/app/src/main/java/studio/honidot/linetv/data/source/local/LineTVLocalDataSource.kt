package studio.honidot.linetv.data.source.local

import android.content.Context
import studio.honidot.linetv.data.*
import studio.honidot.linetv.data.source.LineTVDataSource

/**
 * Concrete implementation of a LineTV source as a db.
 */
class LineTVLocalDataSource(val context: Context) : LineTVDataSource {

    override suspend fun getDramas(): Result<DramaResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
