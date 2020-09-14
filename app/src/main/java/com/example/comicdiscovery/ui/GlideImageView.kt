package com.example.comicdiscovery.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions

@SuppressLint("all")
class GlideImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ImageView(context, attrs) {

    fun load(url: String, requestOptions: RequestOptions? = null) {
        GlideApp
            .with(context)
            .apply { if (requestOptions != null) applyDefaultRequestOptions(requestOptions) }
            .load(url)
            .into(this)
    }
}