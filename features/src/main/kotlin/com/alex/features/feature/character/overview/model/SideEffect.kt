package com.alex.features.feature.character.overview.model

sealed class SideEffect {
    data class DetailScreen(val id: Int) : SideEffect()
}