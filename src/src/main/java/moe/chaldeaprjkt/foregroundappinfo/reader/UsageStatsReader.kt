package moe.chaldeaprjkt.foregroundappinfo.reader

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
internal class UsageStatsReader(private val context: Context):
    ReaderInterface {
    private val statsManager by lazy {
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    }

    override fun read(timeToLife: Int): String {
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - (1000 * timeToLife)
        return statsManager.queryEvents(beginTime, endTime).let {
            var info = context.packageName
            val ev = UsageEvents.Event()
            while (it.hasNextEvent()) {
                it.getNextEvent(ev)
                @Suppress("DEPRECATION")
                if (ev.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    info = ev.packageName
                }
            }
            info
        }
    }
}
