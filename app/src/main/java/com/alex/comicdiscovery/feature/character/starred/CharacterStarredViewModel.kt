package com.alex.comicdiscovery.feature.character.starred

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.starred.models.UiModelCharacter
import com.alex.comicdiscovery.feature.character.starred.models.RecyclerViewState
import com.alex.comicdiscovery.repository.character.CharacterRepository
import com.alex.comicdiscovery.repository.models.RpModelResult
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterStarredViewModel(
    private val characterRepository: CharacterRepository,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    private val _recyclerViewState = MutableLiveData<RecyclerViewState>()
    val recyclerViewState: LiveData<RecyclerViewState> = _recyclerViewState

    // true -> visible / false -> gone
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    // the detail-id as an Int
    private val _detailState = LiveEvent<Int>()
    val detailState: LiveData<Int> = _detailState

    // ----------------------------------------------------------------------------

    init {
        _loadingState.postValue(false)

        getCharacters()
    }

    // ----------------------------------------------------------------------------


    fun onClickCharacter(id: Int) {
        _detailState.postValue(id)
    }

    // ----------------------------------------------------------------------------

    private fun getCharacters() {
        viewModelScope.launch {
            _loadingState.postValue(true)

            when (val result = characterRepository.getStarredCharacters()) {
                is RpModelResult.Success -> {
                    _loadingState.postValue(false)
                    result
                        .data
                        .result
                        .map {  character -> UiModelCharacter(character.id, character.name, character.realName, character.image.smallUrl) }
                        .let { characters ->
                            when (characters.isEmpty()) {
                                true -> RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_starred_message_no_entries))
                                false -> RecyclerViewState.CharacterState(characters)
                            }
                        }.also { state -> _recyclerViewState.postValue(state) }
                }
                is RpModelResult.Failure -> {
                    _loadingState.postValue(false)
                    _recyclerViewState.postValue(
                        RecyclerViewState.MessageState(resourceProvider.getString(
                            R.string.character_starred_message_error)))
                }
            }
        }
    }
}