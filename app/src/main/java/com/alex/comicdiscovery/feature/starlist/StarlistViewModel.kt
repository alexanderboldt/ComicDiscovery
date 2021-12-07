package com.alex.comicdiscovery.feature.starlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.starlist.model.UiModelStarlistItem
import com.alex.comicdiscovery.repository.models.RpModelList
import com.alex.comicdiscovery.repository.starlist.StarlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

class StarlistViewModel(private val starlistRepository: StarlistRepository) : BaseViewModel<Unit, Unit>(Unit) {

    var starlists: List<UiModelStarlistItem> by mutableStateOf(emptyList())
        private set

    var starlistNameNew: String by mutableStateOf("")
        private set

    val isStarlistCreateButtonEnabled: Boolean
        get() = starlistNameNew.isNotBlank()

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .getAll()
                .collect { mapStarlists(it) }
        }
    }

    // ----------------------------------------------------------------------------

    // create

    fun setNewStarlistName(name: String) {
        starlistNameNew = name
    }

    fun onCreateNewStarlist() {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .create(starlistNameNew)
                .flatMapConcat { starlistRepository.getAll() }
                .collect {
                    mapStarlists(it)
                    starlistNameNew = ""
                }
        }
    }

    // update

    fun updateStarlist(id: Long, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            starlistRepository
                .update(id, name)
                .flatMapConcat { starlistRepository.getAll() }
                .collect { mapStarlists(it) }
        }
    }

    // delete

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
        this.starlists = repositoryStarlists.map { UiModelStarlistItem.Existing(it.id, it.name) } + UiModelStarlistItem.New
    }
}