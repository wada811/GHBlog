package com.wada811.ghblog.data.entity.mapper.data

interface EntityDataMapper<TEntity, TDomain> {
    fun toEntity(domain: TDomain): TEntity
    fun fromEntity(entity: TEntity): TDomain
}