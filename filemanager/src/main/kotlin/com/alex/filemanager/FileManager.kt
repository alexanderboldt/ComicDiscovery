package com.alex.filemanager

import android.content.Context
import java.io.File

class FileManager(private val context: Context) {

    private val fileName = "avatar_%s"

    // ----------------------------------------------------------------------------

    suspend fun saveAvatar(file: File): File {
        context
            .filesDir
            .listFiles()
            ?.filter { it.isFile && it.name.startsWith(fileName.dropLast(2)) }
            ?.forEach { it.delete() }

        return file.copyTo(context.filesDir.resolve(fileName.format(System.currentTimeMillis())), true)
    }

    suspend fun getAvatar() = context
        .filesDir
        .listFiles()
        ?.find { it.isFile && it.name.startsWith(fileName.dropLast(2)) }
}