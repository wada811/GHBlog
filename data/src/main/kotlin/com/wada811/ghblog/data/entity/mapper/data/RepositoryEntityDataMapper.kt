package com.wada811.ghblog.data.entity.mapper.data

import com.wada811.ghblog.data.entity.data.RepositoryEntity
import com.wada811.ghblog.domain.model.Owner
import com.wada811.ghblog.domain.model.Permission
import com.wada811.ghblog.domain.model.Repository

object RepositoryEntityDataMapper : EntityDataMapper<RepositoryEntity, Repository> {
    override fun toEntity(domain: Repository): RepositoryEntity = RepositoryEntity(
        UserEntityDataMapper.toEntity(domain.user),
        domain.id,
        domain.name,
        domain.fullName,
        domain.owner.login,
        domain.owner.id,
        domain.owner.avatarUrl,
        domain.owner.gravatarId,
        domain.owner.url,
        domain.owner.htmlUrl,
        domain.owner.followersUrl,
        domain.owner.followingUrl,
        domain.owner.gistsUrl,
        domain.owner.starredUrl,
        domain.owner.subscriptionsUrl,
        domain.owner.organizationsUrl,
        domain.owner.reposUrl,
        domain.owner.eventsUrl,
        domain.owner.receivedEventsUrl,
        domain.owner.type,
        domain.owner.siteAdmin,
        domain.private,
        domain.htmlUrl,
        domain.description,
        domain.fork,
        domain.url,
        domain.forksUrl,
        domain.keysUrl,
        domain.collaboratorsUrl,
        domain.teamsUrl,
        domain.hooksUrl,
        domain.issueEventsUrl,
        domain.eventsUrl,
        domain.assigneesUrl,
        domain.branchesUrl,
        domain.tagsUrl,
        domain.blobsUrl,
        domain.gitTagsUrl,
        domain.gitRefsUrl,
        domain.treesUrl,
        domain.statusesUrl,
        domain.languagesUrl,
        domain.stargazersUrl,
        domain.contributorsUrl,
        domain.subscribersUrl,
        domain.subscriptionUrl,
        domain.commitsUrl,
        domain.gitCommitsUrl,
        domain.commentsUrl,
        domain.issueCommentUrl,
        domain.contentsUrl,
        domain.compareUrl,
        domain.mergesUrl,
        domain.archiveUrl,
        domain.downloadsUrl,
        domain.issuesUrl,
        domain.pullsUrl,
        domain.milestonesUrl,
        domain.notificationsUrl,
        domain.labelsUrl,
        domain.releasesUrl,
        domain.deploymentsUrl,
        domain.createdAt,
        domain.updatedAt,
        domain.pushedAt,
        domain.gitUrl,
        domain.sshUrl,
        domain.cloneUrl,
        domain.svnUrl,
        domain.homepage,
        domain.size,
        domain.stargazersCount,
        domain.watchersCount,
        domain.language,
        domain.hasIssues,
        domain.hasDownloads,
        domain.hasWiki,
        domain.hasPages,
        domain.forksCount,
        domain.mirrorUrl,
        domain.openIssuesCount,
        domain.forks,
        domain.openIssues,
        domain.watchers,
        domain.defaultBranch,
        domain.permissions.admin,
        domain.permissions.push,
        domain.permissions.pull
    )

    override fun fromEntity(entity: RepositoryEntity): Repository = Repository(
        UserEntityDataMapper.fromEntity(entity.user),
        entity.id,
        entity.name,
        entity.full_name,
        Owner(
            entity.owner_login,
            entity.owner_id,
            entity.owner_avatar_url,
            entity.owner_gravatar_id,
            entity.owner_url,
            entity.owner_html_url,
            entity.owner_followers_url,
            entity.owner_following_url,
            entity.owner_gists_url,
            entity.owner_starred_url,
            entity.owner_subscriptions_url,
            entity.owner_organizations_url,
            entity.owner_repos_url,
            entity.owner_events_url,
            entity.owner_received_events_url,
            entity.owner_type,
            entity.owner_site_admin
        ),
        entity.is_private,
        entity.html_url,
        entity.description,
        entity.fork,
        entity.url,
        entity.forks_url,
        entity.keys_url,
        entity.collaborators_url,
        entity.teams_url,
        entity.hooks_url,
        entity.issue_events_url,
        entity.events_url,
        entity.assignees_url,
        entity.branches_url,
        entity.tags_url,
        entity.blobs_url,
        entity.git_tags_url,
        entity.git_refs_url,
        entity.trees_url,
        entity.statuses_url,
        entity.languages_url,
        entity.stargazers_url,
        entity.contributors_url,
        entity.subscribers_url,
        entity.subscription_url,
        entity.commits_url,
        entity.git_commits_url,
        entity.comments_url,
        entity.issue_comment_url,
        entity.contents_url,
        entity.compare_url,
        entity.merges_url,
        entity.archive_url,
        entity.downloads_url,
        entity.issues_url,
        entity.pulls_url,
        entity.milestones_url,
        entity.notifications_url,
        entity.labels_url,
        entity.releases_url,
        entity.deployments_url,
        entity.created_at,
        entity.updated_at,
        entity.pushed_at,
        entity.git_url,
        entity.ssh_url,
        entity.clone_url,
        entity.svn_url,
        entity.homepage,
        entity.size,
        entity.stargazers_count,
        entity.watchers_count,
        entity.language,
        entity.has_issues,
        entity.has_downloads,
        entity.has_wiki,
        entity.has_pages,
        entity.forks_count,
        entity.mirror_url,
        entity.open_issues_count,
        entity.forks,
        entity.open_issues,
        entity.watchers,
        entity.default_branch,
        Permission(
            entity.permission_admin,
            entity.permission_push,
            entity.permission_pull
        )
    )
}