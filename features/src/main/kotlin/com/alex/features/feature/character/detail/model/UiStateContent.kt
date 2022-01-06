package com.alex.features.feature.character.detail.model

sealed class UiStateContent {
    data class Character(val character: UiModelCharacter) : UiStateContent()
    data class Loading(val message: String) : UiStateContent()
    data class Message(val message: String) : UiStateContent()
}