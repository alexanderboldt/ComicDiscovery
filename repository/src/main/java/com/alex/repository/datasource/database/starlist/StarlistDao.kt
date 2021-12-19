package com.alex.repository.datasource.database.starlist

import androidx.room.*
import com.alex.repository.datasource.database.character.DbModelCharacter

@Dao
interface StarlistDao {

    // create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(starlist: DbModelStarlist): Long

    // read

    @Query("select * from DbModelStarlist")
    suspend fun getAll(): List<DbModelStarlist>

    @Query("""
        select DbModelCharacter.* from DbModelStarlist
        left join DbModelStarlistCharacter on DbModelStarlist.id = DbModelStarlistCharacter.starlistId
        left join DbModelCharacter on DbModelStarlistCharacter.characterId = DbModelCharacter.id
        where DbModelStarlist.id = :starlistId
        """)
    suspend fun getCharacters(starlistId: Long): List<DbModelCharacter>

    // update

    @Update
    suspend fun update(starlist: DbModelStarlist): Int

    // delete

    @Query("DELETE FROM DbModelStarlist WHERE id = :id")
    fun delete(id: Long): Int
}