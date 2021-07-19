package com.alex.comicdiscovery.feature.character.overview.model

sealed class ListState {
    data class CharacterState(val characters: List<UiModelCharacter>) : ListState()
    data class LoadingState(val message: String) : ListState()
    data class MessageState(val message: String) : ListState()
}