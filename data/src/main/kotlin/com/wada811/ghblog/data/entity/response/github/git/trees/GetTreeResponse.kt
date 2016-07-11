package com.wada811.ghblog.data.entity.response.github.git.trees

data class GetTreeResponse(
    var sha: String,
    var url: String,
    var tree: List<NodeResponse>,
    var truncated: Boolean
) {
    data class NodeResponse(
        var path: String,
        var mode: String,
        var type: String,
        var size: Int,
        var sha: String,
        var url: String
    )
}
