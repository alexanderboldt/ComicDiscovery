package com.alex.comicdiscovery.feature.character.overview.model

sealed class UiEventCharacterOverview {
    data class DetailScreen(val id: Int) : UiEventCharacterOverview()
}