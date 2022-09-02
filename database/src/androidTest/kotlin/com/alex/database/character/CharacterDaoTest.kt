package com.alex.database.character

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alex.database.BaseDaoTest
import com.alex.database.ComicDiscoveryDatabaseInternal.Companion.database
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest : BaseDaoTest() {

    // insert

    @Test
    fun itShouldInsertCharacters() = runBlocking {
        // insert the characters and verify
        characterSeeds.forEach { characterSeed ->
            val insertedId = database.characterDao().insert(characterSeed)
            Truth.assertThat(insertedId).isEqualTo(characterSeed.id)
        }
    }

    // ----------------------------------------------------------------------------
    // get

    @Test
    fun itShouldReadCharacters() = runBlocking {
        // precondition: insert the characters
        characterSeeds.forEach { database.characterDao().insert(it) }

        // get the character and verify
        characterSeeds.forEach { characterSeed ->
            val dbModelCharacter = database.characterDao().get(characterSeed.id)
            Truth.assertThat(dbModelCharacter).isEqualTo(characterSeed)
        }
    }

    // ----------------------------------------------------------------------------
    // delete

    @Test
    fun itShouldDeleteCharacters() = runBlocking {
        // precondition: insert the characters
        characterSeeds.forEach { database.characterDao().insert(it) }

        // delete the characters and verify
        characterSeeds.forEach { characterSeed ->
            val numberOfDeletedRows = database.characterDao().delete(characterSeed.id)
            Truth.assertThat(numberOfDeletedRows).isEqualTo(1)
        }
    }
}