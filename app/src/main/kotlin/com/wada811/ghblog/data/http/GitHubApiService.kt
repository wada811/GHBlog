package com.wada811.ghblog.data.http

import com.wada811.ghblog.data.entity.ContentEntity
import com.wada811.ghblog.data.entity.GitTreeEntity
import com.wada811.ghblog.data.entity.ReferenceEntity
import com.wada811.ghblog.data.entity.RepositoryEntity
import retrofit.Response
import retrofit.http.GET
import retrofit.http.Path
import retrofit.http.Url
import rx.Observable

interface GitHubApiService {
    val ApiBaseUrl: String
        get() = "https://api.github.com"

    @GET("user/repos")
    fun getRepositoryList(): Observable<Response<List<RepositoryEntity>>>
    @GET
    fun getRepositoryList(@Url url: String): Observable<Response<List<RepositoryEntity>>>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    fun getContents(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String): Observable<Response<List<ContentEntity>>>

    @GET("/repos/{owner}/{repo}/git/refs/{ref}")
    fun getReference(@Path("owner") owner: String, @Path("repo") repo: String, @Path("ref") ref: String): Observable<Response<ReferenceEntity>>

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    fun getGitTree(@Path("owner") owner: String, @Path("repo") repo: String, @Path("sha") sha: String): Observable<Response<GitTreeEntity>>
}