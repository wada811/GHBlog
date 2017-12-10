package com.wada811.ghblog.data.entity.mapper.data

import com.wada811.ghblog.data.entity.data.ArticleEntity
import com.wada811.ghblog.domain.model.Article
import com.wada811.observablemodel.ObservableSynchronizedArrayList

object ArticleEntityDataMapper : EntityDataMapper<ArticleEntity, Article> {
    override fun toEntity(domain: Article): ArticleEntity = ArticleEntity(
        BlogEntityDataMapper.toEntity(domain.blog),
        domain.filePath,
        domain.publishDateTime,
        domain.isDraft,
        domain.title,
        domain.tags,
        domain.body
    )

    override fun fromEntity(entity: ArticleEntity): Article = Article(
        BlogEntityDataMapper.fromEntity(entity.blog),
        null,
        entity.path,
        entity.publishDateTime,
        entity.isDraft,
        entity.title,
        ObservableSynchronizedArrayList(entity.tags),
        entity.body
    )
}