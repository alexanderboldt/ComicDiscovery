package com.alex.comicdiscovery.feature.character.starred.model

sealed class RecyclerViewState {
    data class CharacterState(val characters: List<UiModelCharacter>) : RecyclerViewState()
    data class LoadingState(val message: String) : RecyclerViewState()
    data class MessageState(val message: String) : RecyclerViewState()
}