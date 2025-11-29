package dev.corazza.mytasks.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalTime

class LocalTImeAdapter : TypeAdapter<LocalTime>() {
    override fun write(writer: JsonWriter?, value: LocalTime?) {
        writer?.value(value?.toString())
    }

    override fun read(reader: JsonReader?): LocalTime? {
        return LocalTime.parse(reader?.nextString())
    }
}