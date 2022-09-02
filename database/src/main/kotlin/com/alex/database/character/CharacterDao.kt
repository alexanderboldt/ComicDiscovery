package com.alex.database.character

import androidx.room.*

@Dao
interface CharacterDao {

    // region create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: DbModelCharacter): Long

    // endregion

    // ----------------------------------------------------------------------------

    // region read

    @Query("select * from DbModelCharacter where id = :id")
    suspend fun get(id: Int): DbModelCharacter?

    // endregion

    // ----------------------------------------------------------------------------

    // region delete

    @Query("delete from DbModelCharacter where id = :id")
    suspend fun delete(id: Int): Int

    // endregion
}