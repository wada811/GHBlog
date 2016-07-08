package com.wada811.ghblog.data.http

import com.squareup.moshi.Moshi
import com.wada811.ghblog.data.entity.GitTreeEntity
import com.wada811.ghblog.data.entity.RepositoryContentEntity
import com.wada811.ghblog.data.entity.RepositoryContentInfoEntity
import com.wada811.ghblog.data.entity.RepositoryEntity
import com.wada811.ghblog.data.entity.request.github.git.commits.CreateCommitRequest
import com.wada811.ghblog.data.entity.request.github.git.commits.GetCommitRequest
import com.wada811.ghblog.data.entity.request.github.git.refs.GetReferenceRequest
import com.wada811.ghblog.data.entity.request.github.git.refs.UpdateReferenceRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.CreateTreeRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.CreateContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.DeleteContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.UpdateContentRequest
import com.wada811.ghblog.data.entity.response.github.git.trees.CreateTreeResponse
import com.wada811.ghblog.data.http.adapter.ZonedDateTimeAdapter
import com.wada811.ghblog.domain.model.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.threeten.bp.ZonedDateTime
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable

class GitHubApi(var user: User) {
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
                        .addHeader("Authorization", "token ${user.accessToken}")
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

    fun getRepositoryList(): Observable<Response<List<RepositoryEntity>>> = client.getRepositoryList()
    fun getRepositoryList(url: String): Observable<Response<List<RepositoryEntity>>> = client.getRepositoryList(url)

    fun getContents(owner: String, repo: String, path: String): Observable<Response<List<RepositoryContentInfoEntity>>> = client.getContents(owner, repo, path)
    fun getContent(owner: String, repo: String, path: String): Observable<Response<RepositoryContentEntity>> = client.getContent(owner, repo, path)
    fun createContent(request: CreateContentRequest) = client.createContent(request.owner, request.repo, request.path, request.commit)
    fun updateContent(request: UpdateContentRequest) = client.updateContent(request.owner, request.repo, request.path, request.commit)
    fun deleteContent(request: DeleteContentRequest) = client.deleteContent(request.owner, request.repo, request.path, request.commit.getQueryMap())

    fun getCommit(request: GetCommitRequest) = client.getCommit(request.owner, request.repo, request.sha)
    fun createCommit(request: CreateCommitRequest) = client.createCommit(request.owner, request.repo, request.commit)

    fun getReference(request: GetReferenceRequest) = client.getReference(request.owner, request.repo, request.ref)
    fun updateReference(request: UpdateReferenceRequest) = client.updateReference(request.owner, request.repo, request.ref, request.reference)

    fun getGitTree(owner: String, repo: String, sha: String): Observable<Response<GitTreeEntity>> = client.getGitTree(owner, repo, sha)
    fun getGitTreeRecursively(owner: String, repo: String, sha: String, recursive: Int = 1): Observable<Response<GitTreeEntity>> = client.getGitTreeRecursively(owner, repo, sha, recursive)
    fun createGitTree(request: CreateTreeRequest): Observable<Response<CreateTreeResponse>> = client.createGitTree(request.owner, request.repo, request.body)

}