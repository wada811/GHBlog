package com.wada811.ghblog.data.http

import com.squareup.okhttp.Headers
import java.util.*

internal object ApiInfoParser {
    fun parseResponseHeader(headers: Headers) = ApiInfo(
            headers.parseLinkHeader(),
            headers.parseOauthScopeHeader(),
            headers.parseAcceptedOauthScopeHeader(),
            headers.parseETagHeader(),
            headers.parseRateLimitScopeHeader()
    )

    private fun Headers.parseLinkHeader(): HashMap<String, String> {
        val relRegex = Regex("rel=\"(prev|next|first|last)\"", RegexOption.IGNORE_CASE)
        val urlRegex = Regex("<(.+)>", RegexOption.IGNORE_CASE)
        val links = this["Link"]?.split(",")
        val httpLinks = HashMap<String, String>()
        links?.forEach {
            link ->
            val relMatches = relRegex.find(link)
            if (relMatches == null || relMatches.groups.count() != 2) {
                return@forEach
            }
            val urlMatches = urlRegex.find(link)
            if (urlMatches == null || urlMatches.groups.count() != 2) {
                return@forEach
            }
            httpLinks[relMatches.groupValues[1]] = urlMatches.groupValues[1]
        }
        return httpLinks
    }

    private fun Headers.parseOauthScopeHeader(): ArrayList<String> {
        val oauthScopes = this["X-OAuth-Scopes"]?.split(",")
        val oauthScopeList = ArrayList<String>()
        if (oauthScopes != null) {
            oauthScopeList.addAll(oauthScopes.map { it.trim() })
        }
        return oauthScopeList
    }

    private fun Headers.parseAcceptedOauthScopeHeader(): ArrayList<String> {
        val acceptedOAuthScopes = this["X-Accepted-OAuth-Scopes"]?.split(",")
        val acceptedOAuthScopeList = ArrayList<String>()
        if (acceptedOAuthScopes != null) {
            acceptedOAuthScopeList.addAll(acceptedOAuthScopes.map { it.trim() })
        }
        return acceptedOAuthScopeList
    }

    private fun Headers.parseETagHeader(): String {
        val etag = this["ETag"]
        return etag
    }

    private fun Headers.parseRateLimitScopeHeader(): RateLimit {
        val limit = this["X-RateLimit-Limit"].toInt()
        val remaining = this["X-RateLimit-Remaining"].toInt()
        val resetAsUnixEpochSeconds = this["X-RateLimit-Reset"].toLong()
        return RateLimit(limit, remaining, resetAsUnixEpochSeconds)
    }
}