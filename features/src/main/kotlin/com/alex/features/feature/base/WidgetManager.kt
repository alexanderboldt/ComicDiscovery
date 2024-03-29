package com.alex.features.feature.base

import android.content.Context
import android.widget.Toast

class WidgetManager(private val context: Context) {

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}