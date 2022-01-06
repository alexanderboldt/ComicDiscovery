package com.alex.features.feature.settings.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class State {

    enum class UiModelTheme {
        SYSTEM,
        LIGHT,
        DARK;

        companion object {
            fun valueOf(index: Int) = values()[index]
        }
    }

    // ----------------------------------------------------------------------------

    var theme: UiModelTheme by mutableStateOf(UiModelTheme.SYSTEM)
}