package com.alex.comicdiscovery.feature.character.overview.model

import com.alex.comicdiscovery.ui.components.UiModelBaseList

sealed class UiStateContent {
    data class Items(val items: List<UiModelBaseList>) : UiStateContent()
    data class Loading(val message: String) : UiStateContent()
    data class Message(val message: String) : UiStateContent()
}