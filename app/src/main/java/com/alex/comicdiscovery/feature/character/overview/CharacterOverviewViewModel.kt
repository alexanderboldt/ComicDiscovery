package com.alex.comicdiscovery.feature.character.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.overview.models.UiModelCharacter
import com.alex.comicdiscovery.feature.character.overview.models.RecyclerViewState
import com.alex.comicdiscovery.repository.search.SearchRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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

    private val _hideKeyboardState = MutableLiveData<Unit>()
    val hideKeyboardState: LiveData<Unit> = _hideKeyboardState

    // ----------------------------------------------------------------------------

    init {
        _loadingState.postValue(false)
        _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search)))
    }

    // ----------------------------------------------------------------------------

    fun onSubmitSearch(query: String?) {
        when (query.isNullOrBlank()) {
            true -> _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search)))
            false -> {
                search(query)
                _hideKeyboardState.postValue(Unit)
            }
        }
    }

    fun onClickCharacter(id: Int) {
        _detailState.postValue(id)
    }

    // ----------------------------------------------------------------------------

    private fun search(query: String) {
        viewModelScope.launch(Dispatchers.Main) {

            _loadingState.postValue(true)

            searchRepository
                .getSearch(query)
                .catch { throwable ->
                    _loadingState.postValue(false)
                    _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_error)))
                }
                .collect { result ->
                    _loadingState.postValue(false)

                    result
                        .result
                        .map {  character -> UiModelCharacter(character.id, character.name, character.realName, character.image.smallUrl) }
                        .let { characters ->
                            when (characters.isEmpty()) {
                                true -> RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_entries))
                                false -> RecyclerViewState.CharacterState(characters)
                            }
                        }.also { state -> _recyclerViewState.postValue(state) }
                }
        }
    }
}