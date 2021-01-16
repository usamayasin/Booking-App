package com.app.faisalmovers.mvvm.utils

import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import com.app.faisalmovers.R
import com.google.android.material.snackbar.Snackbar


fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.setActionTextColor(Color.WHITE)
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(Color.RED)
    snackbarView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up))
    snackbar.show()
}