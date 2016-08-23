package com.wada811.ghblog.data.entity.mapper.response

import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentResponse
import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentResponse.ContentLinkResponse
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.ghblog.domain.model.RepositoryContentInfo
import com.wada811.ghblog.domain.model.User

object GetContentResponseDataMapper {
    fun transform(user: User, repository: Repository, response: GetContentResponse) = RepositoryContent(
        user,
        repository,
        response.name,
        response.path,
        response.sha,
        response.size,
        response.url,
        response.html_url,
        response.git_url,
        response.download_url,
        response.type,
        ContentLinkResponseDataMapper.transform(response._links),
        response.encoding,
        response.content
    )

    object ContentLinkResponseDataMapper {
        fun transform(response: ContentLinkResponse) = RepositoryContentInfo.ContentLink(
            response.self,
            response.git,
            response.html
        )
    }
}