package com.example.comicdiscovery.feature.character.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicdiscovery.feature.character.overview.models.Character
import com.example.comicdiscovery.repository.search.SearchRepository
import kotlinx.coroutines.launch

class CharacterOverviewViewModel(searchRepository: SearchRepository) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch {
            searchRepository
                .getSearch("Barry Allen")
                .result
                .map {  character -> Character(character.name, character.realName, character.image.iconUrl) }
                .apply { _characters.postValue(this) }
        }
    }
}