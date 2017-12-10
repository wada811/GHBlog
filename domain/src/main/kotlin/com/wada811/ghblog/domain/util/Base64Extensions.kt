package com.wada811.ghblog.domain.util

fun String.encodeBase64(): String = Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
fun String.decodeBase64(flags: Int = Base64.NO_WRAP) = String(Base64.decode(this, flags))
