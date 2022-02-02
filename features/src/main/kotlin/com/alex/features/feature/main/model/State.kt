package com.alex.features.feature.main.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class State {

    enum class UiModelTheme {
        SYSTEM,
        LIGHT,
        DARK
    }

    // ----------------------------------------------------------------------------

    var theme: UiModelTheme by mutableStateOf(UiModelTheme.SYSTEM)
}