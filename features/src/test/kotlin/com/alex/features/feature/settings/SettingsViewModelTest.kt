package com.alex.features.feature.settings

import com.alex.features.feature.BaseViewModelTest
import com.alex.features.feature.settings.model.State
import com.alex.repository.SettingsRepository
import com.alex.repository.model.RpModelTheme
import com.google.common.truth.Truth.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SettingsViewModelTest : BaseViewModelTest() {

    // mocking dependencies
    private val settingsRepository = mockk<SettingsRepository>(relaxed = true)

    private val viewModel: SettingsViewModel by lazy {
        SettingsViewModel(settingsRepository)
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `should be successful with mocked dark-theme`() = runTest {
        // mock
        every { settingsRepository.getTheme() } returns flowOf(RpModelTheme.DARK)

        // verify
        assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.DARK)
    }

    @Test
    fun `should be successful with theme change`() = runTest {
        // mock the default theme
        every { settingsRepository.getTheme() } returns flowOf(RpModelTheme.SYSTEM)

        // verify the default theme
        assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.SYSTEM)

        // change the theme
        viewModel.onSelectTheme(State.UiModelTheme.DARK)

        // verify the changed theme
        assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.DARK)
    }
}