package com.alex.repository.datasource.database.character

import androidx.room.*

@Dao
interface CharacterDao {

    // create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: DbModelCharacter): Long

    // read

    @Query("SELECT * FROM DbModelCharacter WHERE id = :id")
    suspend fun getCharacter(id: Int): DbModelCharacter?

    // delete

    @Query("DELETE FROM DbModelCharacter WHERE id = :id")
    fun delete(id: Int): Int
}