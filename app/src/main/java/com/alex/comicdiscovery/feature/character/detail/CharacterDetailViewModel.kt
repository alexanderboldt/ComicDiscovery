package com.alex.comicdiscovery.feature.character.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.detail.models.UiModelCharacter
import com.alex.comicdiscovery.feature.character.detail.models.ContentState
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
    private val resourceProvider: ResourceProvider) : ViewModel() {

    var contentState: ContentState by mutableStateOf(ContentState.MessageState(resourceProvider.getString(R.string.character_detail_message_loading)))
        private set



    private val _starState = MutableLiveData<Int>()
    val starState: LiveData<Int> = _starState

    // ----------------------------------------------------------------------------

    private var isStarred = false

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            when (userComesFromStarredScreen) {
                true -> characterRepository.getStarredCharacter(characterId)
                false -> characterRepository.getCharacter(characterId)
            }.onStart {
                contentState = ContentState.LoadingState(resourceProvider.getString(R.string.character_detail_message_loading))
            }.catch { throwable ->
                contentState = ContentState.MessageState(resourceProvider.getString(R.string.character_detail_message_error))

                Timber.w(throwable)
            }.collect { result ->
                val character = result.result
                isStarred = character.isStarred

                contentState = ContentState.CharacterState(
                    UiModelCharacter(
                        character.image.smallUrl,
                        "Name\n ${character.name}",
                        "Real Name\n ${character.realName}",
                        "Aliases\n ${character.aliases}",
                        "Gender\n ${character.gender}",
                        "Birth\n ${character.birth}",
                        "Powers\n ${character.powers}",
                        "Origin\n ${character.origin}"
                    ))

                _starState.postValue(getStarIcon())
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onClickStar() {
        viewModelScope.launch(Dispatchers.Main) {
            when (isStarred) {
                true -> characterRepository.unstarCharacter(characterId)
                false -> characterRepository.starCharacter(characterId)
            }.collect { wasSuccessful ->
                if (wasSuccessful) {
                    isStarred = !isStarred
                    _starState.postValue(getStarIcon())
                }
            }
        }
    }

    // ----------------------------------------------------------------------------

    private fun getStarIcon() = if (isStarred) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
}