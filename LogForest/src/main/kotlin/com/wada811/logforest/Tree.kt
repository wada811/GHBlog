package com.wada811.logforest

internal interface Tree {
    /** Log a verbose message. */
    fun v(message: String)

    /** Log a verbose message and an exception. */
    fun v(message: String, t: Throwable)

    /** Log a debug message. */
    fun d(message: String)

    /** Log a debug message and an exception. */
    fun d(message: String, t: Throwable)

    /** Log an info message. */
    fun i(message: String)

    /** Log an info message and an exception. */
    fun i(message: String, t: Throwable)

    /** Log a warning message. */
    fun w(message: String)

    /** Log a warning message and an exception. */
    fun w(message: String, t: Throwable)

    /** Log an error message. */
    fun e(message: String)

    /** Log an error message and an exception. */
    fun e(message: String, t: Throwable)

    /** Log an assert message. */
    fun wtf(message: String)

    /** Log an assert message and an exception. */
    fun wtf(message: String, t: Throwable)

    /** Log a message at `level`. */
    fun print(level: LogLevel, message: String)

    /** Log a an message and an exception at `level`. */
    fun print(level: LogLevel, message: String, t: Throwable)

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     * @param level Log level. See [Log] for constants.
     * *
     * @param tag Explicit or inferred tag. May be `null`.
     * *
     * @param message Formatted log message. May be `null`, but then `t` will not be.
     * *
     * @param t Accompanying exceptions. May be `null`, but then `message` an not be.
     */
    fun log(level: LogLevel, tag: String, message: String, t: Throwable?)
}