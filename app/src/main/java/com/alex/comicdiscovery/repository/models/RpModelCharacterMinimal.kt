package com.alex.comicdiscovery.repository.models

data class RpModelCharacterMinimal(
    val id: Int,
    val name: String,
    val realName: String?,
    val smallImageUrl: String
)