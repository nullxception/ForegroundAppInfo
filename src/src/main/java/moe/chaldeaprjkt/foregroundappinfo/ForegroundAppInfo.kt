package moe.chaldeaprjkt.foregroundappinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import androidx.annotation.RequiresApi
import moe.chaldeaprjkt.foregroundappinfo.reader.ActivityReader
import moe.chaldeaprjkt.foregroundappinfo.reader.UsageStatsReader

@RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
class ForegroundAppInfo(private val context: Context) {
    private val reader by lazy {
        UsageStatsReader(context)
    }

    fun read(): ApplicationInfo =
        context.packageManager.getPackageInfo(reader.read(), 0).applicationInfo

}
