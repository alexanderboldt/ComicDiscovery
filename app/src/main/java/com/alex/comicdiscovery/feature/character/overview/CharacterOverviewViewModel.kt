package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.overview.model.ListState
import com.alex.comicdiscovery.repository.search.SearchRepository
import com.alex.comicdiscovery.ui.components.UiModelCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterOverviewViewModel(
    private val searchRepository: SearchRepository,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    var query: String by mutableStateOf("")
        private set

    var listState: ListState by mutableStateOf(ListState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search)))
        private set

    private var _detailScreen = Channel<Int>(Channel.RENDEZVOUS)
    val detailScreen = _detailScreen.receiveAsFlow()

    // ----------------------------------------------------------------------------

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    fun onQuerySubmit() {
        when (query.isBlank()) {
            true -> listState = ListState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_search))
            false -> search(query)
        }
    }

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _detailScreen.send(id)
        }
    }

    // ----------------------------------------------------------------------------

    private fun search(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            searchRepository
                .getSearch(query)
                .onStart {
                    listState = ListState.LoadingState(resourceProvider.getString(R.string.character_overview_message_loading))
                }.catch { throwable ->
                    listState = ListState.MessageState(resourceProvider.getString(R.string.character_overview_message_error))

                    Timber.w(throwable)
                }.collect { result ->
                    result
                        .result
                        .map {  character -> UiModelCharacter(character.id, character.name, character.realName, character.image.smallUrl) }
                        .also { characters ->
                            listState = when (characters.isEmpty()) {
                                true -> ListState.MessageState(resourceProvider.getString(R.string.character_overview_message_no_entries))
                                false -> ListState.CharacterState(characters)
                            }
                        }
                }
        }
    }
}