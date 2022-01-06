package com.alex.repository

import android.content.Context
import com.alex.database.ComicDiscoveryDatabase

object Repository {

    fun init(context: Context) {
        ComicDiscoveryDatabase.init(context)
    }
}