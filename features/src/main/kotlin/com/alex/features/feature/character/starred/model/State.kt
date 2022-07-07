package com.alex.features.feature.character.starred.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alex.features.ui.components.UiModelCharacter

class State(initialContent: Content) {

    data class UiModelStarlist(val id: Long, val name: String)

    sealed interface Starlist {
        object NoListsAvailable : Starlist
        data class Starlists(val starlists: List<UiModelStarlist>) : Starlist
    }

    // ----------------------------------------------------------------------------

    sealed interface Content {
        data class Characters(val characters: List<UiModelCharacter>) : Content
        data class Message(val message: String) : Content
    }

    // ----------------------------------------------------------------------------

    var starlists: Starlist by mutableStateOf(Starlist.NoListsAvailable)
    var selectedStarlistId: Long by mutableStateOf(-1)
    var content: Content by mutableStateOf(initialContent)
}