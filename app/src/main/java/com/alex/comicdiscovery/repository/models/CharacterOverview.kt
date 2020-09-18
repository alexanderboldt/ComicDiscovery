package com.alex.comicdiscovery.repository.models

data class CharacterOverview(
    val id: Int,
    val name: String,
    val realName: String?,
    val image: Image
)