package com.example.comicdiscovery.feature.character.detail.models

sealed class ContentState {
    data class CharacterState(val character: Character) : ContentState()
    data class MessageState(val message: String) : ContentState()
}