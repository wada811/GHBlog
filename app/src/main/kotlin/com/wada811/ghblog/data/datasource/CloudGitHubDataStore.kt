package com.wada811.ghblog.data.datasource

import android.util.Log
import com.wada811.ghblog.data.entity.RepositoryEntity
import com.wada811.ghblog.data.entity.RepositoryEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.GitTreeEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.RepositoryContentEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.RepositoryContentInfoEntityDataMapper
import com.wada811.ghblog.data.entity.request.github.repos.contents.CreateContentRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.UpdateContentRequest
import com.wada811.ghblog.data.http.ApiInfoParser
import com.wada811.ghblog.data.http.GitHubApi
import com.wada811.ghblog.model.domain.*
import retrofit.Response
import rx.Observable
import java.util.*

class CloudGitHubDataStore(var user: User) {
    fun getAllRepository(): Observable<List<Repository>> {
        return Observable.defer {
            getNextPageRepository(GitHubApi(user).getRepositoryList())
                    .map {
                        it.map {
                            RepositoryEntityDataMapper.transform(it)
                        }
                    }
        }
    }

    private fun getNextPageRepository(observable: Observable<Response<List<RepositoryEntity>>>): Observable<List<RepositoryEntity>> {
        return observable.flatMap { response: Response<List<RepositoryEntity>> ->
            val apiInfo = ApiInfoParser.parseResponseHeader(response.headers())
            val nextPageUrl = apiInfo.getNextPageUrl()
            if (nextPageUrl == null) {
                Observable.just(response.body())
            } else {
                Observable.just(response.body())
                        .zipWith(getNextPageRepository(GitHubApi(user).getRepositoryList(nextPageUrl)), {
                            list1, list2 ->
                            val list = list1 as ArrayList<RepositoryEntity>
                            list.addAll(list2)
                            list
                        })
            }
        }
    }

    fun getContents(repository: Repository, path: String): Observable<List<RepositoryContentInfo>> {
        return Observable.defer {
            GitHubApi(user).getContents(repository.owner.login, repository.name, path)
                    .map {
                        it.body().map {
                            RepositoryContentInfoEntityDataMapper.transform(it)
                        }
                    }
        }
    }

    fun getContent(repository: Repository, path: String): Observable<RepositoryContent> {
        return Observable.defer {
            GitHubApi(user).getContent(repository.owner.login, repository.name, path)
                    .map {
                        RepositoryContentEntityDataMapper.transform(it.body())
                    }
        }
    }

    fun createContent(repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            val request = CreateContentRequest(repository.owner.login, repository.name, commit.path,
                    CreateContentRequest.CreateContentCommitRequest(commit.path, commit.message, commit.encodedContent())
            )
            GitHubApi(user).createContent(request)
                    .map {
                        val response = it.body()
                        GitHubCommit(
                                RepositoryContentInfo(
                                        response.content.name,
                                        response.content.path,
                                        response.content.sha,
                                        response.content.size,
                                        response.content.url,
                                        response.content.html_url,
                                        response.content.git_url,
                                        response.content.download_url,
                                        response.content.type,
                                        RepositoryContentInfo.ContentLink(
                                                response.content._links.self,
                                                response.content._links.git,
                                                response.content._links.html
                                        )
                                ),
                                GitHubCommit.Commit(
                                        response.commit.sha,
                                        response.commit.url,
                                        response.commit.html_url,
                                        GitHubCommit.Commit.Author(
                                                response.commit.author.date,
                                                response.commit.author.name,
                                                response.commit.author.email
                                        ),
                                        GitHubCommit.Commit.Author(
                                                response.commit.committer.date,
                                                response.commit.committer.name,
                                                response.commit.committer.email
                                        ),
                                        response.commit.message,
                                        GitHubTree(
                                                response.commit.tree.sha,
                                                response.commit.tree.url
                                        ),
                                        response.commit.parents.map {
                                            GitHubReference(
                                                    it.sha,
                                                    it.url,
                                                    it.html_url
                                            )
                                        }
                                )
                        )
                    }
        }
    }

    fun updateContent(repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            val request = UpdateContentRequest(repository.owner.login, repository.name, commit.path,
                    UpdateContentRequest.UpdateContentCommitRequest(commit.path, commit.message, commit.encodedContent(), commit.sha!!)
            )
            GitHubApi(user).updateContent(request)
                    .map {
                        val response = it.body()
                        GitHubCommit(
                                RepositoryContentInfo(
                                        response.content.name,
                                        response.content.path,
                                        response.content.sha,
                                        response.content.size,
                                        response.content.url,
                                        response.content.html_url,
                                        response.content.git_url,
                                        response.content.download_url,
                                        response.content.type,
                                        RepositoryContentInfo.ContentLink(
                                                response.content._links.self,
                                                response.content._links.git,
                                                response.content._links.html
                                        )
                                ),
                                GitHubCommit.Commit(
                                        response.commit.sha,
                                        response.commit.url,
                                        response.commit.html_url,
                                        GitHubCommit.Commit.Author(
                                                response.commit.author.date,
                                                response.commit.author.name,
                                                response.commit.author.email
                                        ),
                                        GitHubCommit.Commit.Author(
                                                response.commit.committer.date,
                                                response.commit.committer.name,
                                                response.commit.committer.email
                                        ),
                                        response.commit.message,
                                        GitHubTree(
                                                response.commit.tree.sha,
                                                response.commit.tree.url
                                        ),
                                        response.commit.parents.map {
                                            GitHubReference(
                                                    it.sha,
                                                    it.url,
                                                    it.html_url
                                            )
                                        }
                                )
                        )
                    }
        }
    }

    fun getTree(repository: Repository): Observable<GitHubTree> {
        return Observable.defer {
//            GitHubApi(user).getReference(repository.owner.login, repository.name, "heads/" + repository.defaultBranch)
//                    .map { ReferenceEntityDataMapper.transform(it) }
//                    .flatMap { reference ->
//                        GitHubApi(user).getGitTree(repository.owner.login, repository.name, reference.referenceObject.sha)
//                                .map { gitTreeEntity: GitTreeEntity ->
//                                    GitTreeEntityDataMapper.transform(gitTreeEntity)
//                                }
//                    }
            GitHubApi(user).getGitTree(repository.owner.login, repository.name, "heads/" + repository.defaultBranch)
                    .map {
                        GitTreeEntityDataMapper.transform(it.body())
                    }
        }

    }
}
