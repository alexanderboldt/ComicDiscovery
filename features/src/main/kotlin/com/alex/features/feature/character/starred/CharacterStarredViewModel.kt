package com.alex.features.feature.character.starred

import androidx.lifecycle.viewModelScope
import com.alex.features.R
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.base.ResourceProvider
import com.alex.features.feature.character.starred.model.*
import com.alex.repository.StarlistRepository
import com.alex.features.ui.components.UiModelCharacter
import com.alex.features.util.printCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterStarredViewModel(
    private val starlistRepository: StarlistRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<State, SideEffect>(
    State(State.Content.Message(resourceProvider.getString(R.string.character_starred_message_loading)))) {

    fun init() {
        getStarlistsAndCharacters()
    }

    // ----------------------------------------------------------------------------

    fun onClickStarlist(index: Int) {
        state.selectedStarlistIndex = index
        getCharacters()
    }

    fun onClickStarlistSettings() {
        viewModelScope.launch(Dispatchers.Main) {
            postSideEffect(SideEffect.StarlistSettingsScreen)
        }
    }

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            postSideEffect(SideEffect.CharacterDetailScreen(id))
        }
    }

    // ----------------------------------------------------------------------------

    private fun getStarlistsAndCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getAll()
                .onStart { state.content = State.Content.Message(resourceProvider.getString(R.string.character_starred_message_loading)) }
                .printCatch { state.content = State.Content.Message(resourceProvider.getString(R.string.character_starred_message_error)) }
                .collect {  starlists ->
                    if (starlists.isEmpty()) {
                        state.starlists = State.Starlist.NoListsAvailable
                        state.content = State.Content.Message(resourceProvider.getString(R.string.character_starred_message_no_characters))
                    } else {
                        state.starlists = State.Starlist.Starlists(starlists.map {
                            State.UiModelStarlist(it.id, it.name)
                        })

                        getCharacters()
                    }
                }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getStarredCharacters((state.starlists as State.Starlist.Starlists).starlists[state.selectedStarlistIndex].id)
                .printCatch { state.content = State.Content.Message(resourceProvider.getString(R.string.character_starred_message_no_characters)) }
                .collect { response ->
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
                            state.content = when (characters.isEmpty()) {
                                true -> State.Content.Message(resourceProvider.getString(R.string.character_starred_message_no_characters_in_starlist))
                                false -> State.Content.Characters(characters)
                            }
                        }
                }
        }
    }
}