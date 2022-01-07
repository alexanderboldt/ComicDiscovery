package com.alex.features

import android.content.Context
import com.alex.repository.Repository

object Features {

    fun init(context: Context) {
        Repository.init(context)
    }
}