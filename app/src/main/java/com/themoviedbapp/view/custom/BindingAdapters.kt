package com.themoviedbapp.view.custom

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.themoviedbapp.extension.glideLoadImage

@BindingAdapter("loadImage")
fun loadImage(img: ImageView, url: String?) = url?.run {
    glideLoadImage(img.context, url, img, 0)
}