package com.example.imperialassault

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setContentView(R.layout.activity_main)
        newButton.setOnClickListener {
            val intent = Intent(this, Characters_list::class.java)
                startActivity(intent)
        }
        loadButton.setOnClickListener {
            val intent = Intent(this, LoadScreen::class.java)
                startActivity(intent)
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(selectedCharacter!=null) {
            //wipeSelectedCharacter()
        }

        if(blastAnim == null) {
            blastAnim = createAnimation("blast")
        }
        if(impactAnim == null) {
            impactAnim = createAnimation("impact")
        }
        if(sliceAnim == null) {
            sliceAnim = createAnimation("slice")
        }
    }

    fun setAnimation(){
        /*if(Build.VERSION.SDK_INT>20) {
            val fade = android.transition.Fade()
            fade.setDuration(100);
            getWindow().setExitTransition(fade);
            getWindow().setEnterTransition(fade);
        }*/
    }

    companion object{
        var selectedCharacter:Character? = null
        var blastAnim: AnimationDrawable? = null
        var impactAnim : AnimationDrawable? = null
        var restAnim: AnimationDrawable? = null
        var sliceAnim: AnimationDrawable? = null

        var data:List<CharacterData>? = null
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
                break
            }
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun wipeSelectedCharacter(){

        selectedCharacter!!.currentImage!!.recycle()
        for (i in 0..8) {
            if (MainActivity.selectedCharacter!!.xpCardImages[i] != null) {
                selectedCharacter!!.xpCardImages!![i].recycle()
            }

        }
        if (selectedCharacter!!.power != null) {
            selectedCharacter!!.power!!.recycle()
            selectedCharacter!!.power_wounded!!.recycle()
        }
        if (selectedCharacter!!.portraitImage != null) {
            selectedCharacter!!.portraitImage = null
        }
        selectedCharacter = null
    }

}