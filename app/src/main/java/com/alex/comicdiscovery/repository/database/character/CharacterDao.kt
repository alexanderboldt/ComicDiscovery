package com.alex.comicdiscovery.repository.database.character

import androidx.room.*

@Dao
interface CharacterDao {

    @Query("select * from DbModelCharacter")
    suspend fun getAll(): List<DbModelCharacter>

    @Query("SELECT * FROM DbModelCharacter WHERE id = :id")
    suspend fun getCharacter(id: Int): DbModelCharacter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: DbModelCharacter): Long

    @Query("DELETE FROM DbModelCharacter WHERE id = :id")
    fun delete(id: Int): Int
}