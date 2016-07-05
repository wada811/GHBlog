package com.wada811.ghblog.domain.model

class Reference(
        var ref: String,
        var url: String,
        var referenceObject: ReferenceObject
) {
    class ReferenceObject(
            var type: String,
            var sha: String,
            var url: String
    )
}