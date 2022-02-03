package com.alex.features.feature.main

import com.alex.features.feature.BaseViewModelTest
import com.alex.features.feature.main.model.State
import com.alex.repository.SettingsRepository
import com.alex.repository.model.RpModelTheme
import com.google.common.truth.Truth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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

    private fun initViewModel() {
        viewModel = MainViewModel(settingsRepository)
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `should be successful with mocked theme`() {
        runTest {
            // mock
            `when`(settingsRepository.getTheme()).thenReturn(flowOf(RpModelTheme.LIGHT))

            // execute
            initViewModel()

            // verify
            assertThat(viewModel.state.theme).isEqualTo(State.UiModelTheme.LIGHT)
        }
    }
}