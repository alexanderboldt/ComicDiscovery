package com.alex.features.feature.character.detail.model

sealed class SideEffect {
    data class Message(val message: String) : SideEffect()
}