package com.alex.database.converter

import androidx.room.TypeConverter

internal class ListTypeConverter {

    @TypeConverter
    fun fromStringToList(value: String) = value.split(";")

    @TypeConverter
    fun fromListToString(list: List<String>) = list.joinToString(";")
}