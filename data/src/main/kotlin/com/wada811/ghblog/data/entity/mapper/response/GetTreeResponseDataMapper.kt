package com.wada811.ghblog.data.entity.mapper.response

import com.wada811.ghblog.data.entity.response.github.git.trees.GetTreeResponse
import com.wada811.ghblog.domain.model.GitHubTree
import com.wada811.ghblog.domain.model.GitHubTree.Node

object GetTreeResponseDataMapper {
    fun transform(response: GetTreeResponse) = GitHubTree(
        response.sha,
        response.url,
        response.tree.map { NodeResponseDataMapper.transform(it) },
        response.truncated
    )

    object NodeResponseDataMapper {
        fun transform(response: GetTreeResponse.NodeResponse) = Node(
            response.path,
            response.mode,
            response.type,
            response.size,
            response.sha,
            response.url
        )
    }
}