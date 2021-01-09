package com.example.imperialassault

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import kotlinx.android.synthetic.main.activity_character__view.*
import java.io.InputStream


class Character_view : AppCompatActivity() {
    var character:Character = Character();
    var blastAnim:AnimationDrawable = AnimationDrawable()
    var impactAnim :AnimationDrawable= AnimationDrawable()
    var restAnim:AnimationDrawable= AnimationDrawable()
    var sliceAnim:AnimationDrawable= AnimationDrawable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character__view)

        var load = false //intent.getBooleanExtra("Load",false)
        var characterName : String = intent.getStringExtra("CharacterName").toString()

        if(!load) {
            when (characterName) {
                "mak" -> {
                    character = Character_mak(this)
                }

            }


        }
        else{

        }
        name.setText("" + character.name);
        health.setText("" + character.health);
        endurance.setText("" + character.endurance);
        speed.setText("" + character.speed);

        when(character.defence_dice){
            "white" -> ImageViewCompat.setImageTintList(defence, ColorStateList.valueOf(Color.WHITE))
            "black" -> ImageViewCompat.setImageTintList(defence, ColorStateList.valueOf(Color.BLACK))
        }



        setDiceColor(strength1, character.strength[0]);
        setDiceColor(strength2, character.strength[1]);
        setDiceColor(strength3, character.strength[2]);

        setDiceColor(insight1, character.insight[0]);
        setDiceColor(insight2, character.insight[1]);
        setDiceColor(insight3, character.insight[2]);

        setDiceColor(tech1, character.tech[0]);
        setDiceColor(tech2, character.tech[1]);
        setDiceColor(tech3, character.tech[2]);

        character_image.setImageBitmap(character.getCharacterImage())
        background_image.setImageBitmap(character.getBackgroundImage(this))

        blastAnim = createAnimation("blast")
        impactAnim = createAnimation("impact")
        restAnim = createAnimation("rest")
        sliceAnim = createAnimation("slice")

        blast_animation.setBackgroundDrawable(blastAnim)
        blast_animation.visibility = FrameLayout.INVISIBLE
        slice_animation.setBackgroundDrawable(sliceAnim)
        slice_animation.visibility = FrameLayout.INVISIBLE
        impact_animation.setBackgroundDrawable(impactAnim)
        impact_animation.visibility = FrameLayout.INVISIBLE
        rest_animation.setBackgroundDrawable(restAnim)
        rest_animation.visibility = FrameLayout.INVISIBLE
    }


    fun setDiceColor(dice: ImageView, color: Char) {
        when(color){
            'B' -> ImageViewCompat.setImageTintList(dice, ColorStateList.valueOf(getResources().getColor(R.color.dice_blue, null)))
            'G' -> ImageViewCompat.setImageTintList(dice, ColorStateList.valueOf(getResources().getColor(R.color.dice_green, null)))
            'Y' -> ImageViewCompat.setImageTintList(dice, ColorStateList.valueOf(getResources().getColor(R.color.dice_yellow, null)))
            ' ' -> dice.visibility = ImageView.GONE
        }
    }

    fun playDamageAnim(){
        val animType = Math.random();
        if(animType<0.3){
            blast_animation.visibility = FrameLayout.VISIBLE
            blastAnim.setVisible(true, true)
            blastAnim.start()
        }
        else if(animType<0.6){
            slice_animation.visibility = FrameLayout.VISIBLE
            sliceAnim.setVisible(true, true)
            sliceAnim.start()
        }
        else{
            impact_animation.visibility = FrameLayout.VISIBLE
            impactAnim.setVisible(true, true)
            impactAnim.start()
        }
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
                val bitmapWidth = width/(height-128)*bitmap.width
                val bitmapOffset =((bitmap.width-bitmapWidth)/2).toInt()
                println(bitmapWidth)
                bitmap = Bitmap.createBitmap(bitmap, bitmapOffset, 0, bitmapWidth.toInt(), bitmap.height)
                val frame = BitmapDrawable(resources, bitmap);
                animation.addFrame(frame, 100)
            }
        }
        animation.setOneShot(true);


        return animation;
    }

    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open(path)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun onAddStrain(view: View) {
        if(character.strain < character.endurance) {
            character.strain++
            add_strain.setText("" + character.strain)
        }
        else{

        }
    }
    fun onMinusStrain(view: View) {
        if(character.strain >0) {
            character.strain--
            add_strain.setText("" + character.strain)
        }
        else{

        }
    }
    fun onAddDamage(view: View) {
        if(character.damage < character.health*2) {
            character.damage++
        }

        if(character.damage < character.health) {

            add_damage.setText("" + character.damage)
        }
        else if(character.damage < character.health*2){

            character.wounded = character.damage-character.health
            add_damage.setText("" + character.wounded)
            wounded.visibility = ImageView.VISIBLE
        }
        else{
            add_damage.setText("" + character.health)
            val slide = ObjectAnimator.ofFloat(character_image, "translationY",0f,0f,character_image.height.toFloat())
            slide.setDuration(1500)
            slide.start()
        }

        playDamageAnim()
    }
    fun onMinusDamage(view: View) {
        if(character.damage >0) {
            character.damage--
        }

        if(character.damage < character.health) {
            add_damage.setText("" + character.damage)
            wounded.visibility = ImageView.INVISIBLE
            character.wounded = 0;
        }
        else if(character.damage < character.health*2){
            character.wounded = character.damage-character.health
            add_damage.setText("" + character.wounded)
            val slide = ObjectAnimator.ofFloat(character_image, "translationY", 0f)
            slide.setDuration(500)
            slide.start()
        }

    }






}

