package com.wada811.ghblog.data.net

import com.squareup.moshi.Moshi
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.ResponseBody
import com.wada811.ghblog.data.entity.RepositoryEntity
import com.wada811.ghblog.data.net.adapter.ZonedDateTimeAdapter
import com.wada811.ghblog.model.domain.User
import org.threeten.bp.ZonedDateTime
import retrofit.MoshiConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.Observable

class GitHubApi(var user: User) : GitHubApiService {

    private fun getClient(): GitHubApiService {
        val moshi = Moshi.Builder()
                .add(ZonedDateTime::class.java, ZonedDateTimeAdapter())
                .build()
        val client = OkHttpClient()
        client.interceptors().add(Interceptor {
            chain: Interceptor.Chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "token ${user.accessToken}")
                    .build()
            val response = chain.proceed(request)
            val responseBodyText = response.body().string()
            System.out.println("GitHubApi $responseBodyText")
            return@Interceptor response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBodyText)).build()
        })
        val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(ApiBaseUrl)
                .build()
        return retrofit.create(GitHubApiService::class.java)
    }

    override fun getRepositoryList(): Observable<List<RepositoryEntity>> = getClient().getRepositoryList()
}