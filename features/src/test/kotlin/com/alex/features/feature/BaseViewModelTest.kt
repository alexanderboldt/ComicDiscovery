package com.alex.features.feature

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    // ----------------------------------------------------------------------------

    @Before
    fun beforeInternal() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun afterInternal() {
        Dispatchers.resetMain()
    }
}