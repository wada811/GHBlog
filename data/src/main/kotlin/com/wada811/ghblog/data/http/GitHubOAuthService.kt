package com.wada811.ghblog.data.http

import com.wada811.ghblog.data.entity.request.github.oauth.AccessTokenRequest
import com.wada811.ghblog.data.entity.response.github.oauth.AccessTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface GitHubOAuthService {
    companion object {
        val ApiBaseUrl: String
            get() = "https://github.com"
    }

    @POST("login/oauth/access_token")
    fun getAccessToken(@Body request: AccessTokenRequest): Observable<Response<AccessTokenResponse>>
}