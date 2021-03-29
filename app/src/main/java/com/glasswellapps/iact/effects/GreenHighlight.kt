package com.glasswellapps.iact.effects

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.widget.ImageView

class GreenHighlight(var imageView: ImageView, var context: Context, var resources: Resources) {
    var anim = ValueAnimator.ofInt(0,255,0)
    var bitmap:Bitmap? = null

    init {
        anim.duration = 2000
        anim.repeatCount = Animator.DURATION_INFINITE.toInt()
        imageView.background.setColorFilter(Color.argb(0, 255,
            255, 255), PorterDuff.Mode.MULTIPLY)
    }

    fun active() {
        //bitmap = Blurry.with(context).radius(5) .capture(imageView).get()
        anim.addUpdateListener(ValueAnimator.AnimatorUpdateListener {
            //imageView.background = BitmapDrawable(resources,bitmap)
            imageView.background.setColorFilter(Color.argb(anim.getAnimatedValue() as Int, 255,
                255, 255), PorterDuff.Mode.MULTIPLY)
        })
        anim.start()
    }

    fun disabled() {
        anim.cancel()
        //imageView.background = null
        imageView.background.setColorFilter(Color.argb(0, 255,
            255, 255), PorterDuff.Mode.MULTIPLY)
    }
}