package com.alex.repository.datasource.database.starlistCharacter

import androidx.room.Entity
import androidx.room.ForeignKey
import com.alex.repository.datasource.database.character.DbModelCharacter
import com.alex.repository.datasource.database.starlist.DbModelStarlist

@Entity(
    primaryKeys = ["starlistId", "characterId"],
    foreignKeys = [
        ForeignKey(
            entity = DbModelStarlist::class,
            parentColumns = ["id"],
            childColumns = ["starlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DbModelCharacter::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )])
data class DbModelStarlistCharacter(
    val starlistId: Long,
    val characterId: Int
)