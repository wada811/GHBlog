package com.wada811.ghblog.data.entity.data

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table("blog")
data class BlogEntity(
    @Setter("repository") @PrimaryKey @Column(indexed = true) val repository: RepositoryEntity,
    @Setter("url") @Column val url: String
)
