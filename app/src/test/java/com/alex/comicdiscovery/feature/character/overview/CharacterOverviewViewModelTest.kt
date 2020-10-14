package com.alex.comicdiscovery.feature.character.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.overview.models.RecyclerViewState
import com.alex.comicdiscovery.repository.search.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterOverviewViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: CharacterOverviewViewModel

    // mocking dependencies

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    // mocking observers

    @Mock
    private lateinit var recyclerViewStateMock: Observer<RecyclerViewState>

    @Mock
    private lateinit var loadingStateMock: Observer<Boolean>

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)

        `when`(resourceProvider.getString(R.string.character_overview_message_no_search)).thenReturn("Make a search for some characters")

        viewModel = CharacterOverviewViewModel(searchRepository, resourceProvider).apply {
            recyclerViewState.observeForever(recyclerViewStateMock)
            loadingState.observeForever(loadingStateMock)
        }
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `init should be successful`() {
        runBlockingTest {
//            val flow = flowOf(RpModelResponse(0, 0, emptyList<RpModelCharacterOverview>()))
//            Mockito.`when`(searchRepository.getSearch("test")).thenReturn(flow)

            verify(loadingStateMock, never()).onChanged(true)
            verify(loadingStateMock, times(1)).onChanged(false)

            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.CharacterState(ArgumentMatchers.anyList()))
            verify(recyclerViewStateMock, times(1)).onChanged(RecyclerViewState.MessageState("Make a search for some characters"))
        }
    }
}