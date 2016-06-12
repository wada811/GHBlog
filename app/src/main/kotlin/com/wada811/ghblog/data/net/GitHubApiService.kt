package com.wada811.ghblog.data.net

import com.wada811.ghblog.data.entity.RepositoryEntity
import retrofit.http.GET
import rx.Observable

interface GitHubApiService {
    val ApiBaseUrl: String
        get() = "https://api.github.com"
    @GET("user/repos")
    fun getRepositoryList(): Observable<List<RepositoryEntity>>
}