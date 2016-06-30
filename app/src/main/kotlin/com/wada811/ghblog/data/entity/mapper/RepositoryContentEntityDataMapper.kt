package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.RepositoryContentEntity
import com.wada811.ghblog.data.entity.RepositoryContentInfoEntity
import com.wada811.ghblog.model.domain.RepositoryContent
import com.wada811.ghblog.model.domain.RepositoryContentInfo

object RepositoryContentEntityDataMapper {
    fun transform(entity: RepositoryContentEntity): RepositoryContent = RepositoryContent(
            entity.name,
            entity.path,
            entity.sha,
            entity.size,
            entity.url,
            entity.html_url,
            entity.git_url,
            entity.download_url,
            entity.type,
            ContentLinkEntityDataMapper.transform(entity._links),
            entity.encoding,
            entity.content
    )

    object ContentLinkEntityDataMapper {
        fun transform(entity: RepositoryContentInfoEntity.ContentLinkEntity): RepositoryContentInfo.ContentLink = RepositoryContentInfo.ContentLink(
                entity.self,
                entity.git,
                entity.html
        )
    }
}