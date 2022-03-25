package com.alex.repository

import android.content.Context
import com.alex.filemanager.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

/**
 * Manages the profile settings.
 *
 * @param context An instance of [Context]
 * @param fileManager An instance of [FileManager]
 */
class ProfileRepository(
    private val context: Context,
    private val fileManager: FileManager
) {

    /**
     * Returns an already saved avatar, if available.
     *
     * @return Returns a [File] in a [Flow].
     */
    fun getAvatar() = flow {
        emit(fileManager.getAvatar())
    }.flowOn(Dispatchers.IO)

    /**
     * Saves the assigned file in the app-storage.
     *
     * @param file An avatar as [File].
     *
     * @return Returns the saved avatar as a [File] in a [Flow].
     */
    fun saveAvatar(file: File) = flow {
        emit(fileManager.saveAvatar(file))
    }.flowOn(Dispatchers.IO)
}