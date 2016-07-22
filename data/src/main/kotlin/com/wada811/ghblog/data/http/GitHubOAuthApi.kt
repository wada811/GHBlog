package com.wada811.ghblog.data.http

import com.squareup.moshi.Moshi
import com.wada811.ghblog.data.entity.request.github.oauth.AccessTokenRequest
import com.wada811.ghblog.data.entity.response.github.oauth.AccessTokenResponse
import com.wada811.ghblog.data.http.adapter.ZonedDateTimeAdapter
import com.wada811.logforest.LogWood
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.threeten.bp.ZonedDateTime
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable

class GitHubOAuthApi() : GitHubOAuthService {
    val client: GitHubOAuthService
        get() {
            val moshi = Moshi.Builder()
                .add(ZonedDateTime::class.java, ZonedDateTimeAdapter())
                .build()
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    chain: Interceptor.Chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                    LogWood.i("URL: ${request.url().toString()}")
                    val response = chain.proceed(request)
                    val responseBodyText = response.body().string()
                    LogWood.i("response:  $responseBodyText")
                    return@addInterceptor response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBodyText)).build()
                }
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(GitHubOAuthService.ApiBaseUrl)
                .build()
            return retrofit.create(GitHubOAuthService::class.java)
        }

    override fun getAccessToken(request: AccessTokenRequest): Observable<Response<AccessTokenResponse>> = client.getAccessToken(request)
}