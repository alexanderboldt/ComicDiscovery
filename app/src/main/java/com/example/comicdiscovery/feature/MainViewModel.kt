package com.example.comicdiscovery.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicdiscovery.repository.SearchRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        viewModelScope.launch {
            val result = SearchRepository().getSearch("Hal Jordan")
            println(result)
        }
    }
}