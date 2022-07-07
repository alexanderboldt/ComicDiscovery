package com.alex.features.feature.character.overview.model

sealed interface SideEffect {
    data class DetailScreen(val id: Int) : SideEffect
}