package com.wada811.ghblog.data.http

class RateLimit(val limit: Int, val remaining: Int, val resetAsUnixEpochSeconds: Long)