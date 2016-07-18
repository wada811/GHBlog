package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.response.github.repos.RepositoryResponse
import com.wada811.ghblog.domain.model.Owner
import com.wada811.ghblog.domain.model.Permission
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.User

object RepositoryResponseDataMapper {
    fun transform(user: User, response: RepositoryResponse) = Repository(
        user,
        response.id,
        response.name,
        response.full_name,
        OwnerResponseDataMapper.transform(response.owner),
        response.private,
        response.html_url,
        response.description,
        response.fork,
        response.url,
        response.forks_url,
        response.keys_url,
        response.collaborators_url,
        response.teams_url,
        response.hooks_url,
        response.issue_events_url,
        response.events_url,
        response.assignees_url,
        response.branches_url,
        response.tags_url,
        response.blobs_url,
        response.git_tags_url,
        response.git_refs_url,
        response.trees_url,
        response.statuses_url,
        response.languages_url,
        response.stargazers_url,
        response.contributors_url,
        response.subscribers_url,
        response.subscription_url,
        response.commits_url,
        response.git_commits_url,
        response.comments_url,
        response.issue_comment_url,
        response.contents_url,
        response.compare_url,
        response.merges_url,
        response.archive_url,
        response.downloads_url,
        response.issues_url,
        response.pulls_url,
        response.milestones_url,
        response.notifications_url,
        response.labels_url,
        response.releases_url,
        response.deployments_url,
        response.created_at,
        response.updated_at,
        response.pushed_at,
        response.git_url,
        response.ssh_url,
        response.clone_url,
        response.svn_url,
        response.homepage,
        response.size,
        response.stargazers_count,
        response.watchers_count,
        response.language,
        response.has_issues,
        response.has_downloads,
        response.has_wiki,
        response.has_pages,
        response.forks_count,
        response.mirror_url,
        response.open_issues_count,
        response.forks,
        response.open_issues,
        response.watchers,
        response.default_branch,
        PermissionResponseDataMapper.transform(response.permissions)
    )

    object OwnerResponseDataMapper {
        fun transform(response: RepositoryResponse.OwnerResponse) = Owner(
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
            response.site_admin
        )
    }

    object PermissionResponseDataMapper {
        fun transform(response: RepositoryResponse.PermissionResponse) = Permission(
            response.admin,
            response.push,
            response.pull
        )
    }
}
