package com.alex.features.feature

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

open class BaseViewModelTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = UnconfinedTestDispatcher()

    // ----------------------------------------------------------------------------

    @ExperimentalCoroutinesApi
    @Before
    fun beforeInternal() {
        Dispatchers.setMain(dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun afterInternal() {
        Dispatchers.resetMain()
    }
}