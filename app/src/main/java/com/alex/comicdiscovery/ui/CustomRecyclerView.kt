package com.alex.comicdiscovery.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.alex.comicdiscovery.databinding.ViewCustomRecyclerviewBinding

class CustomRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewCustomRecyclerviewBinding.inflate(LayoutInflater.from(context), this)

    // ----------------------------------------------------------------------------

    fun getRecyclerView() = binding.recyclerView

    // ----------------------------------------------------------------------------

    fun showItems() {
        binding.apply {
            recyclerView.isVisible = true
            contentLoadingProgressBar.isGone = true
            contentLoadingProgressBar.hide()
            textViewMessage.isGone = true
        }
    }

    fun showLoading(message: String) {
        binding.apply {
            recyclerView.isGone = true
            contentLoadingProgressBar.isVisible = true
            contentLoadingProgressBar.show()
            textViewMessage.isVisible = true
            textViewMessage.text = message
        }
    }

    fun showMessage(message: String) {
        binding.apply {
            recyclerView.isGone = true
            contentLoadingProgressBar.isGone = true
            contentLoadingProgressBar.hide()
            textViewMessage.isVisible = true
            textViewMessage.text = message
        }
    }
}