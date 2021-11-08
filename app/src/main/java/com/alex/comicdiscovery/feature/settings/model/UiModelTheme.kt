package com.alex.comicdiscovery.feature.settings.model

enum class UiModelTheme {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun valueOf(index: Int) = values()[index]
    }
}