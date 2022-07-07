package com.alex.features.feature.starlist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class State {

    sealed interface StarlistItem {
        data class Existing(val id: Long, val name: String) : StarlistItem
        object New : StarlistItem
    }

    // ----------------------------------------------------------------------------

    val starlists = mutableStateListOf<StarlistItem>()

    var starlistNameNew by mutableStateOf("")

    val isStarlistCreateButtonEnabled: Boolean
        get() = starlistNameNew.isNotBlank()
}