package com.wada811.ghblog.data.entity

import com.wada811.ghblog.model.domain.Permission


object PermissionEntityDataMapper {
    fun transform(entity: PermissionEntity): Permission = Permission(
            entity.admin,
            entity.push,
            entity.pull
    )
}
