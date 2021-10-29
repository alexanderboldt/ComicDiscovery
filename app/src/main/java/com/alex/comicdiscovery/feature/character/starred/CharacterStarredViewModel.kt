package com.alex.comicdiscovery.feature.character.starred

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.starred.model.UiStateContent
import com.alex.comicdiscovery.feature.character.starred.model.UiEventCharacterStarred
import com.alex.comicdiscovery.repository.character.CharacterRepository
import com.alex.comicdiscovery.ui.components.UiModelCharacter
import com.alex.comicdiscovery.util.timberCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterStarredViewModel(
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<UiEventCharacterStarred>() {

    var content: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_loading)))
        private set

    // ----------------------------------------------------------------------------

    fun init() {
        getCharacters()
    }

    // ----------------------------------------------------------------------------

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(UiEventCharacterStarred.DetailScreen(id))
        }
    }

    // ----------------------------------------------------------------------------

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            characterRepository
                .getStarredCharacters()
                .onStart { content = UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_loading)) }
                .timberCatch { content = UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_error)) }
                .collect { result ->
                    result
                        .result
                        .map { character ->
                            UiModelCharacter(
                                character.id,
                                character.name,
                                character.realName,
                                character.smallImageUrl)
                        }.also { characters ->
                            content = when (characters.isEmpty()) {
                                true -> UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_no_entries))
                                false -> UiStateContent.Characters(characters)
                            }
                        }
                }
        }
    }
}