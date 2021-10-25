package com.alex.comicdiscovery.feature.character.detail.model

import com.alex.comicdiscovery.feature.base.UiEvent

sealed class UiEventCharacterDetail : UiEvent {
    data class Message(val message: String) : UiEventCharacterDetail()
}