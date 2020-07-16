package moe.chaldeaprjkt.foregroundappinfo.reader

import android.app.ActivityManager
import android.app.Service
import android.content.Context

@Suppress("DEPRECATION")
internal class ActivityReader(private val context: Context) :
    ReaderInterface {
    override fun read(timeToLife: Int): String =
        (context.getSystemService(Service.ACTIVITY_SERVICE) as ActivityManager)
            .getRunningTasks(1).first().topActivity?.packageName ?: context.packageName
}
