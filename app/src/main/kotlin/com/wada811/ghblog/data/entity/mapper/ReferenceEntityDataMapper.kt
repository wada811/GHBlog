package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.ReferenceEntity
import com.wada811.ghblog.model.domain.Reference
import com.wada811.ghblog.model.domain.Reference.ReferenceObject

object ReferenceEntityDataMapper {
    fun transform(entity: ReferenceEntity): Reference
            = Reference(entity.ref, entity.url, ReferenceObjectEntityDataMapper().transform(entity.`object`))

    class ReferenceObjectEntityDataMapper() {
        fun transform(entity: ReferenceEntity.ReferenceObjectEntity): ReferenceObject
                = ReferenceObject(entity.type, entity.sha, entity.url)
    }
}