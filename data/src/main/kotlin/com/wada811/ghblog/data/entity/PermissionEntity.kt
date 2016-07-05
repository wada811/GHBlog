package com.wada811.ghblog.data.entity

data class PermissionEntity(
        var admin: Boolean,
        var push: Boolean,
        var pull: Boolean
)
