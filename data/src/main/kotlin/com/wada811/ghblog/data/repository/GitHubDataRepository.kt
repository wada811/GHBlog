package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.github.GitHubDataSource
import com.wada811.ghblog.data.datasource.github.RemoteGitHubDataSource
import com.wada811.ghblog.domain.model.*
import com.wada811.ghblog.domain.repository.GitHubRepository
import com.wada811.logforest.LogWood
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import rx.Observable
import rx.schedulers.Schedulers

class GitHubDataRepository(private val localDataSource: GitHubDataSource, private val remoteDataSource: GitHubDataSource) : GitHubRepository {
    override fun getRepositories(user: User): Observable<Repository> {
        return Observable.defer {
            val repositories = ObservableSynchronizedArrayList<Repository>()
            remoteDataSource.getRepositories(user)
                .doOnNext {
                    LogWood.i("getRepositories#doOnNext: ${it.fullName}")
                    repositories.add(it)
                }
                .doOnCompleted {
                    localDataSource.saveRepositories(repositories)
                        .observeOn(Schedulers.io())
                        .doOnNext { LogWood.v("localDataSource.saveRepository#doOnNext: $it") }
                        .doOnError { LogWood.e("localDataSource.saveRepository#doOnError: $it", it) }
                        .doOnCompleted { LogWood.v("localDataSource.saveRepository#doOnCompleted") }
                        .subscribe()
                }
                .onErrorResumeNext {
                    LogWood.e("getRepositories#onErrorResumeNext: $it")
                    localDataSource.getRepositories(user)
                        .doOnNext { LogWood.i("localDataSource.getRepositories#onNext: $it") }
                        .doOnError { LogWood.e("localDataSource.getRepositories#onError: $it") }
                        .doOnCompleted { LogWood.i("localDataSource.getRepositories#onCompleted") }
                }
        }
    }

    override fun saveRepository(repository: Repository): Observable<Boolean> {
        return Observable.defer {
            localDataSource.saveRepository(repository)
        }
    }

    override fun getBlogs(user: User): Observable<Blog> {
        return Observable.defer {
            localDataSource.getBlogs(user)
                .doOnNext { LogWood.i("localDataSource.getBlogs#onNext: $it") }
                .doOnError { LogWood.e("localDataSource.getBlogs#onError: $it") }
                .doOnCompleted { LogWood.i("localDataSource.getBlogs#onCompleted") }
        }
    }

    override fun saveBlog(blog: Blog): Observable<Boolean> {
        return Observable.defer {
            localDataSource.saveBlog(blog)
        }
    }

    override fun getArticles(user: User, blog: Blog): Observable<Article> {
        return Observable.defer {
            RemoteGitHubDataSource().getContents(user, blog.repository, "content/blog")
                .flatMap { Observable.from(it) }
                .flatMap { it.loadContent() }
                .map { Article(blog, it) }
        }
    }

    override fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContent>> {
        return Observable.defer {
            RemoteGitHubDataSource().getContents(user, repository, path)
        }
    }

    override fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent> {
        return Observable.defer {
            RemoteGitHubDataSource().getContent(user, repository, path)
        }
    }

    override fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            RemoteGitHubDataSource().createContent(user, repository, commit)
        }
    }

    override fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            RemoteGitHubDataSource().updateContent(user, repository, commit)
        }
    }

    override fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            RemoteGitHubDataSource().deleteContent(user, repository, commit)
        }
    }

    override fun renameContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            RemoteGitHubDataSource().renameContent(user, repository, commit)
        }
    }

    override fun getTree(user: User, repository: Repository): Observable<GitHubTree> {
        return Observable.defer {
            RemoteGitHubDataSource().getTree(user, repository)
        }
    }

    override fun createTree(user: User, repository: Repository, gitTree: GitTree): Observable<GitHubTree> {
        return Observable.defer {
            RemoteGitHubDataSource().createGitTree(user, repository, gitTree)
        }
    }
}