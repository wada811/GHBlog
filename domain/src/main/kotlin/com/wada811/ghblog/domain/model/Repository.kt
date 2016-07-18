package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import org.threeten.bp.ZonedDateTime
import rx.Observable
import rx.schedulers.Schedulers

class Repository(
    val user: User,
    id: Long,
    name: String,
    fullName: String,
    owner: Owner,
    private: Boolean,
    htmlUrl: String,
    description: String?,
    fork: Boolean,
    url: String,
    forksUrl: String,
    keysUrl: String,
    collaboratorsUrl: String,
    teamsUrl: String,
    hooksUrl: String,
    issueEventsUrl: String,
    eventsUrl: String,
    assigneesUrl: String,
    branchesUrl: String,
    tagsUrl: String,
    blobsUrl: String,
    gitTagsUrl: String,
    gitRefsUrl: String,
    treesUrl: String,
    statusesUrl: String,
    languagesUrl: String,
    stargazersUrl: String,
    contributorsUrl: String,
    subscribersUrl: String,
    subscriptionUrl: String,
    commitsUrl: String,
    gitCommitsUrl: String,
    commentsUrl: String,
    issueCommentUrl: String,
    contentsUrl: String,
    compareUrl: String,
    mergesUrl: String,
    archiveUrl: String,
    downloadsUrl: String,
    issuesUrl: String,
    pullsUrl: String,
    milestonesUrl: String,
    notificationsUrl: String,
    labelsUrl: String,
    releasesUrl: String,
    deploymentsUrl: String,
    createdAt: ZonedDateTime,
    updatedAt: ZonedDateTime,
    pushedAt: ZonedDateTime,
    gitUrl: String,
    sshUrl: String,
    cloneUrl: String,
    svnUrl: String,
    homepage: String?,
    size: Int,
    stargazersCount: Int,
    watchersCount: Int,
    language: String?,
    hasIssues: Boolean,
    hasDownloads: Boolean,
    hasWiki: Boolean,
    hasPages: Boolean,
    forksCount: Int,
    mirrorUrl: String?,
    openIssuesCount: Int,
    forks: Int,
    openIssues: Int,
    watchers: Int,
    defaultBranch: String,
    permissions: Permission
) : INotifyPropertyChanged {
    var id: Long by PropertyChangedDelegate(id)
    var name: String by PropertyChangedDelegate(name)
    var fullName: String by PropertyChangedDelegate(fullName)
    var owner: Owner by PropertyChangedDelegate(owner)
    var private: Boolean by PropertyChangedDelegate(private)
    var htmlUrl: String by PropertyChangedDelegate(htmlUrl)
    var description: String? by PropertyChangedDelegate(description)
    var fork: Boolean by PropertyChangedDelegate(fork)
    var url: String by PropertyChangedDelegate(url)
    var forksUrl: String by PropertyChangedDelegate(forksUrl)
    var keysUrl: String by PropertyChangedDelegate(keysUrl)
    var collaboratorsUrl: String by PropertyChangedDelegate(collaboratorsUrl)
    var teamsUrl: String by PropertyChangedDelegate(teamsUrl)
    var hooksUrl: String by PropertyChangedDelegate(hooksUrl)
    var issueEventsUrl: String by PropertyChangedDelegate(issueEventsUrl)
    var eventsUrl: String by PropertyChangedDelegate(eventsUrl)
    var assigneesUrl: String by PropertyChangedDelegate(assigneesUrl)
    var branchesUrl: String by PropertyChangedDelegate(branchesUrl)
    var tagsUrl: String by PropertyChangedDelegate(tagsUrl)
    var blobsUrl: String by PropertyChangedDelegate(blobsUrl)
    var gitTagsUrl: String by PropertyChangedDelegate(gitTagsUrl)
    var gitRefsUrl: String by PropertyChangedDelegate(gitRefsUrl)
    var treesUrl: String by PropertyChangedDelegate(treesUrl)
    var statusesUrl: String by PropertyChangedDelegate(statusesUrl)
    var languagesUrl: String by PropertyChangedDelegate(languagesUrl)
    var stargazersUrl: String by PropertyChangedDelegate(stargazersUrl)
    var contributorsUrl: String by PropertyChangedDelegate(contributorsUrl)
    var subscribersUrl: String by PropertyChangedDelegate(subscribersUrl)
    var subscriptionUrl: String by PropertyChangedDelegate(subscriptionUrl)
    var commitsUrl: String by PropertyChangedDelegate(commitsUrl)
    var gitCommitsUrl: String by PropertyChangedDelegate(gitCommitsUrl)
    var commentsUrl: String by PropertyChangedDelegate(commentsUrl)
    var issueCommentUrl: String by PropertyChangedDelegate(issueCommentUrl)
    var contentsUrl: String by PropertyChangedDelegate(contentsUrl)
    var compareUrl: String by PropertyChangedDelegate(compareUrl)
    var mergesUrl: String by PropertyChangedDelegate(mergesUrl)
    var archiveUrl: String by PropertyChangedDelegate(archiveUrl)
    var downloadsUrl: String by PropertyChangedDelegate(downloadsUrl)
    var issuesUrl: String by PropertyChangedDelegate(issuesUrl)
    var pullsUrl: String by PropertyChangedDelegate(pullsUrl)
    var milestonesUrl: String by PropertyChangedDelegate(milestonesUrl)
    var notificationsUrl: String by PropertyChangedDelegate(notificationsUrl)
    var labelsUrl: String by PropertyChangedDelegate(labelsUrl)
    var releasesUrl: String by PropertyChangedDelegate(releasesUrl)
    var deploymentsUrl: String by PropertyChangedDelegate(deploymentsUrl)
    var createdAt: ZonedDateTime by PropertyChangedDelegate(createdAt)
    var updatedAt: ZonedDateTime by PropertyChangedDelegate(updatedAt)
    var pushedAt: ZonedDateTime by PropertyChangedDelegate(pushedAt)
    var gitUrl: String by PropertyChangedDelegate(gitUrl)
    var sshUrl: String by PropertyChangedDelegate(sshUrl)
    var cloneUrl: String by PropertyChangedDelegate(cloneUrl)
    var svnUrl: String by PropertyChangedDelegate(svnUrl)
    var homepage: String? by PropertyChangedDelegate(homepage)
    var size: Int by PropertyChangedDelegate(size)
    var stargazersCount: Int by PropertyChangedDelegate(stargazersCount)
    var watchersCount: Int by PropertyChangedDelegate(watchersCount)
    var language: String? by PropertyChangedDelegate(language)
    var hasIssues: Boolean by PropertyChangedDelegate(hasIssues)
    var hasDownloads: Boolean by PropertyChangedDelegate(hasDownloads)
    var hasWiki: Boolean by PropertyChangedDelegate(hasWiki)
    var hasPages: Boolean by PropertyChangedDelegate(hasPages)
    var forksCount: Int by PropertyChangedDelegate(forksCount)
    var mirrorUrl: String? by PropertyChangedDelegate(mirrorUrl)
    var openIssuesCount: Int by PropertyChangedDelegate(openIssuesCount)
    var forks: Int by PropertyChangedDelegate(forks)
    var openIssues: Int by PropertyChangedDelegate(openIssues)
    var watchers: Int by PropertyChangedDelegate(watchers)
    var defaultBranch: String by PropertyChangedDelegate(defaultBranch)
    var permissions: Permission by PropertyChangedDelegate(permissions)

    val repositoryContents: ObservableSynchronizedArrayList<RepositoryContent> = ObservableSynchronizedArrayList()
    var currentRepositoryContent: RepositoryContent? by PropertyChangedDelegate(null)

    fun loadContents(path : String) {
        getContents(path)
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                repositoryContents.addAll(it.map { RepositoryContent(it) })
            })
    }

    fun getContents(path: String) = GHBlogContext.gitHubRepository.getContents(user, this, path)
    fun getContent(path: String) = GHBlogContext.gitHubRepository.getContent(user, this, path)
    fun createContent(path: String, message: String, content: String): Observable<GitHubCommit> {
        val commit = GitCommit(path, message, content)
        return GHBlogContext.gitHubRepository.createContent(user, this, commit)
    }

    fun getTree() = GHBlogContext.gitHubRepository.getTree(user, this)
}