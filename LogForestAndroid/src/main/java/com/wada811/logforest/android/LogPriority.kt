package com.wada811.logforest.android

import android.util.Log
import com.wada811.logforest.LogLevel

enum class LogPriority(level: LogLevel, val priority: Int) {
    VERBOSE(LogLevel.VERBOSE, Log.VERBOSE),
    DEBUG(LogLevel.DEBUG, Log.DEBUG),
    INFO(LogLevel.INFO, Log.INFO),
    WARN(LogLevel.WARN, Log.WARN),
    ERROR(LogLevel.ERROR, Log.ERROR),
    ASSERT(LogLevel.ASSERT, Log.ASSERT);

    companion object {
        fun fromLogLevel(level: LogLevel): LogPriority = when (level) {
            LogLevel.VERBOSE -> LogPriority.VERBOSE
            LogLevel.DEBUG -> LogPriority.DEBUG
            LogLevel.INFO -> LogPriority.INFO
            LogLevel.WARN -> LogPriority.WARN
            LogLevel.ERROR -> LogPriority.ERROR
            LogLevel.ASSERT -> LogPriority.ASSERT
        }
    }

    fun toInt(): Int = priority
}
