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
import kotlinx.coroutines.launch

class CharacterOverviewViewModel(
    searchRepository: SearchRepository,
    resourceProvider: ResourceProvider) : ViewModel() {

    private val _recyclerViewState = MutableLiveData<RecyclerViewState>()
    val recyclerViewState: LiveData<RecyclerViewState> = _recyclerViewState

    // true -> is visible
    // false -> is gone
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch {
            _loadingState.postValue(true)

            when (val result = searchRepository.getSearch("Barry Allen")) {
                is Result.Success -> {
                    _loadingState.postValue(false)
                    result
                        .data
                        .result
                        .map {  character -> Character(character.name, character.realName, character.image.iconUrl) }
                        .apply { _recyclerViewState.postValue(RecyclerViewState.CharacterState(this)) }
                }
                is Result.Failure -> {
                    _loadingState.postValue(false)
                    _recyclerViewState.postValue(RecyclerViewState.MessageState(resourceProvider.getString(R.string.character_overview_message_error)))
                }
            }
        }
    }
}