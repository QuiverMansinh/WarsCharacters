package com.example.imperialassault

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newButton.setOnClickListener({
            startActivity(Intent(this,Characters_list::class.java))
        })
        loadButton.setOnClickListener({
            startActivity(Intent(this,LoadScreen::class.java))
        })
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blastAnim = createAnimation("blast")
        impactAnim = createAnimation("impact")
        sliceAnim = createAnimation("slice")
        restAnim = createAnimation("rest")


    }
    companion object{
        var selectedCharacter:Character? = null
        var blastAnim: AnimationDrawable = AnimationDrawable()
        var impactAnim : AnimationDrawable = AnimationDrawable()
        var restAnim: AnimationDrawable = AnimationDrawable()
        var sliceAnim: AnimationDrawable = AnimationDrawable()

    }

    fun createAnimation(type: String): AnimationDrawable{

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels.toFloat()
        val width = displayMetrics.widthPixels.toFloat()

        val animation = AnimationDrawable()


        for(i in 0..9){
            var bitmap = getBitmap(this, "animations/" + type + "/" + type + "_" + +i + ".png")


            if (bitmap != null) {

                val bitmapWidth = width/(height-180)*bitmap.height
                val bitmapOffset =((bitmap.width-bitmapWidth)/2).toInt()

                bitmap = Bitmap.createBitmap(
                    bitmap,
                    bitmapOffset,
                    0,
                    bitmapWidth.toInt(),
                    bitmap.height
                )


                val frame = BitmapDrawable(resources, bitmap);
                if(type .equals("rest")) {
                    animation.addFrame(frame, 100)
                }

                else {
                    animation.addFrame(frame, 70)
                }
            }
        }
        animation.setOneShot(true);
        return animation;
    }

    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var bitmap:Bitmap? = null
        val options = BitmapFactory.Options()
        for(i in 1..32) {
            try {
                inputStream = assetManager.open(path)
                options.inSampleSize = i
                println(i)
                bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                break
            } catch (outOfMemoryError: OutOfMemoryError) {

                println("next size" + i)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) hideSystemUI()
//    }
//
//    private fun hideSystemUI() {
//        // Enables regular immersive mode.
//        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
//        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
//                // Set the content to appear under the system bars so that the
//                // content doesn't resize when the system bars hide and show.
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                // Hide the nav bar and status bar
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN)
//    }
//
//    // Shows the system bars by removing all the flags
//// except for the ones that make the content appear under the system bars.
//    private fun showSystemUI() {
//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//    }
}