package com.alex.comicdiscovery.feature.character.overview.model

sealed class SideEffect {
    data class DetailScreen(val id: Int) : SideEffect()
}