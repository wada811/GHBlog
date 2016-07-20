package com.wada811.ghblog.data.http

import com.squareup.moshi.Moshi
import com.wada811.ghblog.data.entity.request.github.git.commits.CreateCommitRequest
import com.wada811.ghblog.data.entity.request.github.git.commits.GetCommitRequest
import com.wada811.ghblog.data.entity.request.github.git.refs.GetReferenceRequest
import com.wada811.ghblog.data.entity.request.github.git.refs.UpdateReferenceRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.CreateTreeRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.GetTreeRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.*
import com.wada811.ghblog.data.http.adapter.ZonedDateTimeAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class GitHubApi(var accessToken: String) {
    val client: GitHubService
        get() {
            val moshi = Moshi.Builder()
                .add(ZonedDateTime::class.java, ZonedDateTimeAdapter())
                .build()
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    chain: Interceptor.Chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "token $accessToken")
                        .build()
                    System.out.println("GitHubApi, URL: ${request.url().toString()}")
                    val response = chain.proceed(request)
                    val responseBodyText = response.body().string()
                    System.out.println("GitHubApi, response:  $responseBodyText")
                    return@addInterceptor response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBodyText)).build()
                }
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(GitHubService.ApiBaseUrl)
                .build()
            return retrofit.create(GitHubService::class.java)
        }

    fun getUser() = client.getUser()

    fun getRepositoryList() = client.getRepositoryList()
    fun getRepositoryList(url: String) = client.getRepositoryList(url)

    fun getContents(request: GetContentsRequest) = client.getContents(request.owner, request.repo, request.path)
    fun getContent(request: GetContentRequest) = client.getContent(request.owner, request.repo, request.path)
    fun createContent(request: CreateContentRequest) = client.createContent(request.owner, request.repo, request.path, request.commit)
    fun updateContent(request: UpdateContentRequest) = client.updateContent(request.owner, request.repo, request.path, request.commit)
    fun deleteContent(request: DeleteContentRequest) = client.deleteContent(request.owner, request.repo, request.path, request.commit.getQueryMap())

    fun getCommit(request: GetCommitRequest) = client.getCommit(request.owner, request.repo, request.sha)
    fun createCommit(request: CreateCommitRequest) = client.createCommit(request.owner, request.repo, request.commit)

    fun getReference(request: GetReferenceRequest) = client.getReference(request.owner, request.repo, request.ref)
    fun updateReference(request: UpdateReferenceRequest) = client.updateReference(request.owner, request.repo, request.ref, request.reference)

    fun getTree(request: GetTreeRequest) = client.getTree(request.owner, request.repo, request.sha)
    fun getGitTreeRecursively(request: GetTreeRequest, recursive: Int = 1) = client.getGitTreeRecursively(request.owner, request.repo, request.sha, recursive)
    fun createGitTree(request: CreateTreeRequest) = client.createGitTree(request.owner, request.repo, request.body)

}