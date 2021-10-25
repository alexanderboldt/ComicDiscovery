package com.alex.comicdiscovery.feature.main

import com.alex.comicdiscovery.feature.BaseViewModelTest
import com.alex.comicdiscovery.feature.main.model.UiModelTheme
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: MainViewModel

    // mocking dependencies
    @Mock private lateinit var settingsRepository: SettingsRepository

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        viewModel = MainViewModel(settingsRepository)
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `it should be successful with default-theme`() {
        runBlockingTest {
            // verify
            assertEquals(UiModelTheme.SYSTEM, viewModel.theme)
        }
    }
}