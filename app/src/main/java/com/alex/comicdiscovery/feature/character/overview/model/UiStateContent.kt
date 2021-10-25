package com.alex.comicdiscovery.feature.character.overview.model

import com.alex.comicdiscovery.ui.components.UiModelCharacter

sealed class UiStateContent {
    data class Characters(val characters: List<UiModelCharacter>) : UiStateContent()
    data class Loading(val message: String) : UiStateContent()
    data class Message(val message: String) : UiStateContent()
}