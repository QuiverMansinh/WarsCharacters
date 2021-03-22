package com.example.imperialassault

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import jp.wasabeef.blurry.Blurry
import kotlin.concurrent.thread

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