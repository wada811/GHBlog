package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate
import rx.Observable

class User(userName: String, accessToken: String) : INotifyPropertyChanged {
    var userName: String by PropertyChangedDelegate(userName)
    var accessToken: String by PropertyChangedDelegate(accessToken)

    var repositoryList: Observable<List<Repository>> = GHBlogContext.gitHubRepository.getRepositoryList(this)
    var currentRepository: Repository? by PropertyChangedDelegate(null)
}