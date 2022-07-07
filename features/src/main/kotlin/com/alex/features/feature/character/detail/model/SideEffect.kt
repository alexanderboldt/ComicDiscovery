package com.alex.features.feature.character.detail.model

sealed interface SideEffect {
    data class Message(val message: String) : SideEffect
}