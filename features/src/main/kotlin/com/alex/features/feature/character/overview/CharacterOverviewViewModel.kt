package com.alex.features.feature.character.overview

import androidx.lifecycle.viewModelScope
import com.alex.features.R
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.base.ResourceProvider
import com.alex.features.feature.character.overview.model.SideEffect
import com.alex.features.feature.character.overview.model.State
import com.alex.repository.SearchRepository
import com.alex.features.ui.components.UiModelCharacter
import com.alex.features.ui.components.UiModelLoadMore
import com.alex.features.util.timberCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterOverviewViewModel(
    private val searchRepository: SearchRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel<State, SideEffect>(State(State.Content.Message(resourceProvider.getString(R.string.character_overview_message_no_search)))) {

    private val numberOfLoadedCharacters: Int
        get() = (state.content as? State.Content.Items)?.run { items.count { it is UiModelCharacter } } ?: 0

    private val pageSize = 10

    // ----------------------------------------------------------------------------

    fun onQueryChange(newQuery: String) {
        state.query = newQuery
    }

    fun onQuerySubmit() {
        when (state.query.isBlank()) {
            true -> state.content = State.Content.Message(resourceProvider.getString(R.string.character_overview_message_no_search))
            false -> search(state.query, pageSize)
        }
    }

    fun onClickCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            postSideEffect(SideEffect.DetailScreen(id))
        }
    }

    fun onClickLoadMore() {
        search(state.query,numberOfLoadedCharacters + pageSize)
    }

    // ----------------------------------------------------------------------------

    private fun search(query: String, limit: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            searchRepository
                .getSearch(query, limit)
                .onStart {
                    state.content = when (state.content) {
                        is State.Content.Items -> State.Content.Items((state.content as State.Content.Items).items.filterIsInstance<UiModelCharacter>() + UiModelLoadMore(false))
                        else -> State.Content.Loading(resourceProvider.getString(R.string.character_overview_message_loading))
                    }
                }.timberCatch { state.content = State.Content.Message(resourceProvider.getString(R.string.character_overview_message_error)) }
                .collect { result ->
                    result
                        .result
                        .map {  character -> UiModelCharacter(character.id, character.name, character.realName, character.smallImageUrl) }
                        .also { characters ->
                            state.content = when (characters.isEmpty()) {
                                true -> State.Content.Message(resourceProvider.getString(R.string.character_overview_message_no_entries))
                                false -> {
                                    when (result.numberOfPageResults < result.numberOfTotalResults) {
                                        true -> characters + UiModelLoadMore(true)
                                        false -> characters
                                    }.let { State.Content.Items(it) }
                                }
                            }
                        }
                }
        }
    }
}