package com.alex.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.alex.database.ComicDiscoveryDatabaseInternal.Companion.database
import com.alex.database.character.DbModelCharacter
import com.alex.database.starlist.DbModelStarlist
import org.junit.After
import org.junit.Before
import java.util.concurrent.Executors

open class BaseDaoTest {

    protected val characterSeeds: List<DbModelCharacter> by lazy { getCharacterSeeds(100) }
    protected val starlistSeeds: List<DbModelStarlist> by lazy { getStarlistSeeds(100) }

    // ----------------------------------------------------------------------------

    @Before
    fun beforeInternal() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ComicDiscoveryDatabaseInternal::class.java
            ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    @After
    fun afterInternal() {
        database.close()
    }

    // ----------------------------------------------------------------------------

    private fun getCharacterSeeds(count: Int) = (1..count).map { index ->
        DbModelCharacter(
            index,
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
    }

    private fun getStarlistSeeds(count: Int) = (1..count).map { index ->
        DbModelStarlist(
            index.toLong(),
            "Starlist $index"
        )
    }
}