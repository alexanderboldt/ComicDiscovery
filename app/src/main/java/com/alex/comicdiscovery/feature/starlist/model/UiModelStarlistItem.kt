package com.alex.comicdiscovery.feature.starlist.model

sealed class UiModelStarlistItem {
    data class Existing(val id: Long, val name: String) : UiModelStarlistItem()
    object New : UiModelStarlistItem()
}