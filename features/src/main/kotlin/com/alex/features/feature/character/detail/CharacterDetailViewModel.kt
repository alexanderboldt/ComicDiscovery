package com.alex.features.feature.character.detail

import androidx.lifecycle.viewModelScope
import com.alex.features.R
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.base.ResourceProvider
import com.alex.features.feature.character.detail.model.*
import com.alex.features.util.printCatch
import com.alex.repository.CharacterRepository
import com.alex.repository.StarlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterId: Int,
    private val userComesFromStarredScreen: Boolean,
    private val starlistRepository: StarlistRepository,
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<State, SideEffect>(State(State.Content.Message(resourceProvider.getString(R.string.character_detail_message_loading)))) {

    init {
        getCharacter()
        getStarlists()
    }

    // ----------------------------------------------------------------------------

    fun onClickCheckStarlist(id: Long, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            when (isChecked) {
                true -> starlistRepository.linkCharacter(id, characterId)
                false -> starlistRepository.releaseCharacter(id, characterId)
            }.onStart {
                state.isStarlistLoading = true
            }.printCatch {
                when (isChecked) {
                    true -> R.string.character_detail_message_error_star
                    false -> R.string.character_detail_message_error_unstar
                }.also { messageResource ->
                    postSideEffect(SideEffect.Message(resourceProvider.getString(messageResource)))
                }
                state.isStarlistLoading = false
            }.collect {
                getStarlists()
            }
        }
    }

    // ----------------------------------------------------------------------------

    private fun getCharacter() {
        viewModelScope.launch(Dispatchers.Main) {
            when (userComesFromStarredScreen) {
                true -> characterRepository.getStarredCharacter(characterId)
                false -> characterRepository.getCharacter(characterId)
            }.onStart {
                state.content = State.Content.Loading(resourceProvider.getString(R.string.character_detail_message_loading))
            }.printCatch {
                state.content = State.Content.Message(resourceProvider.getString(R.string.character_detail_message_error))
            }.collect { result ->
                val character = result.result

                state.content = State.Content.Character(
                    State.UiModelCharacter(
                        character.smallImageUrl,
                        character.name,
                        character.summary,
                        character.realName ?: "-",
                        character.aliases ?: "-",
                        when (character.gender) {
                            1 -> resourceProvider.getString(R.string.character_detail_gender_male)
                            2 -> resourceProvider.getString(R.string.character_detail_gender_female)
                            else -> resourceProvider.getString(R.string.character_detail_gender_other)
                        },
                        character.birth ?: "-",
                        character.origin,
                        character.powers.joinToString("\n"),
                        character.teams.joinToString("\n"),
                        character.friends.joinToString("\n"),
                        character.enemies.joinToString("\n")
                    )
                )
            }
        }
    }

    private fun getStarlists() {
        viewModelScope.launch(Dispatchers.Main) {
            combine(
                starlistRepository.getAssociatedStarlists(characterId),
                starlistRepository.getAll()) { starlistIds, starlists ->
                starlistIds to starlists
            }.collect { (starlistIds, starlists) ->
                state.isStarlistLoading = false
                state.starlist = when (starlists.isEmpty()) {
                    true -> State.Starlist.NoListsAvailable
                    false -> State.Starlist.Starlists(starlists.map { State.UiModelStarlist(it.id, it.name, it.id in starlistIds) })
                }
            }
        }
    }
}