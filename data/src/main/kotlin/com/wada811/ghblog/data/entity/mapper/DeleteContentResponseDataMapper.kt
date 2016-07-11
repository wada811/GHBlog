package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.response.github.repos.contents.DeleteContentResponse
import com.wada811.ghblog.domain.model.GitHubCommit
import com.wada811.ghblog.domain.model.GitHubReference
import com.wada811.ghblog.domain.model.GitHubTree

object DeleteContentResponseDataMapper {
    fun transform(response: DeleteContentResponse) = GitHubCommit(
        null,
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