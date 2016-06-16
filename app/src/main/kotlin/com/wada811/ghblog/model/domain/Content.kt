package com.wada811.ghblog.model.domain

class Content(
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
}