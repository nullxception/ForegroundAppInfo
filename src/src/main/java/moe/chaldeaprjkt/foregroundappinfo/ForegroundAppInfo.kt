package moe.chaldeaprjkt.foregroundappinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Handler
import android.os.Looper
import moe.chaldeaprjkt.foregroundappinfo.reader.ActivityReader
import moe.chaldeaprjkt.foregroundappinfo.reader.UsageStatsReader
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ForegroundAppInfo(private val context: Context) {
    var timeout = 1000L
        set(value) {
            stopListening()
            runCallback()
            field = value
        }

    private lateinit var callback: (ApplicationInfo) -> Unit
    private val reader by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsReader(context)
        } else {
            ActivityReader(context)
        }
    }

    private val pool by lazy {
        ScheduledThreadPoolExecutor(1)
    }

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private fun runCallback() {
        if (!::callback.isInitialized)
            return

        handler.post { callback.invoke(read()) }
        pool.schedule(Runnable(::runCallback), timeout, TimeUnit.MILLISECONDS)
    }

    fun read(): ApplicationInfo =
        context.packageManager.getPackageInfo(reader.read(), 0).applicationInfo

    fun startListening(callback: (ApplicationInfo) -> Unit) {
        callback.let {
            handler.post { it.invoke(read()) }
            this.callback = it
        }

        pool.schedule(Runnable(::runCallback), timeout, TimeUnit.MILLISECONDS)
    }

    fun stopListening() {
        if (pool.isTerminated || !::callback.isInitialized)
            return

        pool.shutdownNow()
    }
}
