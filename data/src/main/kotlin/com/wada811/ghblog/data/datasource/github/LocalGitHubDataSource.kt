package com.wada811.ghblog.data.datasource.github

import android.content.Context
import com.wada811.ghblog.data.entity.data.CommitEntity
import com.wada811.ghblog.data.entity.data.OrmaDatabase
import com.wada811.ghblog.data.entity.data.RepositoryEntity
import com.wada811.ghblog.data.entity.mapper.data.ArticleEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.data.BlogEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.data.RepositoryEntityDataMapper
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.domain.model.Blog
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.User
import com.wada811.logforest.LogWood
import rx.Observable

class LocalGitHubDataSource(context: Context) : GitHubDataSource {
    private val database: OrmaDatabase = OrmaDatabase.builder(context).trace(true).build()
    override fun getRepositories(user: User): Observable<Repository> {
        return database.selectFromRepositoryEntity()
            .executeAsObservable()
            .filter { it.user.id == user.id }
            .map { repositoryEntity: RepositoryEntity ->
                LogWood.v("LocalUserDataSource#getCurrentUser#map")
                RepositoryEntityDataMapper.fromEntity(repositoryEntity)
            }
    }

    override fun saveRepository(repository: Repository): Observable<Boolean> {
        LogWood.d("LocalGitHubDataSource.saveRepository")
        return database.relationOfRepositoryEntity()
            .upserter()
            .executeAsObservable(RepositoryEntityDataMapper.toEntity(repository))
            .map { !it.equals(-1) }
            .toObservable()
    }

    override fun saveRepositories(repositories: MutableList<Repository>): Observable<Boolean> {
        LogWood.d("LocalGitHubDataSource.saveRepositories")
        return database.relationOfRepositoryEntity()
            .upserter()
            .executeAllAsObservable(repositories.map { RepositoryEntityDataMapper.toEntity(it) })
            .map { !it.equals(-1) }
    }

    override fun getBlogs(user: User): Observable<Blog> {
        return database.selectFromBlogEntity()
            .executeAsObservable()
            .filter { it.repository.user.id == user.id }
            .map { BlogEntityDataMapper.fromEntity(it) }
    }

    override fun saveBlog(blog: Blog): Observable<Boolean> {
        return database.relationOfBlogEntity()
            .upserter()
            .executeAsObservable(BlogEntityDataMapper.toEntity(blog))
            .map { !it.equals(-1) }
            .toObservable()
    }

    override fun getArticles(blog: Blog): Observable<Article> {
        return database.selectFromArticleEntity()
            .executeAsObservable()
            .filter { it.blog.repositoryId == blog.repository.id }
            .map { ArticleEntityDataMapper.fromEntity(it) }
    }

    override fun saveArticle(article: Article): Observable<Boolean> {
        return database.relationOfArticleEntity()
            .upserter()
            .executeAsObservable(ArticleEntityDataMapper.toEntity(article))
            .map { !it.equals(-1) }
            .toObservable()
    }

    override fun saveArticles(articles: MutableList<Article>): Observable<Boolean> {
        return database.relationOfArticleEntity()
            .upserter()
            .executeAllAsObservable(articles.map { ArticleEntityDataMapper.toEntity(it) })
            .map { !it.equals(-1) }
    }

    override fun saveCommit(commit: CommitEntity): Observable<Boolean> {
        return database.relationOfCommitEntity()
            .inserter()
            .executeAsObservable(commit)
            .map { !it.equals(-1) }
            .toObservable()
    }
}