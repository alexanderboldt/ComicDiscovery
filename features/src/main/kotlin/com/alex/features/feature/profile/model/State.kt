package com.alex.features.feature.profile.model

import androidx.compose.runtime.*
import java.io.File

class State {
    var avatar: File? by mutableStateOf(null, neverEqualPolicy())
}