package com.alex.database.character

import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alex.database.ComicDiscoveryDatabaseInternal
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CharacterTest {

    private lateinit var database: ComicDiscoveryDatabaseInternal

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ComicDiscoveryDatabaseInternal::class.java
            ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    @After
    fun after() {
        database.close()
    }

    // ----------------------------------------------------------------------------

    @Test
    fun itShouldInsertACharacter() = runTest {
        // create the character
        val characterSeed = DbModelCharacter(
            2473282,
            "Batman",
            "summary",
            "Bruce Wayne",
            "http://url.com",
            1,
            "Batman",
            "01.01.940",
            listOf("Investigation"),
            "Earth",
            listOf("Justice League"),
            listOf("Superman", "The Flash"),
            listOf("Joker", "The Penguin")
        )

        // insert the character and verify
        val insertedId = database.characterDao().insert(characterSeed)

        Truth.assertThat(insertedId).isEqualTo(characterSeed.id)

        // get the character and verify
        val characterDb = database.characterDao().getCharacter(characterSeed.id)

        Truth.assertThat(characterDb).isEqualTo(characterSeed)

        // delete the character and verify
        val numberOfDeletedRows = database.characterDao().delete(characterSeed.id)

        Truth.assertThat(numberOfDeletedRows).isEqualTo(1)
    }
}