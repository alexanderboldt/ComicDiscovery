package com.alex.comicdiscovery.feature.character.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.detail.model.*
import com.alex.comicdiscovery.repository.character.CharacterRepository
import com.alex.comicdiscovery.repository.starlist.StarlistRepository
import com.alex.comicdiscovery.util.timberCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterId: Int,
    private val userComesFromStarredScreen: Boolean,
    private val starlistRepository: StarlistRepository,
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<UiEventCharacterDetail>() {

    var content: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_detail_message_loading)))
        private set

    var starlists: UiStateStarlist by mutableStateOf(UiStateStarlist.NoListsAvailable)
        private set

    var isStarlistLoading: Boolean by mutableStateOf(false)
        private set

    // ----------------------------------------------------------------------------

    init {
        getCharacter()
        getStarlists()
    }

    // ----------------------------------------------------------------------------

    fun onClickCheckStarlist(id: Long, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            when (isChecked) {
                true -> starlistRepository.addCharacter(id, characterId)
                false -> starlistRepository.removeCharacter(id, characterId)
            }.onStart {
                isStarlistLoading = true
            }.timberCatch {
                when (isChecked) {
                    true -> R.string.character_detail_message_error_star
                    false -> R.string.character_detail_message_error_unstar
                }.also { messageResource ->
                    sendEvent(UiEventCharacterDetail.Message(resourceProvider.getString(messageResource)))
                }
                isStarlistLoading = false
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
                false -> characterRepository.getCharacterDetail(characterId)
            }.onStart {
                content = UiStateContent.Loading(resourceProvider.getString(R.string.character_detail_message_loading))
            }.timberCatch {
                content = UiStateContent.Message(resourceProvider.getString(R.string.character_detail_message_error))
            }.collect { result ->
                val character = result.result

                content = UiStateContent.Character(
                    UiModelCharacter(
                        character.smallImageUrl,
                        character.name,
                        character.realName ?: "-",
                        character.aliases ?: "-",
                        when (character.gender) {
                            1 -> resourceProvider.getString(R.string.character_detail_gender_male)
                            2 -> resourceProvider.getString(R.string.character_detail_gender_female)
                            else -> resourceProvider.getString(R.string.character_detail_gender_other)
                        },
                        character.birth ?: "-",
                        character.origin,
                        character.powers.joinToString("\n")
                    ))
            }
        }
    }

    private fun getStarlists() {
        viewModelScope.launch(Dispatchers.Main) {
            combine(
                starlistRepository.getAssociatedStarlists(characterId),
                starlistRepository.getAllStarlists()) { starlistIds, starlists ->
                starlistIds to starlists
            }.collect { (starlistIds, starlists) ->
                isStarlistLoading = false
                this@CharacterDetailViewModel.starlists = if (starlists.isEmpty()) {
                    UiStateStarlist.NoListsAvailable
                } else {
                    UiStateStarlist.Starlists(starlists.map { UiModelStarlist(it.id, it.name, it.id in starlistIds) })
                }
            }
        }
    }
}