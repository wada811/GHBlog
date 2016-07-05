package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.PermissionEntity
import com.wada811.ghblog.domain.model.Permission


object PermissionEntityDataMapper {
    fun transform(entity: PermissionEntity): Permission = Permission(
            entity.admin,
            entity.push,
            entity.pull
    )
}
