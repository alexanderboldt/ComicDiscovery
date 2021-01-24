package com.alex.comicdiscovery.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.repository.session.SessionRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    private val _startFragmentState = LiveEvent<Int>()
    val startFragmentState: LiveData<Int> = _startFragmentState

    // ----------------------------------------------------------------------------

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sessionRepository
                .isUserLoggedIn()
                .collect { isUserLoggedIn ->
                    _startFragmentState.postValue(if (isUserLoggedIn) R.id.homeFragment else R.id.loginFragment)
                }
        }
    }
}