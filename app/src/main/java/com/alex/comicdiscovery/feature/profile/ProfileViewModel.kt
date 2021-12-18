package com.alex.comicdiscovery.feature.profile

import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.feature.base.BaseViewModel
import com.alex.comicdiscovery.feature.profile.model.State
import com.alex.comicdiscovery.repository.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(private val profileRepository: ProfileRepository) : BaseViewModel<State, Unit>(State()) {

    init {
        viewModelScope.launch(Dispatchers.Main) {
            profileRepository.getAvatar().collect { targetFile ->
                state.avatar = targetFile
            }
        }
    }

    // ----------------------------------------------------------------------------

    fun onAvatarSelected(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            profileRepository.saveAvatar(file).collect { targetFile ->
                delay(300)
                state.avatar = targetFile
            }
        }
    }
}