package com.alex.features.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var selectNavigationIndex: Int by mutableStateOf(0)
        private set

    // ----------------------------------------------------------------------------

    fun onClickNavigationItem(index: Int) {
        selectNavigationIndex = index
    }
}