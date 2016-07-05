package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.RepositoryContentInfoEntity
import com.wada811.ghblog.data.entity.RepositoryContentInfoEntity.ContentLinkEntity
import com.wada811.ghblog.domain.model.RepositoryContentInfo
import com.wada811.ghblog.domain.model.RepositoryContentInfo.ContentLink

object RepositoryContentInfoEntityDataMapper {
    fun transform(entity: RepositoryContentInfoEntity): RepositoryContentInfo = RepositoryContentInfo(
            entity.name,
            entity.path,
            entity.sha,
            entity.size,
            entity.url,
            entity.html_url,
            entity.git_url,
            entity.download_url,
            entity.type,
            ContentLinkEntityDataMapper.transform(entity._links)
    )

    object ContentLinkEntityDataMapper {
        fun transform(entity: ContentLinkEntity): ContentLink = ContentLink(
                entity.self,
                entity.git,
                entity.html
        )
    }
}