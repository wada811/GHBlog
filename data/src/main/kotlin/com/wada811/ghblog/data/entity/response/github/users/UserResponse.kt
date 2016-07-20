package com.wada811.ghblog.data.entity.response.github.users

import org.threeten.bp.ZonedDateTime

data class UserResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
    val type: String,
    val site_admin: Boolean,
    val name: String,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean,
    val bio: String?,
    val public_repos: Int,
    val public_gists: Int,
    val followers: Int,
    val following: Int,
    val created_at: ZonedDateTime,
    val updated_at: ZonedDateTime,
    val total_private_repos: Int,
    val owned_private_repos: Int,
    val private_gists: Int,
    val disk_usage: Int,
    val collaborators: Int,
    val plan: PlanResponse
) {
    data class PlanResponse(
        val name: String,
        val space: Int,
        val private_repos: Int,
        val collaborators: Int
    )
}