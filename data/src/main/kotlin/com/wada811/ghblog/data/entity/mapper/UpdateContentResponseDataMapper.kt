package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.response.github.repos.contents.UpdateContentResponse
import com.wada811.ghblog.domain.model.GitHubCommit
import com.wada811.ghblog.domain.model.GitHubReference
import com.wada811.ghblog.domain.model.GitHubTree
import com.wada811.ghblog.domain.model.RepositoryContentInfo

object UpdateContentResponseDataMapper {
    fun transform(response: UpdateContentResponse) = GitHubCommit(
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