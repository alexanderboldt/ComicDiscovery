package com.alex.comicdiscovery.repository.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("select * from character")
    suspend fun getAll(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbArticle: Character): Long
}