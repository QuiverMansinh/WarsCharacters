package com.example.imperialassault

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Characters_list : AppCompatActivity() {
    var charactersImage = arrayListOf<ImageView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setContentView(R.layout.activity_characters_list)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //Characters list for List view to choose characters

        charactersImage = arrayListOf<ImageView>(
            findViewById(R.id.imageViewBiv),
            findViewById(R.id.imageViewGaar),
            findViewById(R.id.imageViewMak),
            findViewById(R.id.imageViewSaska),
            findViewById(R.id.imageViewDavith),
            findViewById(R.id.imageViewGideon),
            findViewById(R.id.imageViewMHD),
            findViewById(R.id.imageViewShyla),
            findViewById(R.id.imageViewDiala),
            findViewById(R.id.imageViewJyn),
            findViewById(R.id.imageViewMurne),
            findViewById(R.id.imageViewVerena),
            findViewById(R.id.imageViewFenn),
            findViewById(R.id.imageViewLoku),
            findViewById(R.id.imageViewOnar),
            findViewById(R.id.imageViewVinto),
            findViewById(R.id.imageViewKo),
            findViewById(R.id.imageViewJarrod),
            findViewById(R.id.imageViewDrok),
            findViewById(R.id.imageViewCT),
            findViewById(R.id.imageViewTress),
        )
        var allChSel = BitmapFactory.decodeResource(resources,R.drawable
            .allcharacterselect_21)
        allChSel = Bitmap.createScaledBitmap(allChSel,2048,683,false)

        //code bellow cuts out image  assets from one image sheet for Character list.

        var row = 6
        var col = 4
        var width = 2048/col
        var height = 683/row
        var buffer1 = 0

        for (r in 0..row-1){
            for (c in 0..col-1){
                if(buffer1 > 20) break
                var drawable = BitmapDrawable( Bitmap.createBitmap
                (allChSel,0+
                (width*c),0+(height*r),width-1,height-1))

                charactersImage[buffer1++].setImageDrawable(drawable)
            }
        }

        //TODO delete selected character on load
    }

    fun setAnimation(){
        /*if(Build.VERSION.SDK_INT>20) {
            val fade = android.transition.Fade()
            fade.setDuration(100);
            getWindow().setExitTransition(fade);
            getWindow().setEnterTransition(fade);
        }*/
    }

    //Load layout for selected character (pointing out it's tag to note which assets to load)

    fun onSelect(view: View) {
        for(i in 0..charactersImage.count()-1){
            if(charactersImage[i] != view){
                charactersImage[i].alpha = 0f
            }
        }
        if(MainActivity.selectedCharacter != null) {
            wipeSelectedCharacter()
        }
        val intent = Intent(this, CharacterScreen::class.java)
        intent.putExtra("CharacterName", view.tag.toString())
        intent.putExtra("Load", false)


        startActivity(intent);
        finish()

    }

    fun wipeSelectedCharacter(){

        MainActivity.selectedCharacter!!.currentImage!!.recycle()
        for (i in 0..8) {
            if (MainActivity.selectedCharacter!!.xpCardImages[i] != null) {
                MainActivity.selectedCharacter!!.xpCardImages!![i].recycle()
            }
        }
        if (MainActivity.selectedCharacter!!.power != null) {
            MainActivity.selectedCharacter!!.power!!.recycle()
            MainActivity.selectedCharacter!!.power_wounded!!.recycle()
        }
        if (MainActivity.selectedCharacter!!.portraitImage != null) {
            MainActivity.selectedCharacter!!.portraitImage = null
        }
        MainActivity.selectedCharacter = null
    }
}