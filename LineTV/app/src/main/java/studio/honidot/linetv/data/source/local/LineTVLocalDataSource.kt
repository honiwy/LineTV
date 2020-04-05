package studio.honidot.linetv.data.source.local

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import studio.honidot.linetv.data.*
import studio.honidot.linetv.data.source.LineTVDataSource

/**
 * Concrete implementation of a LineTV source as a db.
 */
class LineTVLocalDataSource(val context: Context) : LineTVDataSource {

    override suspend fun getDramas(): Result<DramaResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDramasInLocal(): LiveData<List<Drama>> {
        return LineTVDatabase.getInstance(context).lineTVDatabaseDao.getAllDramas()
    }

    override suspend fun insertDrama(drama: Drama) {
        withContext(Dispatchers.IO) {
            LineTVDatabase.getInstance(context).lineTVDatabaseDao.insertDrama(drama)
        }
    }

}
