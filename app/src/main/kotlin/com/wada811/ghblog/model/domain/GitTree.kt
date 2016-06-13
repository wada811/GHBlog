package com.wada811.ghblog.model.domain

class GitTree(
        var sha: String,
        var url: String,
        var tree: List<Node>,
        var truncated: Boolean
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