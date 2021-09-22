package com.alex.comicdiscovery.feature.character.detail.model

sealed class ContentState {
    data class CharacterState(val character: UiModelCharacter) : ContentState()
    data class LoadingState(val message: String) : ContentState()
    data class MessageState(val message: String) : ContentState()
}