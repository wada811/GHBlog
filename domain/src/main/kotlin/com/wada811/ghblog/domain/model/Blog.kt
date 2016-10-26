package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.logforest.LogWood
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import rx.schedulers.Schedulers

class Blog(
    val user: User,
    val repository: Repository,
    title: String,
    url: String
) : INotifyPropertyChanged {
    var title: String by PropertyChangedDelegate(title)
    var url: String by PropertyChangedDelegate(url)
    val articles: ObservableSynchronizedArrayList<Article> by lazy {
        loadArticles()
        ObservableSynchronizedArrayList<Article>()
    }
    var currentArticle: Article? by PropertyChangedDelegate(null)

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

    fun loadArticles() {
        GHBlogContext.gitHubRepository.getArticles(user, this)
            .subscribeOn(Schedulers.io())
            .subscribe({
                LogWood.v("loadArticles#onNext: $it")
                articles.add(it)
            }, {
                LogWood.e("loadArticles#onError: $it", it)
            }, {
                LogWood.v("loadArticles#onCompleted")
            })
    }

    fun createArticle(path: String, message: String, content: String) {
        repository.createContent(path, message, content)
            .subscribeOn(Schedulers.io())
            .subscribe({
                LogWood.v("createArticle#onNext: $it")
                articles.add(Article(this, it.content!!.asRepositoryContent()))
            }, {
                LogWood.e("createArticle#onError: $it", it)
            }, {
                LogWood.v("createArticle#onCompleted")
            })
    }
}