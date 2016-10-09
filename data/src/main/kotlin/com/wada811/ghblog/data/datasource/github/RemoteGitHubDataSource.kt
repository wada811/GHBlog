package com.wada811.ghblog.data.datasource.github

import com.wada811.ghblog.data.entity.mapper.response.*
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

class RemoteGitHubDataSource : GitHubDataSource {
    override fun getRepositories(user: User): Observable<Repository> {
        return getRepositories(user, GitHubApi(user.accessToken).getRepositories())
            .map { RepositoryResponseDataMapper.transform(user, it) }
    }

    private fun getRepositories(user: User, observable: Observable<Response<List<RepositoryResponse>>>): Observable<RepositoryResponse> {
        return observable.flatMap { response: Response<List<RepositoryResponse>> ->
            val apiInfo = ApiInfoParser.parseResponseHeader(response.headers())
            val nextPageUrl = apiInfo.getNextPageUrl()
            if (nextPageUrl == null) {
                Observable.from(response.body())
            } else {
                Observable.from(response.body())
                    .mergeWith(getRepositories(user, GitHubApi(user.accessToken).getRepositories(nextPageUrl)))
            }
        }
    }

    override fun saveRepository(repository: Repository): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveRepositories(repositories: MutableList<Repository>): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBlogs(user: User): Observable<Blog> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBlog(blog: Blog): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContentInfo>> {
        val request = GetContentsRequest(repository.owner.login, repository.name, path)
        return GitHubApi(user.accessToken).getContents(request)
            .map { it.body().map { GetContentsResponseDataMapper.transform(user, repository, it) } }
    }

    fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent> {
        val request = GetContentRequest(repository.owner.login, repository.name, path)
        return GitHubApi(user.accessToken).getContent(request)
            .map { GetContentResponseDataMapper.transform(user, repository, it.body()) }
    }

    fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        val request = CreateContentRequest(repository.owner.login, repository.name, commit.path,
            CreateContentRequest.CreateContentCommitRequest(commit.path, commit.message, commit.encodedContent())
        )
        return GitHubApi(user.accessToken).createContent(request)
            .map { CreateContentResponseDataMapper.transform(user, repository, it.body()) }
    }

    fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        val request = UpdateContentRequest(repository.owner.login, repository.name, commit.path,
            UpdateContentRequest.UpdateContentCommitRequest(commit.path, commit.message, commit.encodedContent(), commit.sha!!)
        )
        return GitHubApi(user.accessToken).updateContent(request)
            .map { UpdateContentResponseDataMapper.transform(user, repository, it.body()) }
    }

    fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        val request = DeleteContentRequest(repository.owner.login, repository.name, commit.path,
            DeleteContentRequest.DeleteContentCommitRequest(commit.path, commit.message, commit.sha!!)
        )
        return GitHubApi(user.accessToken).deleteContent(request).map { DeleteContentResponseDataMapper.transform(it.body()) }
    }

    fun renameContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return deleteContent(user, repository, GitCommit(commit.oldPath!!, commit.message, commit.content, commit.sha))
            .zipWith(createContent(user, repository, GitCommit(commit.path, commit.message, commit.content)), {
                deleteCommit, createCommit ->
                createCommit
            })

    }

    fun getTree(user: User, repository: Repository): Observable<GitHubTree> {
        val request = GetTreeRequest(repository.owner.login, repository.name, "heads/" + repository.defaultBranch)
        return GitHubApi(user.accessToken).getTree(request).map { GetTreeResponseDataMapper.transform(it.body()) }
    }

    fun createGitTree(user: User, repository: Repository, tree: GitTree): Observable<GitHubTree> {
        val request = CreateTreeRequest(repository.owner.login, repository.name,
            CreateTreeBodyRequest(
                tree.nodes.map { CreateTreeTreeRequest(it.path, it.mode, it.type, it.sha, it.content) },
                tree.baseTree
            )
        )
        return GitHubApi(user.accessToken).createGitTree(request).map { CreateTreeResponseDataMapper.transform(it.body()) }
    }
}
