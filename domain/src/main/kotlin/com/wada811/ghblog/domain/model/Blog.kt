package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.logforest.LogWood
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import rx.schedulers.Schedulers

class Blog(
    val user: User,
    val repository: Repository,
    url: String
) : INotifyPropertyChanged {
    var url: String by PropertyChangedDelegate(url)

    fun save() {
        GHBlogContext.gitHubRepository.saveBlog(this)
            .subscribeOn(Schedulers.io())
            .subscribe({
                LogWood.v("GHBlogContext.gitHubRepository.saveBlog(this)#onNext: $it")
            }, {
                LogWood.e("GHBlogContext.gitHubRepository.saveBlog(this)#onError: $it", it)
            }, {
                LogWood.v("GHBlogContext.gitHubRepository.saveBlog(this)#onCompleted")
            })
    }
}