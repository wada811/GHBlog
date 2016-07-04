package com.wada811.ghblog.model.domain

import com.wada811.ghblog.util.Base64

class RepositoryContent(
        name: String,
        path: String,
        sha: String,
        size: Int,
        url: String,
        htmlUrl: String,
        gitUrl: String,
        downloadUrl: String,
        type: String,
        contentLink: ContentLink,
        val encoding: String,
        val encodedContent: String
) : RepositoryContentInfo(
        name,
        path,
        sha,
        size,
        url,
        htmlUrl,
        gitUrl,
        downloadUrl,
        type,
        contentLink
) {
    lateinit var content: String

    init {
        content = String(Base64.decode(encodedContent, Base64.NO_WRAP))
    }
}