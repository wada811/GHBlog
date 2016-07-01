package com.wada811.ghblog.model.domain

import org.apache.commons.codec.binary.Base64

class GitCommit(
        var path: String,
        var message: String,
        var content: String
) {
    var encodedContent: String = Base64.encodeBase64String(content.toByteArray())
}