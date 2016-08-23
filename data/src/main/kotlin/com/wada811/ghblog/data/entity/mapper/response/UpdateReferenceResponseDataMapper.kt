package com.wada811.ghblog.data.entity.mapper.response

import com.wada811.ghblog.data.entity.response.github.git.refs.UpdateReferenceResponse
import com.wada811.ghblog.data.entity.response.github.git.refs.UpdateReferenceResponse.UpdateReferenceObjectResponse
import com.wada811.ghblog.domain.model.Reference
import com.wada811.ghblog.domain.model.Reference.ReferenceObject

object UpdateReferenceResponseDataMapper {
    fun transform(response: UpdateReferenceResponse) = Reference(
        response.ref,
        response.url,
        ReferenceObjectResponseDataMapper().transform(response.`object`)
    )

    class ReferenceObjectResponseDataMapper() {
        fun transform(response: UpdateReferenceObjectResponse) = ReferenceObject(
            response.type,
            response.sha,
            response.url
        )
    }
}