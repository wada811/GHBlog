package com.wada811.ghblog.data.entity.mapper.response

import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentsResponse
import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentsResponse.ContentLinkResponse
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.RepositoryContentInfo
import com.wada811.ghblog.domain.model.RepositoryContentInfo.ContentLink
import com.wada811.ghblog.domain.model.User

object GetContentsResponseDataMapper {
    fun transform(user: User, repository: Repository, response: GetContentsResponse) = RepositoryContentInfo(
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
        ContentLinkResponseDataMapper.transform(response._links)
    )

    object ContentLinkResponseDataMapper {
        fun transform(response: ContentLinkResponse) = ContentLink(
            response.self,
            response.git,
            response.html
        )
    }
}