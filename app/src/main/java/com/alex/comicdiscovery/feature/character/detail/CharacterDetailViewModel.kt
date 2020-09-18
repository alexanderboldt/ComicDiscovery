package com.alex.comicdiscovery.feature.character.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.detail.models.Character
import com.alex.comicdiscovery.feature.character.detail.models.ContentState
import com.alex.comicdiscovery.repository.character.CharacterRepository
import com.alex.comicdiscovery.repository.models.Result
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    private val _contentState = MutableLiveData<ContentState>()
    val contentState: LiveData<ContentState> = _contentState

    private val _starState = MutableLiveData<Int>()
    val starState: LiveData<Int> = _starState

    // true -> visible / false -> gone
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private var currentId: Int = 0
    private var isStarred = false

    // ----------------------------------------------------------------------------

    fun init(id: Int) {
        viewModelScope.launch {
            _loadingState.postValue(true)

            currentId = id

            when (val result = characterRepository.getCharacter(id)) {
                is Result.Success -> {
                    _loadingState.postValue(false)

                    val character = result.data.result
                    isStarred = character.isStarred

                    _loadingState.postValue(false)
                    _contentState.postValue(ContentState.CharacterState(Character(
                        character.image.iconUrl,
                        "Name\n ${character.name}",
                        "Real Name\n ${character.realName}",
                        "Aliases\n ${character.aliases}",
                        "Gender\n ${character.gender.toString()}",
                        "Birth\n ${character.birth}",
                        "Powers\n ${character.powers.toString()}",
                        "Origin\n ${character.origin.toString()}"
                    )))
                    _starState.postValue(getStarIcon())
                }
                is Result.Failure -> {
                    _loadingState.postValue(false)
                    _contentState.postValue(ContentState.MessageState(resourceProvider.getString(R.string.character_detail_message_error)))
                }
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onClickStar() {
        viewModelScope.launch {
            val wasSuccessful = when (isStarred) {
                true -> characterRepository.unstarCharacter(currentId)
                false -> characterRepository.starCharacter(currentId)
            }
            if (wasSuccessful) {
                isStarred = !isStarred
                _starState.postValue(getStarIcon())
            }
        }
    }

    // ----------------------------------------------------------------------------

    private fun getStarIcon() = if (isStarred) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
}