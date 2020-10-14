package com.alex.comicdiscovery.feature.base

import android.content.Context
import androidx.annotation.StringRes

open class ResourceProvider(private val context: Context) {
    open fun getString(@StringRes resource: Int) = context.getString(resource)
}