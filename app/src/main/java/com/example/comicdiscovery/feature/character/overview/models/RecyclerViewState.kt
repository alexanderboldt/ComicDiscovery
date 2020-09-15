package com.example.comicdiscovery.feature.character.overview.models

sealed class RecyclerViewState {
    data class CharacterState(val characters: List<Character>) : RecyclerViewState()
    data class MessageState(val message: String) : RecyclerViewState()
}