package com.alex.features.feature.character.overview.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alex.features.ui.components.UiModelBaseList

class State(initialContent: Content) {

    sealed interface Content {
        data class Items(val items: List<UiModelBaseList>) : Content
        data class Loading(val message: String) : Content
        data class Message(val message: String) : Content
    }

    // ----------------------------------------------------------------------------

    var query: String by mutableStateOf("")
    var content: Content by mutableStateOf(initialContent)
}