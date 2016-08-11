package com.wada811.ghblog.data.entity.data

import android.support.annotation.Nullable
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import org.threeten.bp.ZonedDateTime

@Table("users")
data class UserEntity(
    @Setter("access_token") @Column(indexed = true) val access_token: String,
    @Setter("login") @Column val login: String,
    @Setter("id") @Column @PrimaryKey val id: Int,
    @Setter("avatar_url") @Column val avatar_url: String,
    @Setter("gravatar_id") @Column val gravatar_id: String,
    @Setter("url") @Column val url: String,
    @Setter("html_url") @Column val html_url: String,
    @Setter("followers_url") @Column val followers_url: String,
    @Setter("following_url") @Column val following_url: String,
    @Setter("gists_url") @Column val gists_url: String,
    @Setter("starred_url") @Column val starred_url: String,
    @Setter("subscriptions_url") @Column val subscriptions_url: String,
    @Setter("organizations_url") @Column val organizations_url: String,
    @Setter("repos_url") @Column val repos_url: String,
    @Setter("events_url") @Column val events_url: String,
    @Setter("received_events_url") @Column val received_events_url: String,
    @Setter("type") @Column val type: String,
    @Setter("site_admin") @Column val site_admin: Boolean,
    @Setter("name") @Column val name: String,
    @Setter("company") @Column @Nullable val company: String?,
    @Setter("blog") @Column @Nullable val blog: String?,
    @Setter("location") @Column @Nullable val location: String?,
    @Setter("email") @Column @Nullable val email: String?,
    @Setter("hireable") @Column val hireable: Boolean,
    @Setter("bio") @Column @Nullable val bio: String?,
    @Setter("public_repos") @Column val public_repos: Int,
    @Setter("public_gists") @Column val public_gists: Int,
    @Setter("followers") @Column val followers: Int,
    @Setter("following") @Column val following: Int,
    @Setter("created_at") @Column val created_at: ZonedDateTime,
    @Setter("updated_at") @Column val updated_at: ZonedDateTime,
    @Setter("total_private_repos") @Column val total_private_repos: Int,
    @Setter("owned_private_repos") @Column val owned_private_repos: Int,
    @Setter("private_gists") @Column val private_gists: Int,
    @Setter("disk_usage") @Column val disk_usage: Int,
    @Setter("collaborators") @Column val collaborators: Int,
    @Setter("plan_name") @Column val plan_name: String,
    @Setter("plan_space") @Column val plan_space: Int,
    @Setter("plan_private_repos") @Column val plan_private_repos: Int,
    @Setter("plan_collaborators") @Column val plan_collaborators: Int
)
