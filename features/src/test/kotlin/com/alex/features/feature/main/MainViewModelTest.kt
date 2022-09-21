package com.alex.features.feature.main

import com.alex.features.feature.BaseViewModelTest
import com.alex.features.feature.main.model.State
import com.alex.repository.SettingsRepository
import com.alex.repository.model.RpModelTheme
import com.google.common.truth.Truth.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainViewModelTest : BaseViewModelTest() {

    // mocking dependencies
    private val settingsRepository = mockk<SettingsRepository>(relaxed = true)

    private val viewModel: MainViewModel by lazy {
        MainViewModel(settingsRepository)
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `should be successful with mocked theme`() = runTest {
        // mock
        every { settingsRepository.getTheme() } returns flowOf(RpModelTheme.LIGHT)

        // verify
        assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.LIGHT)
    }
}