package com.wada811.ghblog.data.datasource

import com.wada811.ghblog.data.entity.mapper.*
import com.wada811.ghblog.data.entity.request.github.git.trees.CreateTreeRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.CreateTreeRequest.CreateTreeBodyRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.CreateTreeRequest.CreateTreeBodyRequest.CreateTreeTreeRequest
import com.wada811.ghblog.data.entity.request.github.git.trees.GetTreeRequest
import com.wada811.ghblog.data.entity.request.github.repos.contents.*
import com.wada811.ghblog.data.entity.response.github.repos.RepositoryResponse
import com.wada811.ghblog.data.http.ApiInfoParser
import com.wada811.ghblog.data.http.GitHubApi
import com.wada811.ghblog.domain.model.*
import retrofit2.Response
import rx.Observable
import java.util.*

class CloudGitHubDataStore(var user: User) {
    fun getAllRepository(): Observable<List<Repository>> {
        return Observable.defer {
            getAllRepository(GitHubApi(user).getRepositoryList())
                .map { it.map { RepositoryResponseDataMapper.transform(it) } }
        }
    }

    private fun getAllRepository(observable: Observable<Response<List<RepositoryResponse>>>): Observable<List<RepositoryResponse>> {
        return observable.flatMap { response: Response<List<RepositoryResponse>> ->
            val apiInfo = ApiInfoParser.parseResponseHeader(response.headers())
            val nextPageUrl = apiInfo.getNextPageUrl()
            if (nextPageUrl == null) {
                Observable.just(response.body())
            } else {
                Observable.just(response.body())
                    .zipWith(getAllRepository(GitHubApi(user).getRepositoryList(nextPageUrl)), {
                        list1, list2 ->
                        val list = list1 as ArrayList<RepositoryResponse>
                        list.addAll(list2)
                        list
                    })
            }
        }
    }

    fun getContents(repository: Repository, path: String): Observable<List<RepositoryContentInfo>> {
        return Observable.defer {
            val request = GetContentsRequest(repository.owner.login, repository.name, path)
            GitHubApi(user).getContents(request).map { it.body().map { GetContentsResponseDataMapper.transform(it) } }
        }
    }

    fun getContent(repository: Repository, path: String): Observable<RepositoryContent> {
        return Observable.defer {
            val request = GetContentRequest(repository.owner.login, repository.name, path)
            GitHubApi(user).getContent(request).map { GetContentResponseDataMapper.transform(it.body()) }
        }
    }

    fun createContent(repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            val request = CreateContentRequest(repository.owner.login, repository.name, commit.path,
                CreateContentRequest.CreateContentCommitRequest(commit.path, commit.message, commit.encodedContent())
            )
            GitHubApi(user).createContent(request).map { CreateContentResponseDataMapper.transform(it.body()) }
        }
    }

    fun updateContent(repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            val request = UpdateContentRequest(repository.owner.login, repository.name, commit.path,
                UpdateContentRequest.UpdateContentCommitRequest(commit.path, commit.message, commit.encodedContent(), commit.sha!!)
            )
            GitHubApi(user).updateContent(request).map { UpdateContentResponseDataMapper.transform(it.body()) }
        }
    }

    fun deleteContent(repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return Observable.defer {
            val request = DeleteContentRequest(repository.owner.login, repository.name, commit.path,
                DeleteContentRequest.DeleteContentCommitRequest(commit.path, commit.message, commit.sha!!)
            )
            GitHubApi(user).deleteContent(request).map { DeleteContentResponseDataMapper.transform(it.body()) }
        }
    }

    fun renameContent(repository: Repository, commit: GitRenameCommit): Observable<GitHubCommit> {
        return deleteContent(repository, GitCommit(commit.oldPath, commit.message, commit.content, commit.sha))
            .zipWith(createContent(repository, GitCommit(commit.path, commit.message, commit.content)), {
                deleteCommit, createCommit -> createCommit
            })

    }

    fun getTree(repository: Repository): Observable<GitHubTree> {
        return Observable.defer {
            val request = GetTreeRequest(repository.owner.login, repository.name, "heads/" + repository.defaultBranch)
            GitHubApi(user).getTree(request).map { GetTreeResponseDataMapper.transform(it.body()) }
        }
    }

    fun createGitTree(repository: Repository, tree: GitTree): Observable<GitHubTree> {
        return Observable.defer {
            val request = CreateTreeRequest(repository.owner.login, repository.name,
                CreateTreeBodyRequest(
                    listOf(
                        CreateTreeTreeRequest(
                            tree.path,
                            tree.mode,
                            tree.type,
                            tree.sha,
                            tree.content
                        )
                    ),
                    tree.baseTree
                )
            )
            GitHubApi(user).createGitTree(request)
                .map {
                    val response = it.body()
                    GitHubTree(
                        response.sha,
                        response.url,
                        response.tree.map {
                            GitHubTree.Node(
                                it.path,
                                it.mode,
                                it.type,
                                it.size,
                                it.sha,
                                it.url
                            )
                        }
                    )
                }
        }
    }
}
