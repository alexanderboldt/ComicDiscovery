package com.alex.repository.datasource.database.converter

import androidx.room.TypeConverter

class ListTypeConverter {

    @TypeConverter
    fun fromStringToList(value: String) = value.split(";")

    @TypeConverter
    fun fromListToString(list: List<String>) = list.joinToString(";")
}