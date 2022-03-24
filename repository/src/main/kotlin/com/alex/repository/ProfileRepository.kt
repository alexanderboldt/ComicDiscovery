package com.alex.repository

import android.content.Context
import com.alex.datastore.profile.ProfileDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

/**
 * Manages the profile settings.
 *
 * @param context An instance of [Context]
 * @param profileDataStore An instance of [ProfileDataStore]
 */
class ProfileRepository(
    private val context: Context,
    private val profileDataStore: ProfileDataStore
) {

    /**
     * Returns an already saved avatar, if available.
     *
     * @return Returns a [File] in a [Flow].
     */
    fun getAvatar() = flow {
        profileDataStore.getAvatar()?.let { File(it) }.also { emit(it) }
    }.flowOn(Dispatchers.IO)

    /**
     * Saves the assigned file in the app-storage.
     *
     * @param file An avatar as [File].
     *
     * @return Returns the saved avatar as a [File] in a [Flow].
     */
    fun saveAvatar(file: File) = flow {
        profileDataStore.setAvatar(file.path)
        file
            .copyTo(context.filesDir.resolve("avatar_${System.currentTimeMillis()}"), true)
            .also { emit(it) }
    }.flowOn(Dispatchers.IO)
}