package com.alex.comicdiscovery.repository.database.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey
    val id: Long,
    val name: String
)