package com.wada811.ghblog.data.datasource

import com.wada811.ghblog.data.entity.request.github.oauth.AccessTokenRequest
import com.wada811.ghblog.data.http.GitHubApi
import com.wada811.ghblog.data.http.GitHubOAuthApi
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.User
import rx.Observable

class CloudUserDataStore {
    fun getAccessToken(code: String, state: String): Observable<String> {
        return Observable.defer {
            val request = AccessTokenRequest(GHBlogContext.gitHubApp.clientId, GHBlogContext.gitHubApp.clientSecret, code, state)
            GitHubOAuthApi().getAccessToken(request).map { it.body().access_token }
        }
    }

    fun getUser(accessToken: String): Observable<User> {
        return Observable.defer {
            GitHubApi(accessToken).getUser().map {
                val response = it.body()
                User(
                    accessToken,
                    response.login,
                    response.id,
                    response.avatar_url,
                    response.gravatar_id,
                    response.url,
                    response.html_url,
                    response.followers_url,
                    response.following_url,
                    response.gists_url,
                    response.starred_url,
                    response.subscriptions_url,
                    response.organizations_url,
                    response.repos_url,
                    response.events_url,
                    response.received_events_url,
                    response.type,
                    response.site_admin,
                    response.name,
                    response.company,
                    response.blog,
                    response.location,
                    response.email,
                    response.hireable,
                    response.bio,
                    response.public_repos,
                    response.public_gists,
                    response.followers,
                    response.following,
                    response.created_at,
                    response.updated_at,
                    response.total_private_repos,
                    response.owned_private_repos,
                    response.private_gists,
                    response.disk_usage,
                    response.collaborators,
                    User.Plan(
                        response.plan.name,
                        response.plan.space,
                        response.plan.private_repos,
                        response.plan.collaborators
                    )
                )
            }
        }
    }

}