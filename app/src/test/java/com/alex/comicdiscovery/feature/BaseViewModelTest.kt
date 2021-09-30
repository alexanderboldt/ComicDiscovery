package com.alex.comicdiscovery.feature

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    // ----------------------------------------------------------------------------

    @Before
    fun beforeInternal() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun afterInternal() {
        Dispatchers.resetMain()
    }
}