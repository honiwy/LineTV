package studio.honidot.linetv

import android.app.Application
import studio.honidot.linetv.data.source.LineTVRepository
import studio.honidot.linetv.utility.ServiceLocator
import kotlin.properties.Delegates

class LineTVApplication : Application() {

    // Depends on the flavor,
    val lineTVRepository: LineTVRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var INSTANCE: LineTVApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}