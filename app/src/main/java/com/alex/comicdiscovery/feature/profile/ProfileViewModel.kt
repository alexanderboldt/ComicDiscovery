package com.alex.comicdiscovery.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.repository.file.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(private val fileRepository: FileRepository) : ViewModel() {

    var avatar: File? by mutableStateOf(null)
        private set

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            fileRepository.getAvatar().collect { targetFile ->
                avatar = targetFile
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onAvatarSelected(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            fileRepository.saveAvatar(file).collect { targetFile ->
                avatar = null
                delay(10)
                avatar = targetFile
            }
        }
    }
}