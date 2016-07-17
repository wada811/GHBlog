package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import rx.schedulers.Schedulers

class User(userName: String, accessToken: String) : INotifyPropertyChanged {
    var userName: String by PropertyChangedDelegate(userName)
    var accessToken: String by PropertyChangedDelegate(accessToken)


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