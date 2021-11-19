package com.alex.comicdiscovery.feature.starlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.starlist.model.UiModelStarlist
import com.alex.comicdiscovery.repository.starlist.StarlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

class StarlistViewModel(private val starlistRepository: StarlistRepository) : ViewModel() {

    var starlists: List<UiModelStarlist> by mutableStateOf(emptyList())
        private set

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getAllStarlists()
                .collect { starlists ->
                    this@StarlistViewModel.starlists = starlists.map { UiModelStarlist(it.id, it.name) }
                }
        }
    }

    // ----------------------------------------------------------------------------

    fun onClickCreate(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .createStarlist(name)
                .flatMapConcat { starlistRepository.getAllStarlists() }
                .collect { starlists ->
                    this@StarlistViewModel.starlists = starlists.map { UiModelStarlist(it.id, it.name) }
                }
        }
    }

    fun onClickDelete(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .deleteStarlist(id)
                .flatMapConcat { starlistRepository.getAllStarlists() }
                .collect { starlists ->
                    this@StarlistViewModel.starlists = starlists.map { UiModelStarlist(it.id, it.name) }
                }
        }
    }
}