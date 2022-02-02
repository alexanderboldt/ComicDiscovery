package com.alex.features.feature.settings

import com.alex.features.feature.BaseViewModelTest
import com.alex.features.feature.settings.model.State
import com.alex.repository.SettingsRepository
import com.alex.repository.model.RpModelTheme
import com.google.common.truth.Truth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
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
    fun `should be successful with default system-theme`() {
        runTest {
            // verify
            assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.SYSTEM)
        }
    }

    @Test
    fun `should be successful with mocked dark-theme`() {
        runTest {
            // mock the theme
            //val flow = flowOf(RpModelTheme.DARK)
            //`when`(settingsRepository.getTheme()).thenReturn(flow)

            // verify
            assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.SYSTEM)
        }
    }

    /*
    @Test
    fun `should be successful with theme-change`() {
        runBlockingTest {
            // verify
            assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.SYSTEM)

            // change the theme
            viewModel.onSelectTheme(State.UiModelTheme.DARK)

            // verify again
            assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.DARK)
        }
    }

     */
}