package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.ReferenceEntity
import com.wada811.ghblog.domain.model.Reference
import com.wada811.ghblog.domain.model.Reference.ReferenceObject

object ReferenceEntityDataMapper {
    fun transform(entity: ReferenceEntity): Reference
            = Reference(entity.ref, entity.url, ReferenceObjectEntityDataMapper().transform(entity.`object`))

    class ReferenceObjectEntityDataMapper() {
        fun transform(entity: ReferenceEntity.ReferenceObjectEntity): Reference.ReferenceObject
                = ReferenceObject(entity.type, entity.sha, entity.url)
    }
}