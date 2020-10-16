package com.alex.comicdiscovery.feature.character.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.overview.models.RecyclerViewState
import com.alex.comicdiscovery.feature.character.overview.models.UiModelCharacter
import com.alex.comicdiscovery.repository.models.RpModelCharacterOverview
import com.alex.comicdiscovery.repository.models.RpModelImage
import com.alex.comicdiscovery.repository.models.RpModelResponse
import com.alex.comicdiscovery.repository.search.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterOverviewViewModelTest {

    @get:Rule val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: CharacterOverviewViewModel

    // mocking dependencies
    @Mock private lateinit var searchRepository: SearchRepository
    @Mock private lateinit var resourceProvider: ResourceProvider

    // mocking observers
    @Mock private lateinit var recyclerViewStateMock: Observer<RecyclerViewState>
    @Mock private lateinit var loadingStateMock: Observer<Boolean>
    @Mock private lateinit var detailStateMock: Observer<Int>
    @Mock private lateinit var hideKeyboardStateMock: Observer<Unit>

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)

        `when`(resourceProvider.getString(R.string.character_overview_message_no_search)).thenReturn("Make a search for some characters")

        viewModel = CharacterOverviewViewModel(searchRepository, resourceProvider).apply {
            recyclerViewState.observeForever(recyclerViewStateMock)
            loadingState.observeForever(loadingStateMock)
            detailState.observeForever(detailStateMock)
            hideKeyboardState.observeForever(hideKeyboardStateMock)
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
            verify(loadingStateMock, never()).onChanged(true)
            verify(loadingStateMock, times(1)).onChanged(false)

            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.CharacterState(anyList()))
            verify(recyclerViewStateMock, times(1)).onChanged(RecyclerViewState.MessageState("Make a search for some characters"))

            verify(detailStateMock, never()).onChanged(anyInt())

            verify(hideKeyboardStateMock, never()).onChanged(any())
        }
    }

    @Test
    fun `it should not search with null string`() {
        runBlockingTest {
            reset(loadingStateMock)
            reset(recyclerViewStateMock)

            viewModel.onSubmitSearch(null)

            verify(loadingStateMock, never()).onChanged(anyBoolean())

            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.CharacterState(anyList()))
            verify(recyclerViewStateMock, times(1)).onChanged(RecyclerViewState.MessageState("Make a search for some characters"))

            verify(detailStateMock, never()).onChanged(anyInt())

            verify(hideKeyboardStateMock, never()).onChanged(any())
        }
    }

    @Test
    fun `it should not search with empty string`() {
        runBlockingTest {
            reset(loadingStateMock)
            reset(recyclerViewStateMock)

            viewModel.onSubmitSearch("")

            verify(loadingStateMock, never()).onChanged(anyBoolean())

            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.CharacterState(anyList()))
            verify(recyclerViewStateMock, times(1)).onChanged(RecyclerViewState.MessageState("Make a search for some characters"))

            verify(detailStateMock, never()).onChanged(anyInt())

            verify(hideKeyboardStateMock, never()).onChanged(any())
        }
    }

    @Test
    fun `it should search with valid string`() {
        runBlockingTest {
            // reset mocks
            reset(loadingStateMock)
            reset(recyclerViewStateMock)

            // predefine
            val queryTerm = "barry allen"

            // mock the search
            val characters = listOf(RpModelCharacterOverview(0, "The flash", "Barry Allen", RpModelImage("urlToImage")))
            val flow = flowOf(RpModelResponse(0, 0, characters))
            `when`(searchRepository.getSearch(queryTerm)).thenReturn(flow)

            // execute
            viewModel.onSubmitSearch(queryTerm)

            // verify

            verify(loadingStateMock, times(1)).onChanged(true)
            verify(loadingStateMock, times(1)).onChanged(false)

            val uiUharacters = listOf(UiModelCharacter(0, "The flash", "Barry Allen", "urlToImage"))

            verify(recyclerViewStateMock, times(1)).onChanged(RecyclerViewState.CharacterState(uiUharacters))
            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.MessageState(anyString()))

            verify(detailStateMock, never()).onChanged(anyInt())

            verify(hideKeyboardStateMock, times(1)).onChanged(Unit)
        }
    }

    @Test
    fun `it should return an empty list with unknown query string`() {
        runBlockingTest {
            // reset mocks
            reset(loadingStateMock)
            reset(recyclerViewStateMock)

            // predefine
            val queryTerm = "PAJDHKJ"

            // mock
            val flow = flowOf(RpModelResponse(0, 0, emptyList<RpModelCharacterOverview>()))
            `when`(searchRepository.getSearch(queryTerm)).thenReturn(flow)

            `when`(resourceProvider.getString(R.string.character_overview_message_no_entries)).thenReturn("No characters found")

            // execute
            viewModel.onSubmitSearch(queryTerm)

            // verify

            verify(loadingStateMock, times(1)).onChanged(true)
            verify(loadingStateMock, times(1)).onChanged(false)

            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.CharacterState(anyList()))
            verify(recyclerViewStateMock, times(1)).onChanged(RecyclerViewState.MessageState("No characters found"))

            verify(detailStateMock, never()).onChanged(anyInt())

            verify(hideKeyboardStateMock, times(1)).onChanged(Unit)
        }
    }

    @Test
    fun `it should open the detail screen`() {
        runBlockingTest {
            // reset mocks
            reset(loadingStateMock)
            reset(recyclerViewStateMock)

            // predefine
            val detailId = 23

            // execute
            viewModel.onClickCharacter(detailId)

            // verify

            verify(loadingStateMock, never()).onChanged(anyBoolean())

            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.CharacterState(anyList()))
            verify(recyclerViewStateMock, never()).onChanged(RecyclerViewState.MessageState(anyString()))

            verify(detailStateMock, times(1)).onChanged(detailId)

            verify(hideKeyboardStateMock, never()).onChanged(Unit)
        }
    }
}