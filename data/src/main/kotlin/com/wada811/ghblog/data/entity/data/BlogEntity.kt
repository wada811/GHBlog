package com.wada811.ghblog.data.entity.data

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table("blog")
data class BlogEntity(
    @Setter("id") @PrimaryKey @Column val repositoryId: Long,
    @Setter("repository") @Column(indexed = true) val repository: RepositoryEntity,
    @Setter("title") @Column val title: String,
    @Setter("url") @Column val url: String
)
