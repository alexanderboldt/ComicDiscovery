package com.alex.comicdiscovery.feature.character.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.detail.model.UiModelCharacter
import com.alex.comicdiscovery.feature.character.detail.model.UiStateContent
import com.alex.comicdiscovery.feature.character.detail.model.UiEventCharacterDetail
import com.alex.comicdiscovery.repository.character.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterDetailViewModel(
    private val characterId: Int,
    private val userComesFromStarredScreen: Boolean,
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<UiEventCharacterDetail>() {

    var contentState: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_detail_message_loading)))
        private set

    var starState: Int by mutableStateOf(getStarIcon())
        private set

    // ----------------------------------------------------------------------------

    private var isStarred = false

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            when (userComesFromStarredScreen) {
                true -> characterRepository.getStarredCharacter(characterId)
                false -> characterRepository.getCharacter(characterId)
            }.onStart {
                contentState = UiStateContent.Loading(resourceProvider.getString(R.string.character_detail_message_loading))
            }.catch { throwable ->
                contentState = UiStateContent.Message(resourceProvider.getString(R.string.character_detail_message_error))

                Timber.w(throwable)
            }.collect { result ->
                val character = result.result
                isStarred = character.isStarred

                contentState = UiStateContent.Character(
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

                starState = getStarIcon()
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onClickStar() {
        viewModelScope.launch(Dispatchers.Main) {
            when (isStarred) {
                true -> characterRepository.unstarCharacter(characterId)
                false -> characterRepository.starCharacter(characterId)
            }.catch { throwable ->
                when (isStarred) {
                    true -> R.string.character_detail_message_error_unstar
                    false -> R.string.character_detail_message_error_star
                }.also { messageResource ->
                    sendEvent(UiEventCharacterDetail.Message(resourceProvider.getString(messageResource)))
                }

                Timber.w(throwable)
            }.collect {
                isStarred = !isStarred
                starState = getStarIcon()
            }
        }
    }

    // ----------------------------------------------------------------------------

    private fun getStarIcon() = if (isStarred) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
}