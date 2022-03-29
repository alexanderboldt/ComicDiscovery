package com.alex.filemanager

import android.content.Context
import java.io.File

class FileManager(private val context: Context) {

    private val fileNamePrefix = "avatar_"

    // ----------------------------------------------------------------------------

    suspend fun saveAvatar(file: File): File {
        context
            .filesDir
            .listFiles()
            ?.filter { it.isFile && it.name.startsWith(fileNamePrefix) }
            ?.forEach { it.delete() }

        return file.copyTo(context.filesDir.resolve(fileNamePrefix + System.currentTimeMillis()), true)
    }

    suspend fun getAvatar() = context
        .filesDir
        .listFiles()
        ?.find { it.isFile && it.name.startsWith(fileNamePrefix) }
}