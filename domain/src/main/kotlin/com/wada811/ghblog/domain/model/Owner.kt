package com.wada811.ghblog.domain.model

class Owner(
        var login: String,
        var id: Long,
        var avatarUrl: String,
        var gravatarId: String,
        var url: String,
        var htmlUrl: String,
        var followersUrl: String,
        var followingUrl: String,
        var gistsUrl: String,
        var starredUrl: String,
        var subscriptionsUrl: String,
        var organizationsUrl: String,
        var reposUrl: String,
        var eventsUrl: String,
        var receivedEventsUrl: String,
        var type: String,
        var siteAdmin: Boolean
)

