package com.alex.comicdiscovery.repository.models

data class RpModelCharacterOverview(
    val id: Int,
    val name: String,
    val realName: String?,
    val image: RpModelImage
)