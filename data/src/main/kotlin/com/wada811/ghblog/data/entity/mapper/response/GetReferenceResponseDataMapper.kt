package com.wada811.ghblog.data.entity.mapper.response

import com.wada811.ghblog.data.entity.response.github.git.refs.GetReferenceResponse
import com.wada811.ghblog.data.entity.response.github.git.refs.GetReferenceResponse.GetReferenceObjectResponse
import com.wada811.ghblog.domain.model.Reference
import com.wada811.ghblog.domain.model.Reference.ReferenceObject

object GetReferenceResponseDataMapper {
    fun transform(response: GetReferenceResponse) = Reference(
        response.ref,
        response.url,
        ReferenceObjectResponseDataMapper().transform(response.`object`)
    )

    class ReferenceObjectResponseDataMapper() {
        fun transform(response: GetReferenceObjectResponse) = ReferenceObject(
            response.type,
            response.sha,
            response.url
        )
    }
}