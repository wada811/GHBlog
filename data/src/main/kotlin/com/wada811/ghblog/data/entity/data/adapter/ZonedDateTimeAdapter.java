package com.wada811.ghblog.data.entity.data.adapter;

import com.github.gfx.android.orma.annotation.StaticTypeAdapter;
import org.jetbrains.annotations.NotNull;
import org.threeten.bp.ZonedDateTime;

//@StaticTypeAdapter(targetType = ZonedDateTime.class, serializedType = String.class, serializer = "serialize", deserializer = "deserialize")
@StaticTypeAdapter(targetType = ZonedDateTime.class, serializedType = String.class)
public class ZonedDateTimeAdapter {

    @NotNull
    public static String serialize(@NotNull ZonedDateTime source){
        return source.toString();
    }

    @NotNull
    public static ZonedDateTime deserialize(@NotNull String serialized){
        return ZonedDateTime.parse(serialized);
    }
}
