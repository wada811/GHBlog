package com.wada811.logforest

import java.io.PrintWriter
import java.io.StringWriter

abstract class LogTree : Tree {
    private val CALL_STACK_INDEX = 6
    private val ANONYMOUS_CLASS = Regex("(\\$.+)+$")

    /**
     * Extract the tag which should be used for the message from the `element`.
     * By default this will use the class name without any anonymous class suffixes (e.g., `Foo$1` becomes `Foo`).
     *
     * Note: This will not be called if a [manual tag][.tag] was specified.
     */
    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        tag = ANONYMOUS_CLASS.replace(tag, "")
        return tag.substring(tag.lastIndexOf('.') + 1)
    }

    fun getTag(): String {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX])
    }

    override fun v(message: String) {
        prepareLog(LogLevel.VERBOSE, message)
    }

    override fun v(message: String, t: Throwable) {
        prepareLog(LogLevel.VERBOSE, message, t)
    }

    override fun d(message: String) {
        prepareLog(LogLevel.DEBUG, message)
    }

    override fun d(message: String, t: Throwable) {
        prepareLog(LogLevel.DEBUG, message, t)
    }

    override fun i(message: String) {
        prepareLog(LogLevel.INFO, message)
    }

    override fun i(message: String, t: Throwable) {
        prepareLog(LogLevel.INFO, message, t)
    }

    override fun w(message: String) {
        prepareLog(LogLevel.WARN, message)
    }

    override fun w(message: String, t: Throwable) {
        prepareLog(LogLevel.WARN, message, t)
    }

    override fun e(message: String) {
        prepareLog(LogLevel.ERROR, message)
    }

    override fun e(message: String, t: Throwable) {
        prepareLog(LogLevel.ERROR, message, t)
    }

    override fun wtf(message: String) {
        prepareLog(LogLevel.ASSERT, message)
    }

    override fun wtf(message: String, t: Throwable) {
        prepareLog(LogLevel.ASSERT, message, t)
    }

    override fun print(level: LogLevel, message: String) {
        prepareLog(level, message)
    }

    override fun print(level: LogLevel, message: String, t: Throwable) {
        prepareLog(level, message, t)
    }

    /** Return whether a message at `level` should be logged.  */
    fun isLoggable(@Suppress("UNUSED_PARAMETER") level: LogLevel): Boolean {
        return true
    }

    private fun prepareLog(level: LogLevel, message: String, t: Throwable? = null) {
        @Suppress("NAME_SHADOWING")
        var message: String = message
        if (!isLoggable(level)) {
            return
        }
        if (message.length == 0) {
            if (t == null) {
                return  // Swallow message if it's null and there's no throwable.
            }
            message = getStackTraceString(t)
        } else {
            if (t != null) {
                message += "\n" + getStackTraceString(t)
            }
        }

        log(level, getTag(), message, t)
    }

    private fun getStackTraceString(t: Throwable): String {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    abstract override fun log(level: LogLevel, tag: String, message: String, t: Throwable?)
}