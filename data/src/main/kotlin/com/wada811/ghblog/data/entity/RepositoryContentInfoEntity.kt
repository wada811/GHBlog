package com.wada811.ghblog.data.entity

data class RepositoryContentInfoEntity(
        var name: String,
        var path: String,
        var sha: String,
        var size: Int,
        var url: String,
        var html_url: String,
        var git_url: String,
        var download_url: String,
        var type: String,
        var _links: ContentLinkEntity
) {
    data class ContentLinkEntity(
            var self: String,
            var git: String,
            var html: String
    )
}
