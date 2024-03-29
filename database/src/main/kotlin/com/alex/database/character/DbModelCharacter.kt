package com.alex.database.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbModelCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val summary: String,
    val realName: String?,
    val smallImageUrl: String,
    val gender: Int,
    val aliases: String?,
    val birth: String?,
    val powers: List<String>,
    val origin: String,
    val teams: List<String>,
    val friends: List<String>,
    val enemies: List<String>
)