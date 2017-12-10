package com.wada811.ghblog.data.entity.data

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import org.threeten.bp.ZonedDateTime


@Table("commit")
data class CommitEntity(
    @Setter("path") @Column(indexed = true) val path: String,
    @Setter("message") @Column val message: String,
    @Setter("content") @Column val content: String,
    @Setter("created") @Column val created: ZonedDateTime = ZonedDateTime.now()
)