package com.alex.comicdiscovery.feature.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {

    /**
     * Convenient observe-function.
     */
    fun <T> LiveData<T>.observe(observer: (t: T) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            observer(data)
        }
    }

    /**
     * Hides the Keyboard.
     */
    fun hideKeyboard() {
        activity
            ?.getSystemService(Context.INPUT_METHOD_SERVICE)
            .run { this as InputMethodManager }
            .run { hideSoftInputFromWindow(view?.windowToken, 0) }
    }
}