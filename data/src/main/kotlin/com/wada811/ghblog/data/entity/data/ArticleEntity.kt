package com.wada811.ghblog.data.entity.data

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import org.threeten.bp.ZonedDateTime

@Table("article")
data class ArticleEntity(
    @Setter("blog") @Column(indexed = true) val blog: BlogEntity,
    @Setter("path") @Column val path: String,
    @Setter("publishDateTime") @Column val publishDateTime: ZonedDateTime,
    @Setter("isDraft") @Column val isDraft: Boolean,
    @Setter("title") @Column val title: String,
    @Setter("tags") @Column val tags: List<String>,
    @Setter("body") @Column val body: String
)
