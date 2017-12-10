package com.wada811.ghblog.data.datasource.github

import com.wada811.ghblog.data.entity.data.CommitEntity
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.domain.model.Blog
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.User
import rx.Observable

interface GitHubDataSource {
    fun getRepositories(user: User): Observable<Repository>
    fun saveRepository(repository: Repository): Observable<Boolean>
    fun saveRepositories(repositories: MutableList<Repository>): Observable<Boolean>
    fun getBlogs(user: User): Observable<Blog>
    fun saveBlog(blog: Blog): Observable<Boolean>
    fun getArticles(blog: Blog): Observable<Article>
    fun saveArticle(article: Article): Observable<Boolean>
    fun saveArticles(articles: MutableList<Article>): Observable<Boolean>

    fun saveCommit(commit: CommitEntity): Observable<Boolean>

}