package com.wada811.ghblog.data.http.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.ZonedDateTime

class ZonedDateTimeAdapter : JsonAdapter<ZonedDateTime>() {
    override fun fromJson(reader: JsonReader?): ZonedDateTime? = ZonedDateTime.parse(reader?.nextString())

    override fun toJson(writer: JsonWriter?, value: ZonedDateTime?) {
        writer?.value(value.toString())
    }
}