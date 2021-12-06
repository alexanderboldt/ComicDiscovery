package com.alex.comicdiscovery.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.repository.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    var avatar: File? by mutableStateOf(null)
        private set

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.Main) {
            profileRepository.getAvatar().collect { targetFile ->
                avatar = targetFile
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onAvatarSelected(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            profileRepository.saveAvatar(file).collect { targetFile ->
                delay(300)
                avatar = targetFile
            }
        }
    }
}