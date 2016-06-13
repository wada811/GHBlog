package com.wada811.ghblog.data.entity

data class ReferenceEntity(
        var ref: String,
        var url: String,
        var `object`: ReferenceObjectEntity
) {

    data class ReferenceObjectEntity(
            var type: String,
            var sha: String,
            var url: String
    )
}