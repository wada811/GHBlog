package com.wada811.ghblog.domain.util

fun String.decodeBase64(flags: Int = Base64.NO_WRAP) = String(Base64.decode(this, flags))
