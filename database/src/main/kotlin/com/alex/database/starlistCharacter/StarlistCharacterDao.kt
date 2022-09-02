package com.alex.database.starlistCharacter

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alex.database.character.DbModelCharacter

@Dao
interface StarlistCharacterDao {

    // region create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(starlistCharacter: DbModelStarlistCharacter): Long

    // endregion

    // ----------------------------------------------------------------------------

    // region read

    @Query("select count(*) from DbModelStarlistCharacter where characterId = :characterId")
    suspend fun getNumberOfAssociations(characterId: Int): Int

    @Query("select starlistId from DbModelStarlistCharacter  where characterId = :characterId")
    suspend fun getAssociatedStarlistIds(characterId: Int): List<Long>

    @Query("""
        select DbModelCharacter.* from DbModelStarlist
        left join DbModelStarlistCharacter on DbModelStarlist.id = DbModelStarlistCharacter.starlistId
        left join DbModelCharacter on DbModelStarlistCharacter.characterId = DbModelCharacter.id
        where DbModelStarlist.id = :starlistId
        """)
    suspend fun getCharacters(starlistId: Long): List<DbModelCharacter>

    // endregion

    // ----------------------------------------------------------------------------

    // region delete

    @Query("delete from DbModelStarlistCharacter where starlistId = :starlistId and characterId = :characterId")
    fun delete(starlistId: Long, characterId: Int): Int

    // endregion
}