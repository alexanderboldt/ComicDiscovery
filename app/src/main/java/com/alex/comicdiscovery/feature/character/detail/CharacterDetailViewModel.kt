package com.alex.comicdiscovery.feature.character.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.detail.models.UiModelCharacter
import com.alex.comicdiscovery.feature.character.detail.models.ContentState
import com.alex.comicdiscovery.repository.character.CharacterRepository
import com.alex.comicdiscovery.repository.models.RpModelResult
import kotlinx.coroutines.Dispatchers
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

    fun init(id: Int, userComesFromStarredScreen: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

            _loadingState.postValue(true)

            currentId = id

            when (userComesFromStarredScreen) {
                true -> characterRepository.getStarredCharacter(id)
                false -> characterRepository.getCharacter(id)
            }.also { result ->
                when (result) {
                    is RpModelResult.Success -> {
                        _loadingState.postValue(false)

                        val character = result.data.result
                        isStarred = character.isStarred

                        _loadingState.postValue(false)
                        _contentState.postValue(
                            ContentState.CharacterState(
                                UiModelCharacter(
                                    character.image.smallUrl,
                                    "Name\n ${character.name}",
                                    "Real Name\n ${character.realName}",
                                    "Aliases\n ${character.aliases}",
                                    "Gender\n ${character.gender}",
                                    "Birth\n ${character.birth}",
                                    "Powers\n ${character.powers}",
                                    "Origin\n ${character.origin}"
                                )
                            )
                        )
                        _starState.postValue(getStarIcon())
                    }
                    is RpModelResult.Failure -> {
                        _loadingState.postValue(false)
                        _contentState.postValue(
                            ContentState.MessageState(resourceProvider.getString(R.string.character_detail_message_error))
                        )
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onClickStar() {
        viewModelScope.launch(Dispatchers.IO) {
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