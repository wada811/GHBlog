package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import org.threeten.bp.ZonedDateTime
import rx.schedulers.Schedulers

class User(
    accessToken: String,
    login: String,
    id: Int,
    avatarUrl: String,
    gravatarId: String,
    url: String,
    htmlUrl: String,
    followersUrl: String,
    followingUrl: String,
    gistsUrl: String,
    starredUrl: String,
    subscriptionsUrl: String,
    organizationsUrl: String,
    reposUrl: String,
    eventsUrl: String,
    receivedEventsUrl: String,
    type: String,
    siteAdmin: Boolean,
    name: String,
    company: String?,
    blog: String?,
    location: String?,
    email: String?,
    hireable: Boolean,
    bio: String?,
    publicRepos: Int,
    publicGists: Int,
    followers: Int,
    following: Int,
    createdAt: ZonedDateTime,
    updatedAt: ZonedDateTime,
    totalPrivateRepos: Int,
    ownedPrivateRepos: Int,
    privateGists: Int,
    diskUsage: Int,
    collaborators: Int,
    plan: Plan
) : INotifyPropertyChanged {
    var accessToken: String by PropertyChangedDelegate(accessToken)
    var login: String by PropertyChangedDelegate(login)
    var id: Int by PropertyChangedDelegate(id)
    var avatarUrl: String by PropertyChangedDelegate(avatarUrl)
    var gravatarId: String by PropertyChangedDelegate(gravatarId)
    var url: String by PropertyChangedDelegate(url)
    var htmlUrl: String by PropertyChangedDelegate(htmlUrl)
    var followersUrl: String by PropertyChangedDelegate(followersUrl)
    var followingUrl: String by PropertyChangedDelegate(followingUrl)
    var gistsUrl: String by PropertyChangedDelegate(gistsUrl)
    var starredUrl: String by PropertyChangedDelegate(starredUrl)
    var subscriptionsUrl: String by PropertyChangedDelegate(subscriptionsUrl)
    var organizationsUrl: String by PropertyChangedDelegate(organizationsUrl)
    var reposUrl: String by PropertyChangedDelegate(reposUrl)
    var eventsUrl: String by PropertyChangedDelegate(eventsUrl)
    var receivedEventsUrl: String by PropertyChangedDelegate(receivedEventsUrl)
    var type: String by PropertyChangedDelegate(type)
    var siteAdmin: Boolean by PropertyChangedDelegate(siteAdmin)
    var name: String by PropertyChangedDelegate(name)
    var company: String? by PropertyChangedDelegate(company)
    var blog: String? by PropertyChangedDelegate(blog)
    var location: String? by PropertyChangedDelegate(location)
    var email: String? by PropertyChangedDelegate(email)
    var hireable: Boolean by PropertyChangedDelegate(hireable)
    var bio: String? by PropertyChangedDelegate(bio)
    var publicRepos: Int by PropertyChangedDelegate(publicRepos)
    var publicGists: Int by PropertyChangedDelegate(publicGists)
    var followers: Int by PropertyChangedDelegate(followers)
    var following: Int by PropertyChangedDelegate(following)
    var createdAt: ZonedDateTime by PropertyChangedDelegate(createdAt)
    var updatedAt: ZonedDateTime by PropertyChangedDelegate(updatedAt)
    var totalPrivateRepos: Int by PropertyChangedDelegate(totalPrivateRepos)
    var ownedPrivateRepos: Int by PropertyChangedDelegate(ownedPrivateRepos)
    var privateGists: Int by PropertyChangedDelegate(privateGists)
    var diskUsage: Int by PropertyChangedDelegate(diskUsage)
    var collaborators: Int by PropertyChangedDelegate(collaborators)
    var plan: Plan by PropertyChangedDelegate(plan)

    class Plan(
        name: String,
        space: Int,
        privateRepos: Int,
        collaborators: Int
    ) : INotifyPropertyChanged {
        var name: String by PropertyChangedDelegate(name)
        var space: Int by PropertyChangedDelegate(space)
        var privateRepos: Int by PropertyChangedDelegate(privateRepos)
        var collaborators: Int by PropertyChangedDelegate(collaborators)
    }

    val repositories = ObservableSynchronizedArrayList<Repository>()

    fun loadRepositories() {
        GHBlogContext.gitHubRepository.getRepositoryList(this)
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                repositories.addAll(it)
            }, {
                System.out.println("onError: $it")
                it.printStackTrace()
            })
    }

    var currentRepository: Repository? by PropertyChangedDelegate(null)

}