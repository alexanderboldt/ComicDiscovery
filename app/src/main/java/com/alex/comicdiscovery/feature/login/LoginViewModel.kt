package com.alex.comicdiscovery.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.repository.session.SessionRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    private val _mainScreenState = LiveEvent<Unit>()
    val mainScreenState: LiveData<Unit> = _mainScreenState

    private val _toastState = MutableLiveData<String>()
    val toastState: LiveData<String> = _toastState

    // ----------------------------------------------------------------------------

    fun onClickLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            sessionRepository.loginUser(username)
            _mainScreenState.postValue(Unit)
        }
    }
}