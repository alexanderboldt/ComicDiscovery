package com.alex.comicdiscovery.feature.base

import android.content.res.Resources
import androidx.annotation.StringRes

/**
 * Provides resources outside of ui-components, like ViewModels.
 *
 * @param resources The Resources-instance.
 */
class ResourceProvider(private val resources: Resources) {
    fun getString(@StringRes resource: Int) = resources.getString(resource)
}