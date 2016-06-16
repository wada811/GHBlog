package com.wada811.ghblog.data.http

import com.squareup.moshi.Moshi
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.ResponseBody
import com.wada811.ghblog.data.entity.ContentEntity
import com.wada811.ghblog.data.entity.GitTreeEntity
import com.wada811.ghblog.data.entity.ReferenceEntity
import com.wada811.ghblog.data.entity.RepositoryEntity
import com.wada811.ghblog.data.http.adapter.ZonedDateTimeAdapter
import com.wada811.ghblog.model.domain.User
import org.threeten.bp.ZonedDateTime
import retrofit.MoshiConverterFactory
import retrofit.Response
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.Observable

class GitHubApi(var user: User) : GitHubApiService {
    val client: GitHubApiService
        get() {
            val moshi = Moshi.Builder()
                    .add(ZonedDateTime::class.java, ZonedDateTimeAdapter())
                    .build()
            val client = OkHttpClient()
            client.interceptors()?.add(Interceptor {
                chain: Interceptor.Chain ->
                val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "token ${user.accessToken}")
                        .build()
                System.out.println("GitHubApi, URL: ${request.httpUrl().toString()}")
                val response = chain.proceed(request)
                val responseBodyText = response.body().string()
                System.out.println("GitHubApi, response:  $responseBodyText")
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

    override fun getRepositoryList(): Observable<Response<List<RepositoryEntity>>> = client.getRepositoryList()
    override fun getRepositoryList(url: String): Observable<Response<List<RepositoryEntity>>> = client.getRepositoryList(url)

    override fun getContents(owner: String, repo: String, path: String): Observable<Response<List<ContentEntity>>> = client.getContents(owner, repo, path)

    override fun getReference(owner: String, repo: String, ref: String): Observable<Response<ReferenceEntity>> = client.getReference(owner, repo, ref)

    override fun getGitTree(owner: String, repo: String, sha: String): Observable<Response<GitTreeEntity>> = client.getGitTree(owner, repo, sha)

}