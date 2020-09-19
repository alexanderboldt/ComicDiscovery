package com.alex.comicdiscovery.repository.database.character

import androidx.room.*

@Dao
interface CharacterDao {

    @Query("select * from character")
    suspend fun getAll(): List<Character>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacter(id: Int): Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character): Long

    @Query("DELETE FROM character WHERE id = :id")
    fun delete(id: Int): Int
}