package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.GitTreeEntity
import com.wada811.ghblog.model.domain.GitHubTree

object GitTreeEntityDataMapper {
    fun transform(entity: GitTreeEntity): GitHubTree = GitHubTree(
            entity.sha,
            entity.url,
            entity.tree.map { NodeEntityDataMapper.transform(it) },
            entity.truncated
    )

    object NodeEntityDataMapper {
        fun transform(entity: GitTreeEntity.NodeEntity): GitHubTree.Node = GitHubTree.Node(
                entity.path,
                entity.mode,
                entity.type,
                entity.size,
                entity.sha,
                entity.url
        )
    }
}