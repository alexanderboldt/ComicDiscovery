package com.alex.repository

import android.content.Context
import com.alex.filemanager.FileManager
import com.alex.repository.util.flowIo
import kotlinx.coroutines.flow.Flow
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
    fun getAvatar() = flowIo {
        emit(fileManager.getAvatar())
    }

    /**
     * Saves the assigned file in the app-storage.
     *
     * @param file An avatar as [File].
     *
     * @return Returns the saved avatar as a [File] in a [Flow].
     */
    fun saveAvatar(file: File) = flowIo {
        emit(fileManager.saveAvatar(file))
    }
}