package moe.chaldeaprjkt.foregroundappinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import moe.chaldeaprjkt.foregroundappinfo.reader.ActivityReader
import moe.chaldeaprjkt.foregroundappinfo.reader.UsageStatsReader

class ForegroundAppInfo(private val context: Context) {
    private val reader by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsReader(context)
        } else {
            ActivityReader(context)
        }
    }

    fun read(): ApplicationInfo =
        context.packageManager.getPackageInfo(reader.read(), 0).applicationInfo

}
