package com.alex.database.character

import androidx.room.*

@Dao
interface CharacterDao {

    // create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: DbModelCharacter): Long

    // ----------------------------------------------------------------------------

    // read

    @Query("select * from DbModelCharacter where id = :id")
    suspend fun getCharacter(id: Int): DbModelCharacter?

    // ----------------------------------------------------------------------------

    // delete

    @Query("delete from DbModelCharacter where id = :id")
    fun delete(id: Int): Int
}