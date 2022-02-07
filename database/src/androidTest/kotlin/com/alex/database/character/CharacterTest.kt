package com.alex.database.character

import android.content.Context
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alex.database.ComicDiscoveryDatabaseInternal
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CharacterTest {

    val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)

    @Before
    fun beforeInternal() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun afterInternal() {
        Dispatchers.resetMain()
    }

    @Test
    fun test() {
        /*
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room
            .inMemoryDatabaseBuilder(context, ComicDiscoveryDatabaseInternal::class.java)
            //.setTransactionExecutor(testDispatcher.asExecutor())
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            //.setQueryExecutor(testDispatcher.asExecutor())
            .build()


        */
        testScope.runTest {
            //val character = database.characterDao().getCharacter(0)
        }

        Truth.assertThat(true).isTrue()
    }
}