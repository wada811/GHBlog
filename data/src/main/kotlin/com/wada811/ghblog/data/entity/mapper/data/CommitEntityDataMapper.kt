package com.wada811.ghblog.data.entity.mapper.data

import com.wada811.ghblog.data.entity.data.CommitEntity
import com.wada811.ghblog.domain.model.GitCommit

object CommitEntityDataMapper : EntityDataMapper<CommitEntity, GitCommit> {
    override fun toEntity(domain: GitCommit): CommitEntity {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromEntity(entity: CommitEntity): GitCommit {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
