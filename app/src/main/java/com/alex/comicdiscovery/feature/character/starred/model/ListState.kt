package com.alex.comicdiscovery.feature.character.starred.model

sealed class ListState {
    data class CharacterState(val characters: List<UiModelCharacter>) : ListState()
    data class MessageState(val message: String) : ListState()
}