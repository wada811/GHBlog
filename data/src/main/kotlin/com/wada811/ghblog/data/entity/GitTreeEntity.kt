package com.wada811.ghblog.data.entity

data class GitTreeEntity(
        var sha: String,
        var url: String,
        var tree: List<NodeEntity>,
        var truncated: Boolean
) {
    data class NodeEntity(
            var path: String,
            var mode: String,
            var type: String,
            var size: Int,
            var sha: String,
            var url: String
    )
}
