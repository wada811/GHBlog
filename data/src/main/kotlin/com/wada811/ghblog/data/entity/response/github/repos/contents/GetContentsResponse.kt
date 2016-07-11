package com.wada811.ghblog.data.entity.response.github.repos.contents

data class GetContentsResponse(
        var name: String,
        var path: String,
        var sha: String,
        var size: Int,
        var url: String,
        var html_url: String,
        var git_url: String,
        var download_url: String,
        var type: String,
        var _links: ContentLinkResponse
) {
    data class ContentLinkResponse(
            var self: String,
            var git: String,
            var html: String
    )
}
