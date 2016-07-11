package com.wada811.ghblog.data.entity.response.github.repos.contents

data class GetContentResponse(
    var name: String,
    var path: String,
    var sha: String,
    var size: Int,
    var url: String,
    var html_url: String,
    var git_url: String,
    var download_url: String,
    var type: String,
    var _links: ContentLinkResponse,
    var encoding: String,
    var content: String
){
    data class ContentLinkResponse(
        var self: String,
        var git: String,
        var html: String
    )
}