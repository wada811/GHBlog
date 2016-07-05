package com.wada811.ghblog.data.entity

import com.wada811.ghblog.data.entity.RepositoryContentInfoEntity.ContentLinkEntity

data class RepositoryContentEntity(
        var name: String,
        var path: String,
        var sha: String,
        var size: Int,
        var url: String,
        var html_url: String,
        var git_url: String,
        var download_url: String,
        var type: String,
        var _links: ContentLinkEntity,
        var encoding: String,
        var content: String

) {
}
