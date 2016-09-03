package com.wada811.ghblog.data.entity.data

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table("blog")
data class BlogEntity(
    @Setter("user") @Column(indexed = true) val user: UserEntity,
    @Setter("repository") @Column val repository: RepositoryEntity,
    @Setter("url") @Column val url: String
)
