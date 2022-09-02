package com.alex.database.starlist

import androidx.room.*
import com.alex.database.character.DbModelCharacter

@Dao
interface StarlistDao {

    // region create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(starlist: DbModelStarlist): Long

    // endregion

    // ----------------------------------------------------------------------------

    // region read

    @Query("select * from DbModelStarlist")
    suspend fun getAll(): List<DbModelStarlist>

    // endregion

    // ----------------------------------------------------------------------------

    // region update

    @Update
    suspend fun update(starlist: DbModelStarlist): Int

    // endregion

    // ----------------------------------------------------------------------------

    // region delete

    @Query("delete from DbModelStarlist where id = :id")
    suspend fun delete(id: Long): Int

    // endregion
}