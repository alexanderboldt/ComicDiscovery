package com.alex.comicdiscovery.feature.character.detail.model

sealed class UiEventCharacterDetail {
    data class Message(val message: String) : UiEventCharacterDetail()
}