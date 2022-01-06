package com.alex.features.feature.character.detail.model

sealed class UiStateStarlist {
    object NoListsAvailable : UiStateStarlist()
    data class Starlists(val starlists: List<UiModelStarlist>) : UiStateStarlist()
}