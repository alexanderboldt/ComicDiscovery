package com.alex.comicdiscovery.repository.file

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class FileRepository(private val context: Context) {

    fun getAvatar(): Flow<File?> {
        return flow {
            context
                .filesDir
                .resolve("avatar")
                .let { file -> if (file.exists()) file else null }
                .apply { emit(this) }
        }.flowOn(Dispatchers.IO)
    }

    fun saveAvatar(file: File): Flow<File> {
        return flow {
            val targetFile = file.copyTo(context.filesDir.resolve("avatar"), true)
            emit(targetFile)
        }.flowOn(Dispatchers.IO)
    }
}