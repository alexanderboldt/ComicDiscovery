package com.alex.comicdiscovery.repository.datasource.database.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbModelCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val realName: String?,
    val smallImageUrl: String,
    val gender: Int,
    val aliases: String?,
    val birth: String?,
    val powers: List<String>,
    val origin: String
)