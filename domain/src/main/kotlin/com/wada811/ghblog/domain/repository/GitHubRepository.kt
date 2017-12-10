package com.wada811.ghblog.domain.repository

import com.wada811.ghblog.domain.model.*
import rx.Observable

interface GitHubRepository {
    fun getRepositories(user: User): Observable<Repository>
    fun saveRepository(repository: Repository): Observable<Boolean>
    fun getBlogs(user: User): Observable<Blog>
    fun saveBlog(blog: Blog): Observable<Boolean>
    fun getArticles(user: User, blog: Blog): Observable<Article>
    fun saveArticle(article: Article): Observable<Boolean>
    fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContent>>
    fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent>
    fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun renameContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun getTree(user: User, repository: Repository): Observable<GitHubTree>
    fun createTree(user: User, repository: Repository, gitTree: GitTree): Observable<GitHubTree>
}