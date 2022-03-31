package com.alex.features.feature.character.detail.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class State(initialContent: Content) {

    data class UiModelCharacter(
        val imageUrl: String,
        val name: String,
        val summary: String,
        val realName: String,
        val aliases: String,
        val gender: String,
        val birth: String,
        val origin: String,
        val powers: String
    )

    sealed class Content {
        data class Character(val character: UiModelCharacter) : Content()
        data class Loading(val message: String) : Content()
        data class Message(val message: String) : Content()
    }

    // ----------------------------------------------------------------------------

    data class UiModelStarlist(val id: Long, val name: String, val isChecked: Boolean)

    sealed class Starlist {
        object NoListsAvailable : Starlist()
        data class Starlists(val starlists: List<UiModelStarlist>) : Starlist()
    }

    // ----------------------------------------------------------------------------

    var content: Content by mutableStateOf(initialContent)
    var starlist: Starlist by mutableStateOf(Starlist.NoListsAvailable)
    var isStarlistLoading: Boolean by mutableStateOf(false)
}