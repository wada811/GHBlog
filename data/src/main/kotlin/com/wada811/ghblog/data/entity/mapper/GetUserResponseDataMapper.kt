package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.response.github.users.GetUserResponse
import com.wada811.ghblog.domain.model.User

object GetUserResponseDataMapper {
    fun transform(accessToken: String, response: GetUserResponse): User = User(
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