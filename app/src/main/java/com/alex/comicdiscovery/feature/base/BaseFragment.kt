package com.alex.comicdiscovery.feature.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {

    /**
     * Convenient observe-function.
     */
    fun <T> LiveData<T>.observe(observer: (t: T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { data ->
            observer(data)
        })
    }
}