package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import rx.Observable

open class RepositoryContentInfo(
        var name: String,
        var path: String,
        var sha: String,
        var size: Int,
        var url: String,
        var htmlUrl: String,
        var gitUrl: String,
        var downloadUrl: String,
        var type: String,
        var contentLink: ContentLink
) {
    class ContentLink(
            var self: String,
            var git: String,
            var html: String
    )

    fun getContent(user: User, repository: Repository): Observable<RepositoryContent> = GHBlogContext.gitHubRepository.getContent(user, repository, path)
}