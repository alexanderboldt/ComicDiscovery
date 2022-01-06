package com.alex.datastore.settings.model

enum class DsModelTheme {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun find(ordinal: Int) = values()[ordinal]
    }
}