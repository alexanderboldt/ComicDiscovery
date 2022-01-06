package com.alex.features.feature.profile.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File

class State {
    var avatar: File? by mutableStateOf(null)
}