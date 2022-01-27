package com.alex.features.feature.settings

import com.alex.features.feature.BaseViewModelTest
import com.alex.features.feature.settings.model.State
import com.alex.repository.SettingsRepository
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: SettingsViewModel

    // mocking dependencies
    @Mock private lateinit var settingsRepository: SettingsRepository

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        viewModel = SettingsViewModel(settingsRepository)
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `it should be successful with default-theme`() {
        runBlockingTest {
            // verify
            assertEquals(State.UiModelTheme.SYSTEM, viewModel.state.theme)
        }
    }

    @Test
    fun `it should be successful with theme-change`() {
        runBlockingTest {
            // verify
            assertEquals(State.UiModelTheme.SYSTEM, viewModel.state.theme)

            // change the theme
            viewModel.onSelectTheme(State.UiModelTheme.DARK)

            // verify again
            assertEquals(State.UiModelTheme.DARK, viewModel.state.theme)
        }
    }
}