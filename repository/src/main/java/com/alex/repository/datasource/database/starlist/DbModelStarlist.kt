package com.alex.repository.datasource.database.starlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbModelStarlist(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)