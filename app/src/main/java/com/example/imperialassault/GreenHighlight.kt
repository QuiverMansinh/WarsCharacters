package com.example.imperialassault

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.widget.ImageView
import kotlin.concurrent.thread

class GreenHighlight(imageView: ImageView) {
    var anim = ValueAnimator()
    var imageView = imageView
    val colorsVal = arrayListOf<Int>(
        191,
        166
    )

    init {
        anim.duration = 1000
        anim.setEvaluator(ArgbEvaluator())
        anim.setIntValues(100, 0, 100)
        anim.repeatCount = Animator.DURATION_INFINITE.toInt()
        anim.addUpdateListener(ValueAnimator.AnimatorUpdateListener {
            imageView.setColorFilter(
                Color.argb(
                    255,
                    64 + colorsVal[0] * anim.getAnimatedValue() as Int / 100,
                    255,
                    89 + colorsVal[1] * anim.getAnimatedValue() as Int / 100
                ),
                PorterDuff.Mode.MULTIPLY
            )
        })
    }

    fun active() {
        anim.start()
    }

    fun disabled() {
        anim.cancel()
        imageView.setColorFilter(
            Color.argb(
                255,
                255,
                255,
                255
            ),
            PorterDuff.Mode.MULTIPLY
        )
    }
}