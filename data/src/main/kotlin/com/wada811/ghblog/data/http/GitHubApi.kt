package com.wada811.ghblog.data.http

import com.squareup.moshi.Moshi
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.ResponseBody
import com.wada811.ghblog.data.entity.*
import com.wada811.ghblog.data.entity.request.github.git.commits.CreateCommitRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.CreateContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.DeleteContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.UpdateContentRequest
import com.wada811.ghblog.data.http.adapter.ZonedDateTimeAdapter
import com.wada811.ghblog.domain.model.User
import org.threeten.bp.ZonedDateTime
import retrofit.MoshiConverterFactory
import retrofit.Response
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.Observable

class GitHubApi(var user: User) {
    val client: GitHubService
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
                .baseUrl(GitHubService.ApiBaseUrl)
                .build()
            return retrofit.create(GitHubService::class.java)
        }

    fun getRepositoryList(): Observable<Response<List<RepositoryEntity>>> = client.getRepositoryList()
    fun getRepositoryList(url: String): Observable<Response<List<RepositoryEntity>>> = client.getRepositoryList(url)

    fun getContents(owner: String, repo: String, path: String): Observable<Response<List<RepositoryContentInfoEntity>>> = client.getContents(owner, repo, path)
    fun getContent(owner: String, repo: String, path: String): Observable<Response<RepositoryContentEntity>> = client.getContent(owner, repo, path)
    fun createContent(request: CreateContentRequest) = client.createContent(request.owner, request.repo, request.path, request.commit)
    fun updateContent(request: UpdateContentRequest) = client.updateContent(request.owner, request.repo, request.path, request.commit)
    fun deleteContent(request: DeleteContentRequest) = client.deleteContent(request.owner, request.repo, request.path, request.commit.getQueryMap())

    fun createCommit(request: CreateCommitRequest) = client.createCommit(request.owner, request.repo, request.commit)
    fun getReference(owner: String, repo: String, ref: String): Observable<Response<ReferenceEntity>> = client.getReference(owner, repo, ref)

    fun getGitTree(owner: String, repo: String, sha: String): Observable<Response<GitTreeEntity>> = client.getGitTree(owner, repo, sha)

}