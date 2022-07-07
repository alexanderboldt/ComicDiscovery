package com.alex.features.feature.starlist

import androidx.lifecycle.viewModelScope
import com.alex.features.feature.base.BaseViewModel
import com.alex.features.feature.starlist.model.State
import com.alex.repository.model.RpModelList
import com.alex.repository.StarlistRepository
import com.alex.util.clearAndAdd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

class StarlistViewModel(private val starlistRepository: StarlistRepository) : BaseViewModel<State, Unit>(State()) {

    init {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getAll()
                .collect { mapStarlists(it) }
        }
    }

    // ----------------------------------------------------------------------------

    fun setNewStarlistName(name: String) {
        state.starlistNameNew = name
    }

    fun onCreateNewStarlist() {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .create(state.starlistNameNew)
                .flatMapConcat { starlistRepository.getAll() }
                .collect {
                    mapStarlists(it)
                    state.starlistNameNew = ""
                }
        }
    }

    // ----------------------------------------------------------------------------

    fun updateStarlist(id: Long, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .update(id, name)
                .flatMapConcat { starlistRepository.getAll() }
                .collect { mapStarlists(it) }
        }
    }

    // ----------------------------------------------------------------------------

    fun onDeleteStarlist(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .delete(id)
                .flatMapConcat { starlistRepository.getAll() }
                .collect { mapStarlists(it) }
        }
    }

    // ----------------------------------------------------------------------------

    private fun mapStarlists(repositoryStarlists: List<RpModelList>) {
        repositoryStarlists
            .map { State.StarlistItem.Existing(it.id, it.name) }
            .plus(State.StarlistItem.New)
            .also { state.starlists.clearAndAdd(it) }
    }
}