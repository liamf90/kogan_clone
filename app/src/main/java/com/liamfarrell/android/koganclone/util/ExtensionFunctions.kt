package com.liamfarrell.android.koganclone.util

import android.view.View
import android.view.animation.AnimationUtils
import com.liamfarrell.android.koganclone.R

fun View.zoomInAnimation(){
    val zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    startAnimation(zoomInAnimation)
}