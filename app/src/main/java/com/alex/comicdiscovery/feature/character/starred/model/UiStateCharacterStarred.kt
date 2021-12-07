package com.alex.comicdiscovery.feature.character.starred.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alex.comicdiscovery.ui.components.UiModelCharacter

class UiStateCharacterStarred(initialContent: Content) {

    data class UiModelStarlist(val id: Long, val name: String)

    sealed class Starlist {
        object NoListsAvailable : Starlist()
        data class Starlists(val starlists: List<UiModelStarlist>) : Starlist()
    }

    sealed class Content {
        data class Characters(val characters: List<UiModelCharacter>) : Content()
        data class Message(val message: String) : Content()
    }

    // ----------------------------------------------------------------------------

    var starlists: Starlist by mutableStateOf(Starlist.NoListsAvailable)
    var selectedStarlistIndex by mutableStateOf(0)
    var content: Content by mutableStateOf(initialContent)
}