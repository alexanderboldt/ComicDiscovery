package com.alex.comicdiscovery.feature.settings

import com.alex.comicdiscovery.feature.BaseViewModelTest
import com.alex.comicdiscovery.feature.settings.model.UiModelThemes
import com.alex.comicdiscovery.repository.settings.SettingsRepository
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
            assertEquals(UiModelThemes.SYSTEM, viewModel.theme)
        }
    }

    @Test
    fun `it should be successful with theme-change`() {
        runBlockingTest {
            // verify
            assertEquals(UiModelThemes.SYSTEM, viewModel.theme)

            // change the theme
            viewModel.onSelectTheme(UiModelThemes.DARK)

            // verify again
            assertEquals(UiModelThemes.DARK, viewModel.theme)
        }
    }
}