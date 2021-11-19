package com.alex.comicdiscovery.feature.character.starred.model

sealed class UiStateStarlist {
    object NoListsAvailable : UiStateStarlist()
    data class Starlists(val starlists: List<UiModelStarlist>) : UiStateStarlist()
}