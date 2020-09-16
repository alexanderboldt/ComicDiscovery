package com.example.comicdiscovery.feature.character.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicdiscovery.R
import com.example.comicdiscovery.feature.base.ResourceProvider
import com.example.comicdiscovery.feature.character.overview.models.Character
import com.example.comicdiscovery.feature.character.overview.models.RecyclerViewState
import com.example.comicdiscovery.repository.models.Result
import com.example.comicdiscovery.repository.search.SearchRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class CharacterOverviewViewModel(
    private val searchRepository: SearchRepository,
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
        _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search)))
    }

    // ----------------------------------------------------------------------------

    fun onSubmitSearch(query: String?) {
        when (query.isNullOrBlank()) {
            true -> _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search)))
            false -> search(query)
        }
    }

    fun onClickCharacter(id: Int) {
        _detailState.postValue(id)
    }

    // ----------------------------------------------------------------------------

    private fun search(query: String) {
        viewModelScope.launch {
            _loadingState.postValue(true)

            when (val result = searchRepository.getSearch(query)) {
                is Result.Success -> {
                    _loadingState.postValue(false)
                    result
                        .data
                        .result
                        .map {  character -> Character(character.id, character.name, character.realName, character.image.iconUrl) }
                        .let { characters ->
                            when (characters.isEmpty()) {
                                true -> RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_entries))
                                false -> RecyclerViewState.CharacterState(characters)
                            }
                        }.also { state -> _recyclerViewState.postValue(state) }
                }
                is Result.Failure -> {
                    _loadingState.postValue(false)
                    _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_error)))
                }
            }
        }
    }
}