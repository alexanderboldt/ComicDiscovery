package com.alex.comicdiscovery.repository.datasource.database.converter

import androidx.room.TypeConverter

class ListTypeConverter {

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return value.split(";")
    }

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(";")
    }
}