package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.base.ResourceProvider
import com.alex.comicdiscovery.feature.character.overview.model.UiStateContent
import com.alex.comicdiscovery.feature.character.overview.model.UiEventCharacterOverview
import com.alex.comicdiscovery.repository.search.SearchRepository
import com.alex.comicdiscovery.ui.components.UiModelCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterOverviewViewModel(
    private val searchRepository: SearchRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<UiEventCharacterOverview>() {

    var query: String by mutableStateOf("")
        private set

    var listState: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_no_search)))
        private set

    // ----------------------------------------------------------------------------

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    fun onQuerySubmit() {
        when (query.isBlank()) {
            true -> listState = UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_no_search))
            false -> search(query)
        }
    }

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(UiEventCharacterOverview.DetailScreen(id))
        }
    }

    // ----------------------------------------------------------------------------

    private fun search(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            searchRepository
                .getSearch(query)
                .onStart {
                    listState = UiStateContent.Loading(resourceProvider.getString(R.string.character_overview_message_loading))
                }.catch { throwable ->
                    listState = UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_error))

                    Timber.w(throwable)
                }.collect { result ->
                    result
                        .result
                        .map {  character -> UiModelCharacter(character.id, character.name, character.realName, character.smallImageUrl) }
                        .also { characters ->
                            listState = when (characters.isEmpty()) {
                                true -> UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_no_entries))
                                false -> UiStateContent.Characters(characters)
                            }
                        }
                }
        }
    }
}