package moe.chaldeaprjkt.foregroundappinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import moe.chaldeaprjkt.foregroundappinfo.reader.ActivityReader
import moe.chaldeaprjkt.foregroundappinfo.reader.ReaderInterface
import moe.chaldeaprjkt.foregroundappinfo.reader.UsageStatsReader

class ForegroundAppInfo(private val context: Context) {
    private lateinit var reader: ReaderInterface

    fun read(): ApplicationInfo =
        context.packageManager.getPackageInfo(reader.read(), 0).applicationInfo

}
