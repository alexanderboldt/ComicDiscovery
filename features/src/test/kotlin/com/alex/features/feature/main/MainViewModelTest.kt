package com.alex.features.feature.main

import com.alex.features.feature.BaseViewModelTest
import com.alex.features.feature.main.model.State
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
        runTest {
            // verify
            assertThat(State.UiModelTheme.SYSTEM).isEqualTo(viewModel.state.theme)
        }
    }

    @Test
    fun `it should be successful with light-theme`() {
        runTest {
            val flowTheme = flowOf(RpModelTheme.LIGHT)
            `when`(settingsRepository.getTheme()).thenReturn(flowTheme)

            // verify
            assertThat(State.UiModelTheme.LIGHT).isEqualTo(viewModel.state.theme)
        }
    }
}