package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.GitTreeEntity
import com.wada811.ghblog.model.domain.GitTree

object GitTreeEntityDataMapper {
    fun transform(entity: GitTreeEntity): GitTree {
        return GitTree(
                entity.sha,
                entity.url,
                entity.tree.map { NodeEntityDataMapper.transform(it) },
                entity.truncated
        )
    }

    object NodeEntityDataMapper {
        fun transform(entity: GitTreeEntity.NodeEntity): GitTree.Node = GitTree.Node(
                entity.path,
                entity.mode,
                entity.type,
                entity.size,
                entity.sha,
                entity.url
        )
    }
}