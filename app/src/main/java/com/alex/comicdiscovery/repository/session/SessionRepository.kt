package com.alex.comicdiscovery.repository.session

import com.alex.comicdiscovery.repository.datastore.SessionDataStore
import com.alex.comicdiscovery.repository.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first

class SessionRepository(
    private val sessionDataStore: SessionDataStore,
    private val settingsDataStore: SettingsDataStore) {

    suspend fun init() {
        sessionDataStore
            .getUsername()
            .first()
            .also { settingsDataStore.init(it) }
    }

    // ----------------------------------------------------------------------------

    fun isUserLoggedIn() = sessionDataStore.isUserLoggedIn()

    suspend fun loginUser(username: String) {
        sessionDataStore.setUserLoggedIn(true)
        sessionDataStore.setUsername(username)
    }

    fun getUsername() = sessionDataStore.getUsername()

    // ----------------------------------------------------------------------------

    suspend fun clear() {
        settingsDataStore.clear()
        sessionDataStore.clear()
    }
}