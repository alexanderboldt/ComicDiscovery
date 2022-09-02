package com.alex.database.starlist

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alex.database.BaseDaoTest
import com.alex.database.ComicDiscoveryDatabaseInternal.Companion.database
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StarlistDaoTest : BaseDaoTest() {

    // insert

    @Test
    fun itShouldInsertStarlists() = runBlocking {
        // insert the starlists and verify
        starlistSeeds.forEach { starlistSeed ->
            val insertedId = database.starlistDao().insert(starlistSeed)
            Truth.assertThat(insertedId).isEqualTo(starlistSeed.id)
        }
    }

    // ----------------------------------------------------------------------------
    // getAll

    @Test
    fun itShouldReadStarlists() = runBlocking {
        // precondition: insert the starlists
        starlistSeeds.forEach { database.starlistDao().insert(it) }

        // get the starlists and verify
        val dbModelStarlists = database.starlistDao().getAll()

        Truth.assertThat(starlistSeeds.size).isEqualTo(dbModelStarlists.size)

        starlistSeeds.forEachIndexed { index, starlistSeed ->
            Truth.assertThat(dbModelStarlists[index]).isEqualTo(starlistSeed)
        }
    }

    // ----------------------------------------------------------------------------
    // update

    @Test
    fun isShouldUpdateStarlists() = runBlocking {
        // precondition: insert the starlists
        starlistSeeds.forEach { database.starlistDao().insert(it) }

        // update the starlists and verify
        starlistSeeds.forEach { starlistSeed ->
            val numberOfUpdatedRows = database.starlistDao().update(starlistSeed)
            Truth.assertThat(numberOfUpdatedRows).isEqualTo(1)
        }
    }

    // ----------------------------------------------------------------------------
    // delete

    @Test
    fun itShouldDeleteStarlists() = runBlocking {
        // precondition: insert the starlists
        starlistSeeds.forEach { database.starlistDao().insert(it) }

        // delete the starlists and verify
        starlistSeeds.forEach { starlistSeed ->
            val numberOfDeletedRows = database.starlistDao().delete(starlistSeed.id)
            Truth.assertThat(numberOfDeletedRows).isEqualTo(1)
        }
    }
}