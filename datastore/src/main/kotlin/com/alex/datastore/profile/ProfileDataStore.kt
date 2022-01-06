package com.alex.datastore.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.alex.datastore.Profile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal val Context.profileDataStore: DataStore<Profile> by dataStore("profile.proto", ProfileSerializer)

class ProfileDataStore(private val context: Context) {

    suspend fun getAvatar(): String? = context
        .profileDataStore
        .data
        .map { profile -> profile.avatar.takeIf { it.isNotBlank() } }
        .first()

    suspend fun setAvatar(avatar: String) {
        context.profileDataStore.updateData {
            it.toBuilder().setAvatar(avatar).build()
        }
    }
}