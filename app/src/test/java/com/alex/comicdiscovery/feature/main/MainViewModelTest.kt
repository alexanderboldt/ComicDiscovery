package com.alex.comicdiscovery.feature.main

import com.alex.comicdiscovery.feature.main.model.UiModelThemes
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MainViewModel

    // mocking dependencies
    @Mock
    private lateinit var settingsRepository: SettingsRepository

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)

        viewModel = MainViewModel(settingsRepository)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `it should be successful with default-theme`() {
        runBlockingTest {
            // verify
            assertEquals(UiModelThemes.SYSTEM, viewModel.theme)
        }
    }
}