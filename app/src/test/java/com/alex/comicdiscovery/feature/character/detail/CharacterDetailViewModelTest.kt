package com.alex.comicdiscovery.feature.character.detail

import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.BaseViewModelTest
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.repository.character.CharacterRepository
import com.alex.repository.models.RpModelCharacter
import com.alex.repository.models.RpModelResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: CharacterDetailViewModel

    // mocking dependencies
    @Mock private lateinit var characterRepository: CharacterRepository
    @Mock private lateinit var resourceProvider: ResourceProvider

    // prepare variables
    private val drawableStarOn = android.R.drawable.btn_star_big_on
    private val drawableStarOff = android.R.drawable.btn_star_big_off

    private val stringLoading = "Loading character …"
    private val stringError = "Could not load character"

    // ----------------------------------------------------------------------------

    @Before
    fun before() {
        `when`(resourceProvider.getString(R.string.character_detail_message_loading)).thenReturn(stringLoading)
        //`when`(resourceProvider.getString(R.string.character_detail_message_error)).thenReturn(stringError)
    }

    // ----------------------------------------------------------------------------

    @Test
    fun `it should be successful with default-theme`() {
        runBlockingTest {
            // mock the search
            val flow = flow {
                val response = RpModelResponse(
                    1,
                    1,
                    RpModelCharacter(
                        1,
                        "Superman",
                        "Clark Kent",
                        "url_to_image",
                        1,
                        "Kal-El\nClark Kent",
                        "18. June 1971",
                        listOf("strength"),
                        "Alien",
                        false))
                emit(response)
            }

            `when`(characterRepository.getStarredCharacter(anyInt())).thenReturn(flow)

            // execute
            viewModel = CharacterDetailViewModel(
                1,
                true,
                characterRepository,
                resourceProvider)

            // verify
            // todo: fix tests
            /*
            val contentState = UiStateContent.CharacterStateContent(
                UiModelCharacter(
                    "url_to_image",
                    "Superman",
                    "Clark Kent",
                    "Kal-El\nClark Kent",
                    "Male",
                    "18. June 1971",
                    "Alien",
                    "strength"
                ))
            delay(1_000)
            assertEquals(UiStateContent.LoadingStateContent(stringLoading), viewModel.contentState)
            assertEquals(drawableStarOff, viewModel.starState)

             */
        }
    }
}