package com.alex.comicdiscovery.feature.character.overview.model

sealed class RecyclerViewState {
    data class CharacterState(val characters: List<UiModelCharacter>) : RecyclerViewState()
    data class LoadingState(val message: String) : RecyclerViewState()
    data class MessageState(val message: String) : RecyclerViewState()
}