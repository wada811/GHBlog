package com.wada811.ghblog.data.entity.mapper.response

import com.wada811.ghblog.data.entity.response.github.git.trees.CreateTreeResponse
import com.wada811.ghblog.data.entity.response.github.git.trees.CreateTreeResponse.NodeResponse
import com.wada811.ghblog.domain.model.GitHubTree
import com.wada811.ghblog.domain.model.GitHubTree.Node

object CreateTreeResponseDataMapper {
    fun transform(response: CreateTreeResponse) = GitHubTree(
        response.sha,
        response.url,
        response.tree.map { NodeResponseDataMapper.transform(it) }
    )

    object NodeResponseDataMapper {
        fun transform(response: NodeResponse) = Node(
            response.path,
            response.mode,
            response.type,
            response.size,
            response.sha,
            response.url
        )
    }
}