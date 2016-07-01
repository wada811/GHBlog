package com.wada811.ghblog.data.http

import com.wada811.ghblog.data.entity.*
import com.wada811.ghblog.data.entity.request.github.repos.contents.CreateContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.UpdateContentRequest
import com.wada811.ghblog.data.entity.response.github.repos.contents.CreateContentResponse
import com.wada811.ghblog.data.entity.response.github.repos.contents.UpdateContentResponse
import retrofit.Response
import retrofit.http.*
import rx.Observable

interface GitHubService {
    companion object {
        val ApiBaseUrl: String
            get() = "https://api.github.com"
    }

    @GET("user/repos")
    fun getRepositoryList(): Observable<Response<List<RepositoryEntity>>>

    @GET
    fun getRepositoryList(@Url url: String): Observable<Response<List<RepositoryEntity>>>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    fun getContents(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String): Observable<Response<List<RepositoryContentInfoEntity>>>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    fun getContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String): Observable<Response<RepositoryContentEntity>>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    fun createContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String,
                      @Body commit: CreateContentRequest.CreateContentCommitRequest): Observable<Response<CreateContentResponse>>
    @PUT("/repos/{owner}/{repo}/contents/{path}")
    fun updateContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String,
                      @Body commit: UpdateContentRequest.UpdateContentCommitRequest): Observable<Response<UpdateContentResponse>>

    @GET("/repos/{owner}/{repo}/git/refs/{ref}")
    fun getReference(@Path("owner") owner: String, @Path("repo") repo: String, @Path("ref") ref: String): Observable<Response<ReferenceEntity>>

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    fun getGitTree(@Path("owner") owner: String, @Path("repo") repo: String, @Path("sha") sha: String): Observable<Response<GitTreeEntity>>
}