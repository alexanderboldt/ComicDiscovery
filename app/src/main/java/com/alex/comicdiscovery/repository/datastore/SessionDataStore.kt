package com.alex.comicdiscovery.repository.datastore

import android.content.Context
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.map

class SessionDataStore(context: Context) {

    private val dataStore = context.createDataStore("session_data_store")

    private val keyIsUserLoggedIn = preferencesKey<Boolean>("key_is_user_logged_in")
    private val keyUsername = preferencesKey<String>("key_username")

    // ----------------------------------------------------------------------------

    fun isUserLoggedIn() = dataStore.data.map { it[keyIsUserLoggedIn] ?: false }

    suspend fun setUserLoggedIn(isUserLoggedIn: Boolean) {
        dataStore.edit { it[keyIsUserLoggedIn] = isUserLoggedIn }
    }

    // ----------------------------------------------------------------------------

    fun getUsername() = dataStore.data.map { it[keyUsername]!! }

    suspend fun setUsername(username: String) {
        dataStore.edit { it[keyUsername] = username }
    }

    // ----------------------------------------------------------------------------

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}