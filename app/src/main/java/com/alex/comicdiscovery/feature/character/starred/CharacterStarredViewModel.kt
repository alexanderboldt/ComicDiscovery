package com.alex.comicdiscovery.feature.character.starred

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.starred.model.ListState
import com.alex.comicdiscovery.feature.character.starred.model.UiModelCharacter
import com.alex.comicdiscovery.repository.character.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterStarredViewModel(
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    var listState: ListState by mutableStateOf(ListState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search)))
        private set

    var detailScreen: Int by mutableStateOf(-1)
        private set

    // ----------------------------------------------------------------------------

    init {
        getCharacters()
    }

    // ----------------------------------------------------------------------------

    fun onClickCharacter(id: Int) {

    }

    // ----------------------------------------------------------------------------

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            characterRepository
                .getStarredCharacters()
                .onStart {
                    listState = ListState.MessageState(resourceProvider.getString(R.string.character_starred_message_loading))
                }.catch { throwable ->
                    listState = ListState.MessageState(resourceProvider.getString(R.string.character_starred_message_error))

                    Timber.w(throwable)
                }.collect { result ->
                    result
                        .result
                        .map { character ->
                            UiModelCharacter(
                                character.id,
                                character.name,
                                character.realName,
                                character.image.smallUrl)
                        }.also { characters ->
                            listState = when (characters.isEmpty()) {
                                true -> ListState.MessageState(resourceProvider.getString(R.string.character_starred_message_no_entries))
                                false -> ListState.CharacterState(characters)
                            }
                        }
                }
        }
    }
}