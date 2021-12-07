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
import com.alex.comicdiscovery.ui.components.UiModelLoadMore
import com.alex.comicdiscovery.util.timberCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterOverviewViewModel(
    private val searchRepository: SearchRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<Unit, UiEventCharacterOverview>(Unit) {

    var query: String by mutableStateOf("")
        private set

    var content: UiStateContent by mutableStateOf(UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_no_search)))
        private set

    private val numberOfLoadedCharacters: Int
        get() = (content as? UiStateContent.Items)?.run { items.count { it is UiModelCharacter } } ?: 0

    private val pageSize = 10

    // ----------------------------------------------------------------------------

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    fun onQuerySubmit() {
        when (query.isBlank()) {
            true -> content = UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_no_search))
            false -> search(query, pageSize)
        }
    }

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            postSideEffect(UiEventCharacterOverview.DetailScreen(id))
        }
    }

    fun onClickLoadMore() {
        search(query,numberOfLoadedCharacters + pageSize)
    }

    // ----------------------------------------------------------------------------

    private fun search(query: String, limit: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            searchRepository
                .getSearch(query, limit)
                .onStart {
                    content = when (content) {
                        is UiStateContent.Items -> UiStateContent.Items((content as UiStateContent.Items).items.filterIsInstance<UiModelCharacter>() + UiModelLoadMore(false))
                        else -> UiStateContent.Loading(resourceProvider.getString(R.string.character_overview_message_loading))
                    }
                }.timberCatch { content = UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_error)) }
                .collect { result ->
                    result
                        .result
                        .map {  character -> UiModelCharacter(character.id, character.name, character.realName, character.smallImageUrl) }
                        .also { characters ->
                            content = when (characters.isEmpty()) {
                                true -> UiStateContent.Message(resourceProvider.getString(R.string.character_overview_message_no_entries))
                                false -> {
                                    when (result.numberOfPageResults < result.numberOfTotalResults) {
                                        true -> characters + UiModelLoadMore(true)
                                        false -> characters
                                    }.let { UiStateContent.Items(it) }
                                }
                            }
                        }
                }
        }
    }
}