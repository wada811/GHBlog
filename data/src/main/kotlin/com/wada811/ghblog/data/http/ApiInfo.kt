package com.wada811.ghblog.data.http

import java.util.*

class ApiInfo(
        var links: HashMap<String, String>,
        var oauthScope: ArrayList<String>,
        var acceptedOauthScope: ArrayList<String>,
        var etag: String,
        var rateLimit: RateLimit
) {
    fun getPreviousPageUrl(): String? {
        return links["prev"]
    }

    fun getNextPageUrl(): String? {
        return links["next"]
    }

    fun getFirstPageUrl(): String? {
        return links["first"]
    }

    fun getLastPageUrl(): String? {
        return links["last"]
    }
}

