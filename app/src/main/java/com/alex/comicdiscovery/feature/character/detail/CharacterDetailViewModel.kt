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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterId: Int,
    private val userComesFromStarredScreen: Boolean,
    private val starlistRepository: StarlistRepository,
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<UiEventCharacterDetail>() {

    var content: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_detail_message_loading)))
        private set

    var starring: UiModelStarring by mutableStateOf(UiModelStarring(false, false))
        private set

    // ----------------------------------------------------------------------------

    init {
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
                starring = starring.copy(isStarred = character.isStarred)

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

    // ----------------------------------------------------------------------------

    fun onClickStar() {
        viewModelScope.launch(Dispatchers.Main) {
            when (starring.isStarred) {
                true -> starlistRepository.removeCharacter(1, characterId)
                false -> starlistRepository.addCharacter(1, characterId)
            }.onStart {
                starring = starring.copy(isLoading = true)
            }.timberCatch {
                when (starring.isStarred) {
                    true -> R.string.character_detail_message_error_unstar
                    false -> R.string.character_detail_message_error_star
                }.also { messageResource ->
                    sendEvent(UiEventCharacterDetail.Message(resourceProvider.getString(messageResource)))
                }
                starring = starring.copy(isLoading = false)
            }.collect {
                starring = starring.copy(isStarred = !starring.isStarred, isLoading = false)
            }
        }
    }
}