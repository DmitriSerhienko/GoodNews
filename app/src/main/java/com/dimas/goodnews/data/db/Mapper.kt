package com.dimas.goodnews.data.db

import androidx.room.TypeConverter
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.data.network.models.Source

class Mapper {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}