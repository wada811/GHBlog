package com.wada811.ghblog.model.domain

import android.util.Base64


class GitCommit(
        var path: String,
        var message: String,
        var content: String
) {
    var encodedContent: String = Base64.encodeToString(content.toByteArray(), Base64.DEFAULT)
}