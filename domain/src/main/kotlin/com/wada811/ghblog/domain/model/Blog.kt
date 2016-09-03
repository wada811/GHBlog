package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate

class Blog(
    val user: User,
    val repository: Repository,
    url: String
) : INotifyPropertyChanged {
    var url: String by PropertyChangedDelegate(url)

    fun save(){
        GHBlogContext.gitHubRepository.saveBlog(this)
    }
}