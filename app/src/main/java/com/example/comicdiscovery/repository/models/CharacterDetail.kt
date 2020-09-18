package com.example.comicdiscovery.repository.models

import com.example.comicdiscovery.repository.api.models.Power

data class CharacterDetail(
    val name: String,
    val realName: String?,
    val image: Image,
    val gender: Int,
    val aliases: String?,
    val birth: String?,
    val powers: List<Power>,
    val origin: String
)