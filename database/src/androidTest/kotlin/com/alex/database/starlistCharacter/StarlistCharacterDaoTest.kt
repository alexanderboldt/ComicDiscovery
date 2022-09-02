package com.alex.database.starlistCharacter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alex.database.BaseDaoTest
import com.alex.database.ComicDiscoveryDatabaseInternal.Companion.database
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StarlistCharacterDaoTest : BaseDaoTest() {

    @Before
    fun before() = runBlocking {
        starlistSeeds.forEach { database.starlistDao().insert(it) }
        characterSeeds.forEach { database.characterDao().insert(it) }
    }

    // ----------------------------------------------------------------------------
    // insert

    @Test
    fun itShouldInsertStarlistCharacters() = runBlocking {
        // link first starlist with first character, second starlist with second character and so on
        starlistSeeds.zip(characterSeeds).forEachIndexed { index, (starlistSeed, characterSeed) ->
            val insertedId = database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
            Truth.assertThat(insertedId).isEqualTo(index.inc())
        }
    }

    // ----------------------------------------------------------------------------
    // getNumberOfAssociations

    @Test
    fun itShouldRead0Associations() = runBlocking {
        characterSeeds.forEach { characterSeed ->
            val numberOfAssociations = database.starlistCharacterDao().getNumberOfAssociations(characterSeed.id)
            Truth.assertThat(numberOfAssociations).isEqualTo(0)
        }
    }

    @Test
    fun itShouldRead1AssociationForEveryCharacter() = runBlocking {
        // link first starlist with first character, second starlist with second character and so on
        starlistSeeds.zip(characterSeeds).forEach { (starlistSeed, characterSeed) ->
            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
        }

        // verify
        characterSeeds.forEach { characterSeed ->
            val numberOfAssociations = database.starlistCharacterDao().getNumberOfAssociations(characterSeed.id)
            Truth.assertThat(numberOfAssociations).isEqualTo(1)
        }
    }

    @Test
    fun itShouldRead100Associations() = runBlocking {
        // link all starlists only with the first character
        val characterSeed = characterSeeds.first()
        starlistSeeds.forEach { starlistSeed ->
            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
        }

        // verify that the character is linked to all starlists
        val numberOfAssociations = database.starlistCharacterDao().getNumberOfAssociations(characterSeed.id)
        Truth.assertThat(numberOfAssociations).isEqualTo(starlistSeeds.size)
    }

    // ----------------------------------------------------------------------------
    // getAssociatedStarlistIds

    @Test
    fun itShouldRead0AssociatedStarlists() = runBlocking {
        characterSeeds.forEach { characterSeed ->
            val numberOfAssociations = database.starlistCharacterDao().getAssociatedStarlistIds(characterSeed.id)
            Truth.assertThat(numberOfAssociations).isEmpty()
        }
    }

    @Test
    fun itShouldRead1AssociatedStarlistForEveryCharacter() = runBlocking {
        // link first starlist with first character, second starlist with second character and so on
        starlistSeeds.zip(characterSeeds).forEach { (starlistSeed, characterSeed) ->
            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
        }

        // verify
        characterSeeds.forEach { characterSeed ->
            val starlists = database.starlistCharacterDao().getAssociatedStarlistIds(characterSeed.id)
            Truth.assertThat(starlists).hasSize(1)
        }
    }

    @Test
    fun itShouldRead100AssociatedStarlists() = runBlocking {
        // link all starlists only with the first character
        val characterSeed = characterSeeds.first()
        starlistSeeds.forEach { starlistSeed ->
            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
        }

        // verify that the character is linked to all starlists
        val starlists = database.starlistCharacterDao().getAssociatedStarlistIds(characterSeed.id)
        Truth.assertThat(starlists.size).isEqualTo(starlistSeeds.size)
    }

    // ----------------------------------------------------------------------------
    // getCharacters

    @Test
    fun isShouldRead100CharactersFromStarlist() = runBlocking {
        val starlistSeed = starlistSeeds.first()
        characterSeeds.forEach { characterSeed ->
            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
        }

        val characters = database.starlistCharacterDao().getCharacters(starlistSeed.id)
        Truth.assertThat(characters).isNotEmpty()
        Truth.assertThat(characters).hasSize(characterSeeds.size)
    }

    // ----------------------------------------------------------------------------
    // delete

    @Test
    fun itShouldDeleteStarlistCharacters() = runBlocking {
        // link first starlist with first character, second starlist with second character and so on
        starlistSeeds.zip(characterSeeds).forEach { (starlistSeed, characterSeed) ->
            database.starlistCharacterDao().insert(DbModelStarlistCharacter(starlistSeed.id, characterSeed.id))
        }

        // delete and verify
        starlistSeeds.zip(characterSeeds).forEach { (starlistSeed, characterSeed) ->
            val numberOfDeletedRows = database.starlistCharacterDao().delete(starlistSeed.id, characterSeed.id)
            Truth.assertThat(numberOfDeletedRows).isEqualTo(1)
        }
    }
}