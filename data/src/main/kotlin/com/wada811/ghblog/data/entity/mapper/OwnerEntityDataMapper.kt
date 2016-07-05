package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.OwnerEntity
import com.wada811.ghblog.domain.model.Owner

object OwnerEntityDataMapper {
    fun transform(entity: OwnerEntity): Owner = Owner(
            entity.login,
            entity.id,
            entity.avatar_url,
            entity.gravatar_id,
            entity.url,
            entity.html_url,
            entity.followers_url,
            entity.following_url,
            entity.gists_url,
            entity.starred_url,
            entity.subscriptions_url,
            entity.organizations_url,
            entity.repos_url,
            entity.events_url,
            entity.received_events_url,
            entity.type,
            entity.site_admin)
}

