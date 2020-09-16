package com.example.comicdiscovery.feature.character.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicdiscovery.feature.character.detail.models.Character
import com.example.comicdiscovery.repository.character.CharacterRepository
import com.example.comicdiscovery.repository.models.Result
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    private val _characterState = MutableLiveData<Character>()
    val characterState: LiveData<Character> = _characterState

    // true -> visible / false -> gone
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    // ----------------------------------------------------------------------------

    fun init(id: Int) {
        viewModelScope.launch {
            _loadingState.postValue(true)

            when (val result = characterRepository.getCharacter("4005-$id")) {
                is Result.Success -> {
                    _loadingState.postValue(false)

                    val character = result.data.result

                    _loadingState.postValue(false)
                    _characterState.postValue(Character(
                        character.image.iconUrl,
                        "Name\n ${character.name}",
                        "Real Name\n ${character.realName}",
                        "Aliases\n ${character.aliases}",
                        "Gender\n ${character.gender.toString()}",
                        "Birth\n ${character.birth}",
                        "Powers\n ${character.powers.toString()}",
                        "Origin\n ${character.origin.toString()}"
                    ))
                }
                is Result.Failure -> {
                    _loadingState.postValue(false)
                    // todo
                }
            }
        }
    }
}