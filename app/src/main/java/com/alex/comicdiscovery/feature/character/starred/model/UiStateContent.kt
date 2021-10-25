package com.alex.comicdiscovery.feature.character.starred.model

import com.alex.comicdiscovery.ui.components.UiModelCharacter

sealed class UiStateContent {
    data class Characters(val characters: List<UiModelCharacter>) : UiStateContent()
    data class Message(val message: String) : UiStateContent()
}