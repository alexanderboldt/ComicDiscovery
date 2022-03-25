package com.alex.database.converter

import androidx.room.TypeConverter

internal class ListTypeConverter {

    private val delimiter = ";"

    // ----------------------------------------------------------------------------

    @TypeConverter
    fun fromStringToList(value: String) = value.split(delimiter)

    @TypeConverter
    fun fromListToString(list: List<String>) = list.joinToString(delimiter)
}