package com.wada811.ghblog.data.entity.mapper.data

import com.wada811.ghblog.data.entity.data.BlogEntity
import com.wada811.ghblog.domain.model.Blog

object BlogEntityDataMapper : EntityDataMapper<BlogEntity, Blog> {
    override fun toEntity(domain: Blog): BlogEntity = BlogEntity(
        RepositoryEntityDataMapper.toEntity(domain.repository),
        domain.url
    )

    override fun fromEntity(entity: BlogEntity): Blog = Blog(
        UserEntityDataMapper.fromEntity(entity.repository.user),
        RepositoryEntityDataMapper.fromEntity(entity.repository),
        entity.url
    )
}