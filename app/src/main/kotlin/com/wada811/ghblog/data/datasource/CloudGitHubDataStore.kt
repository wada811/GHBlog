package com.wada811.ghblog.data.datasource

import com.wada811.ghblog.data.entity.RepositoryEntity
import com.wada811.ghblog.data.entity.RepositoryEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.ContentEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.GitTreeEntityDataMapper
import com.wada811.ghblog.data.http.ApiInfoParser
import com.wada811.ghblog.data.http.GitHubApi
import com.wada811.ghblog.model.domain.Content
import com.wada811.ghblog.model.domain.GitTree
import com.wada811.ghblog.model.domain.Repository
import com.wada811.ghblog.model.domain.User
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

    fun getContents(repository: Repository, path: String): Observable<List<Content>> {
        return Observable.defer {
            GitHubApi(user).getContents(repository.owner.login, repository.name, path)
                    .map {
                        it.body().map {
                            ContentEntityDataMapper.transform(it)
                        }
                    }
        }
    }

    fun getTree(repository: Repository): Observable<GitTree> {
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
