package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.repository.GitHubRepository
import org.threeten.bp.ZonedDateTime
import rx.Observable

class Repository(
        var id: Long,
        var name: String,
        var fullName: String,
        var owner: Owner,
        var private: Boolean,
        var htmlUrl: String,
        var description: String?,
        var fork: Boolean,
        var url: String,
        var forksUrl: String,
        var keysUrl: String,
        var collaboratorsUrl: String,
        var teamsUrl: String,
        var hooksUrl: String,
        var issueEventsUrl: String,
        var eventsUrl: String,
        var assigneesUrl: String,
        var branchesUrl: String,
        var tagsUrl: String,
        var blobsUrl: String,
        var gitTagsUrl: String,
        var gitRefsUrl: String,
        var treesUrl: String,
        var statusesUrl: String,
        var languagesUrl: String,
        var stargazersUrl: String,
        var contributorsUrl: String,
        var subscribersUrl: String,
        var subscriptionUrl: String,
        var commitsUrl: String,
        var gitCommitsUrl: String,
        var commentsUrl: String,
        var issueCommentUrl: String,
        var contentsUrl: String,
        var compareUrl: String,
        var mergesUrl: String,
        var archiveUrl: String,
        var downloadsUrl: String,
        var issuesUrl: String,
        var pullsUrl: String,
        var milestonesUrl: String,
        var notificationsUrl: String,
        var labelsUrl: String,
        var releasesUrl: String,
        var deploymentsUrl: String,
        var createdAt: ZonedDateTime,
        var updatedAt: ZonedDateTime,
        var pushedAt: ZonedDateTime,
        var gitUrl: String,
        var sshUrl: String,
        var cloneUrl: String,
        var svnUrl: String,
        var homepage: String?,
        var size: Int,
        var stargazersCount: Int,
        var watchersCount: Int,
        var language: String?,
        var hasIssues: Boolean,
        var hasDownloads: Boolean,
        var hasWiki: Boolean,
        var hasPages: Boolean,
        var forksCount: Int,
        var mirrorUrl: String?,
        var openIssuesCount: Int,
        var forks: Int,
        var openIssues: Int,
        var watchers: Int,
        var defaultBranch: String,
        var permissions: Permission
) {
    fun getContents(user: User, path: String): Observable<List<RepositoryContentInfo>> = GHBlogContext.gitHubRepository.getContents(user, this, path)
    fun getContent(user: User, path: String): Observable<RepositoryContent> = GHBlogContext.gitHubRepository.getContent(user, this, path)
    fun createContent(user: User, commit: GitCommit): Observable<GitHubCommit> = GHBlogContext.gitHubRepository.createContent(user, this, commit)
    fun updateContent(user: User, commit: GitCommit): Observable<GitHubCommit> = GHBlogContext.gitHubRepository.updateContent(user, this, commit)
    fun getTree(user: User): Observable<GitHubTree> = GHBlogContext.gitHubRepository.getTree(user, this)
}