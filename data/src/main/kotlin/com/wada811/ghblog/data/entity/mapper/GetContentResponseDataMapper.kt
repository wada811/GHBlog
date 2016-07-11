package com.wada811.ghblog.data.entity.mapper

import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentResponse
import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentResponse.ContentLinkResponse
import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.ghblog.domain.model.RepositoryContentInfo

object GetContentResponseDataMapper {
    fun transform(response: GetContentResponse) = RepositoryContent(
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