package com.glasswellapps.iact

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.activity_characters_list.*

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
            findViewById(R.id.imageViewTress)

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

        if(CreateScreen.customCharacter != null){
            customCharacter.visibility = View.VISIBLE;
        }
        else{
            customCharacter.visibility = View.INVISIBLE;
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        characterList.animate().alpha(1f)
        var sortedCharArray = arrayListOf<View>(
            findViewById(R.id.imageViewBiv),
            findViewById(R.id.imageViewDiala),
            findViewById(R.id.imageViewSaska),
            findViewById(R.id.imageViewGideon),
            findViewById(R.id.imageViewJarrod),
            findViewById(R.id.imageViewVinto),
            findViewById(R.id.imageViewMak),
            findViewById(R.id.imageViewLoku),
            findViewById(R.id.imageViewDrok),
            findViewById(R.id.imageViewShyla),
            findViewById(R.id.imageViewJyn),
            findViewById(R.id.imageViewVerena),
            findViewById(R.id.imageViewOnar),
            findViewById(R.id.imageViewDavith),
            findViewById(R.id.imageViewMHD),
            findViewById(R.id.imageViewGaar),
            findViewById(R.id.imageViewFenn),
            findViewById(R.id.imageViewCT),
            findViewById(R.id.imageViewKo),
            findViewById(R.id.imageViewMurne),
            findViewById(R.id.imageViewTress),
            findViewById(R.id.customCharacter)
        )
        if(hasFocus) {
            for (i in 0 until sortedCharArray.size) {
                println("ANIMATED LOAD")
                var anim = ObjectAnimator.ofFloat(
                    sortedCharArray[i],
                    "translationX", -(i.toFloat()/2 + 1) *sortedCharArray[i].width.toFloat(),
                    0f
                )
                anim.duration = ((i.toFloat()/2 + 1)  * 150).toLong()

                println(""+anim.duration + " " + anim.values[0])

                anim.start()

            }
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

    //Load layout for selected character (pointing out it's tag to note which assets to load)

    fun onSelect(view: View) {
        Sounds.selectSound()
        for(i in 0..charactersImage.count()-1){
            if(charactersImage[i] != view){
                charactersImage[i].alpha = 0f
            }
        }
        if(Loaded.getCharacter() != null) {
            wipeSelectedCharacter()
        }
        val intent = Intent(this, CharacterScreen::class.java)
        intent.putExtra("CharacterName", view.tag.toString())
        intent.putExtra("Load", false)


        startActivity(intent);
        finish()

    }

    fun onSelectCustom(view: View) {
        Sounds.selectSound()
        for(i in 0..charactersImage.count()-1){

                charactersImage[i].alpha = 0f
        }
        if(Loaded.getCharacter() != null) {
            wipeSelectedCharacter()
        }
        val intent = Intent(this, CharacterScreen::class.java)
        intent.putExtra("CharacterName", "custom")
        intent.putExtra("Load", false)


        startActivity(intent);
        finish()

    }



    fun wipeSelectedCharacter(){

        Loaded.getCharacter().currentImage!!.recycle()
        for (i in 0..Loaded.getCharacter().xpCardImages.size-1) {
            if (Loaded.getCharacter().xpCardImages[i] != null) {
                Loaded.getCharacter().xpCardImages!![i].recycle()
            }
        }
        if (Loaded.getCharacter().power != null) {
            Loaded.getCharacter().power!!.recycle()
            Loaded.getCharacter().power_wounded!!.recycle()
        }
        if (Loaded.getCharacter().portraitImage != null) {
            Loaded.getCharacter().portraitImage = null
        }
        Loaded.setCharacter(null)
    }
}