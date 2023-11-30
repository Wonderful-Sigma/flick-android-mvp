package com.wonderfulsigma.flick.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.wonderfulsigma.flick.R


fun View.slideUp(context: Context) {
    this.visibility = View.VISIBLE
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_slide_up)
    this.startAnimation(anim)
}

fun View.slideDown(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_slide_down)
    this.startAnimation(anim)
    this.visibility = View.INVISIBLE
}

fun View.slideListDown(context: Context) {
    this.visibility = View.VISIBLE
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_slide_list_down)
    this.startAnimation(anim)
}

fun View.slideListUp(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_slide_list_up)
    this.startAnimation(anim)
    this.visibility = View.GONE
}

fun View.fadeIn(context: Context) {
    this.visibility = View.VISIBLE
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_fade_in)
    this.startAnimation(anim)
}

fun View.fadeOut(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_fade_out)
    this.startAnimation(anim)
    this.visibility = View.INVISIBLE
}

fun View.fastFadeOut(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_fast_fade_out)
    this.startAnimation(anim)
    this.visibility = View.INVISIBLE
}

fun View.slideUpAndFadeIn(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_slide_up_and_fade_in)
    this.startAnimation(anim)
}

fun View.onButtonClick(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.animation_button_click)
    this.startAnimation(anim)
}