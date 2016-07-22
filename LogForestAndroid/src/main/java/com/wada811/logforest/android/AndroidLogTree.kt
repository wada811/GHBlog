package com.wada811.logforest.android

import android.util.Log
import com.wada811.logforest.LogLevel
import com.wada811.logforest.LogTree

object AndroidLogTree : LogTree() {
    override fun log(level: LogLevel, tag: String, message: String, t: Throwable?) {
        if (t == null) {
            Log.println(LogPriority.fromLogLevel(level).toInt(), tag, message)
        } else {
            when (level) {
                LogLevel.VERBOSE -> Log.v(tag, message, t)
                LogLevel.DEBUG -> Log.d(tag, message, t)
                LogLevel.INFO -> Log.i(tag, message, t)
                LogLevel.WARN -> Log.w(tag, message, t)
                LogLevel.ERROR -> Log.e(tag, message, t)
                LogLevel.ASSERT -> Log.wtf(tag, message, t)
            }
        }
    }
}