package com.alex.comicdiscovery.feature.starlist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class State {

    sealed class UiModelStarlistItem {
        data class Existing(val id: Long, val name: String) : UiModelStarlistItem()
        object New : UiModelStarlistItem()
    }

    // ----------------------------------------------------------------------------

    val starlists = mutableStateListOf<UiModelStarlistItem>()

    var starlistNameNew by mutableStateOf("")

    val isStarlistCreateButtonEnabled: Boolean
        get() = starlistNameNew.isNotBlank()
}