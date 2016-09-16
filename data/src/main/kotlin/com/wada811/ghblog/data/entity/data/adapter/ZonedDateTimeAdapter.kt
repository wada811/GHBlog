package com.wada811.ghblog.data.entity.data.adapter

import com.github.gfx.android.orma.annotation.StaticTypeAdapter
import org.threeten.bp.ZonedDateTime

@StaticTypeAdapter(targetType = ZonedDateTime::class, serializedType = String::class)
object ZonedDateTimeAdapter {
    @JvmStatic
    fun serialize(source: ZonedDateTime): String? = source.toString()

    @JvmStatic
    fun deserialize(serialized: String?): ZonedDateTime = ZonedDateTime.parse(serialized)
}
