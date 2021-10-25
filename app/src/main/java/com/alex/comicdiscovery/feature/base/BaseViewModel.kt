package com.alex.comicdiscovery.feature.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<T : UiEvent> : ViewModel() {

    private var _event = Channel<T>(Channel.RENDEZVOUS)
    val event = _event.receiveAsFlow()

    // ----------------------------------------------------------------------------

    protected suspend fun sendEvent(event: T) {
        _event.send(event)
    }
}