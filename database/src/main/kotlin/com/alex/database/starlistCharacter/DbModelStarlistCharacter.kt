package com.alex.database.starlistCharacter

import androidx.room.Entity
import androidx.room.ForeignKey
import com.alex.database.character.DbModelCharacter
import com.alex.database.starlist.DbModelStarlist

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