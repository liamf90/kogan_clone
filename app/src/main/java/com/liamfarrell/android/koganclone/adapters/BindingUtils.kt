package com.liamfarrell.android.koganclone.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageLoad")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl).into(imageView)
}