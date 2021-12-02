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
import com.alex.comicdiscovery.feature.character.starred.model.UiModelStarlist
import com.alex.comicdiscovery.feature.character.starred.model.UiStateStarlist
import com.alex.comicdiscovery.repository.starlist.StarlistRepository
import com.alex.comicdiscovery.ui.components.UiModelCharacter
import com.alex.comicdiscovery.util.timberCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterStarredViewModel(
    private val starlistRepository: StarlistRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<UiEventCharacterStarred>() {

    var starlists: UiStateStarlist by mutableStateOf(UiStateStarlist.NoListsAvailable)
        private set

    var selectedStarlistIndex by mutableStateOf(0)
        private set

    var content: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_loading)))
        private set

    // ----------------------------------------------------------------------------

    fun init() {
        getStarlistsAndCharacters()
    }

    // ----------------------------------------------------------------------------

    fun onClickStarlist(index: Int) {
        selectedStarlistIndex = index
        getCharacters()
    }

    fun onClickStarlistSettings() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(UiEventCharacterStarred.StarlistSettingsScreen)
        }
    }

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(UiEventCharacterStarred.DetailScreen(id))
        }
    }

    // ----------------------------------------------------------------------------

    private fun getStarlistsAndCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getAll()
                .onStart { content = UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_loading)) }
                .timberCatch { content = UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_error)) }
                .collect {  starlists ->
                    if (starlists.isEmpty()) {
                        this@CharacterStarredViewModel.starlists = UiStateStarlist.NoListsAvailable
                        content = UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_no_characters))
                    } else {
                        this@CharacterStarredViewModel.starlists = UiStateStarlist.Starlists(starlists.map { UiModelStarlist(it.id, it.name) })

                        getCharacters()
                    }
                }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getStarredCharacters((starlists as UiStateStarlist.Starlists).starlists[selectedStarlistIndex].id)
                .timberCatch {
                    content = UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_no_characters))
                }.collect { response ->
                    response
                        .result
                        .map { character ->
                            UiModelCharacter(
                                character.id,
                                character.name,
                                character.realName,
                                character.smallImageUrl
                            )
                        }.also { characters ->
                            content = when (characters.isEmpty()) {
                                true -> UiStateContent.Message(resourceProvider.getString(R.string.character_starred_message_no_characters_in_starlist))
                                false -> UiStateContent.Characters(characters)
                            }
                        }
                }
        }
    }
}