package com.alex.comicdiscovery.feature.character.detail.model

sealed class UiStateStarlist {
    object NoListsAvailable : UiStateStarlist()
    data class Starlists(val starlists: List<UiModelStarlist>) : UiStateStarlist()
}