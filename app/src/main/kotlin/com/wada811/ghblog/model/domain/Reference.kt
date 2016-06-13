package com.wada811.ghblog.model.domain

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