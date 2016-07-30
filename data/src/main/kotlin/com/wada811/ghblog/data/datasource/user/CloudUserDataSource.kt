package com.wada811.ghblog.data.datasource.user

import com.wada811.ghblog.data.entity.mapper.GetUserResponseDataMapper
import com.wada811.ghblog.data.entity.request.github.oauth.AccessTokenRequest
import com.wada811.ghblog.data.http.GitHubApi
import com.wada811.ghblog.data.http.GitHubOAuthApi
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.User
import rx.Observable

class CloudUserDataSource : UserDataSource {
    override fun getCurrentUser(): Observable<User> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAccessToken(code: String, state: String): Observable<String> {
        return Observable.defer {
            val request = AccessTokenRequest(GHBlogContext.gitHubApp.clientId, GHBlogContext.gitHubApp.clientSecret, code, state)
            GitHubOAuthApi().getAccessToken(request).map { it.body().access_token }
        }
    }

    override fun getUser(accessToken: String): Observable<User> {
        return Observable.defer {
            GitHubApi(accessToken).getUser().map { GetUserResponseDataMapper.transform(accessToken, it.body()) }
        }
    }

    override fun saveUser(user: User) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}