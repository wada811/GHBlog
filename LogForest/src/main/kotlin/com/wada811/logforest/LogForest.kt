package com.wada811.logforest

object LogForest {

    private val forest = mutableListOf<LogTree>()
    fun plant(logTree: LogTree) {
        forest.add(logTree)
    }

    fun fell(logTree: LogTree) {
        forest.remove(logTree)
    }

    internal val WORLD_TREE: Tree = object : Tree {
        override fun v(message: String) {
            forest.forEach { it.v(message) }
        }

        override fun v(message: String, t: Throwable) {
            forest.forEach { it.v(message, t) }
        }

        override fun d(message: String) {
            forest.forEach { it.d(message) }
        }

        override fun d(message: String, t: Throwable) {
            forest.forEach { it.d(message, t) }
        }

        override fun i(message: String) {
            forest.forEach { it.i(message) }
        }

        override fun i(message: String, t: Throwable) {
            forest.forEach { it.i(message, t) }
        }

        override fun w(message: String) {
            forest.forEach { it.w(message) }
        }

        override fun w(message: String, t: Throwable) {
            forest.forEach { it.w(message, t) }
        }

        override fun e(message: String) {
            forest.forEach { it.e(message) }
        }

        override fun e(message: String, t: Throwable) {
            forest.forEach { it.e(message, t) }
        }

        override fun wtf(message: String) {
            forest.forEach { it.wtf(message) }
        }

        override fun wtf(message: String, t: Throwable) {
            forest.forEach { it.wtf(message, t) }
        }

        override fun print(level: LogLevel, message: String) {
            forest.forEach { it.print(level, message) }
        }

        override fun print(level: LogLevel, message: String, t: Throwable) {
            forest.forEach { it.print(level, message, t) }
        }

        override fun log(level: LogLevel, tag: String, message: String, t: Throwable?) {
            forest.forEach { it.log(level, tag, message, t) }
        }
    }
}

