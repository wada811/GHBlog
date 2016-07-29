package com.wada811.ghblog.data.http

import com.wada811.ghblog.data.entity.request.github.git.commits.CreateCommitRequest
import com.wada811.ghblog.data.entity.request.github.git.refs.UpdateReferenceRequest.UpdateReferenceReferenceRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.CreateTreeRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.CreateContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.UpdateContentRequest
import com.wada811.ghblog.data.entity.response.github.git.commits.CreateCommitResponse
import com.wada811.ghblog.data.entity.response.github.git.commits.GetCommitResponse
import com.wada811.ghblog.data.entity.response.github.git.refs.GetReferenceResponse
import com.wada811.ghblog.data.entity.response.github.git.refs.UpdateReferenceResponse
import com.wada811.ghblog.data.entity.response.github.git.trees.CreateTreeResponse
import com.wada811.ghblog.data.entity.response.github.git.trees.GetTreeResponse
import com.wada811.ghblog.data.entity.response.github.repos.RepositoryResponse
import com.wada811.ghblog.data.entity.response.github.repos.contents.*
import com.wada811.ghblog.data.entity.response.github.users.GetUserResponse
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface GitHubService {
    companion object {
        val ApiBaseUrl: String
            get() = "https://api.github.com"
    }

    @GET("user")
    fun getUser(): Observable<Response<GetUserResponse>>

    @GET("user/repos")
    fun getRepositoryList(): Observable<Response<List<RepositoryResponse>>>

    @GET
    fun getRepositoryList(@Url url: String): Observable<Response<List<RepositoryResponse>>>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    fun getContents(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String): Observable<Response<List<GetContentsResponse>>>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    fun getContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String): Observable<Response<GetContentResponse>>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    fun createContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String,
                      @Body commit: CreateContentRequest.CreateContentCommitRequest): Observable<Response<CreateContentResponse>>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    fun updateContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String,
                      @Body commit: UpdateContentRequest.UpdateContentCommitRequest): Observable<Response<UpdateContentResponse>>

    @DELETE("/repos/{owner}/{repo}/contents/{path}")
    fun deleteContent(@Path("owner") owner: String, @Path("repo") repo: String, @Path("path") path: String,
                      @QueryMap commit: Map<String, String>): Observable<Response<DeleteContentResponse>>

    @GET("/repos/{owner}/{repo}/git/commits/{sha}")
    fun getCommit(@Path("owner") owner: String, @Path("repo") repo: String, @Path("sha") sha: String): Observable<Response<GetCommitResponse>>

    @POST("/repos/{owner}/{repo}/git/commits")
    fun createCommit(@Path("owner") owner: String, @Path("repo") repo: String,
                     @Body commit: CreateCommitRequest.CreateCommitCommitRequest): Observable<Response<CreateCommitResponse>>

    @GET("/repos/{owner}/{repo}/git/refs/{ref}")
    fun getReference(@Path("owner") owner: String, @Path("repo") repo: String, @Path("ref") ref: String): Observable<Response<GetReferenceResponse>>

    @PATCH("/repos/{owner}/{repo}/git/refs/{ref}")
    fun updateReference(@Path("owner") owner: String, @Path("repo") repo: String, @Path("ref") ref: String,
                        @Body reference: UpdateReferenceReferenceRequest): Observable<Response<UpdateReferenceResponse>>

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    fun getTree(@Path("owner") owner: String, @Path("repo") repo: String, @Path("sha") sha: String): Observable<Response<GetTreeResponse>>

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    fun getGitTreeRecursively(@Path("owner") owner: String, @Path("repo") repo: String, @Path("sha") sha: String,
                              @Query("recursive") recursive: Int): Observable<Response<GetTreeResponse>>

    @POST("/repos/{owner}/{repo}/git/trees")
    fun createGitTree(@Path("owner") owner: String, @Path("repo") repo: String,
                      @Body tree: CreateTreeRequest.CreateTreeBodyRequest): Observable<Response<CreateTreeResponse>>
}