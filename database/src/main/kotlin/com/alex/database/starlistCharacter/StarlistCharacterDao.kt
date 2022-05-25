package com.alex.database.starlistCharacter

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StarlistCharacterDao {

    // create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(starlistCharacter: DbModelStarlistCharacter): Long

    // ----------------------------------------------------------------------------

    // read

    @Query("select count(*) from DbModelStarlistCharacter where characterId = :characterId")
    suspend fun getNumberOfAssociations(characterId: Int): Int

    @Query("select starlistId from DbModelStarlistCharacter  where characterId = :characterId")
    suspend fun getAssociatedStarlists(characterId: Int): List<Long>

    // ----------------------------------------------------------------------------

    // delete

    @Query("delete from DbModelStarlistCharacter where starlistId = :starlistId and characterId = :characterId")
    fun delete(starlistId: Long, characterId: Int): Int
}