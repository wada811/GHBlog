package com.wada811.ghblog.domain.model

class GitHubTree(
        var sha: String,
        var url: String,
        var tree: List<Node>? = null,
        var truncated: Boolean? = null
){
    class Node(
            var path: String,
            var mode: String,
            var type: String,
            var size: Int,
            var sha: String,
            var url: String
    )
}