package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.ContentEntity
import com.wada811.ghblog.data.entity.ContentEntity.ContentLinkEntity
import com.wada811.ghblog.model.domain.Content
import com.wada811.ghblog.model.domain.Content.ContentLink

object ContentEntityDataMapper {
    fun transform(entity: ContentEntity): Content = Content(
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