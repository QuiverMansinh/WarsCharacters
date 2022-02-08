package com.glasswellapps.iact.character

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.glasswellapps.iact.R
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlin.random.Random

class CharacterScreenAnimation (val characterScreen: CharacterScreen){
    private var damageAnimation: ImageView = characterScreen.damage_animation
    var character = characterScreen.character
    var character_images: FrameLayout = characterScreen.character_images
    var blastAnim: AnimationDrawable? = null
    var impactAnim: AnimationDrawable? = null
    var sliceAnim: AnimationDrawable? = null
    var lastAnim: AnimationDrawable? = null

    init{
        //rest_animation.setBackgroundDrawable(MainActivity.restAnim)
        //rest_animation.visibility = FrameLayout.INVISIBLE
        blastAnim = characterScreen.resources.getDrawable(R.drawable.blast) as AnimationDrawable
        impactAnim = characterScreen.resources.getDrawable(R.drawable.impact) as AnimationDrawable
        sliceAnim = characterScreen.resources.getDrawable(R.drawable.slice) as AnimationDrawable
    }

    private fun resetAnim() {
        lastAnim?.stop()
        lastAnim?.selectDrawable(0)
    }
    private fun playAnim(anim: AnimationDrawable){
        resetAnim()
        damageAnimation.setImageDrawable(anim)
        anim.start()
        lastAnim = anim
    }
    open fun playDamageAnim() {
        if (character.damageAnimSetting) {
            damageAnimation.visibility = View.VISIBLE
            val animType = Math.random()
            if (animType < 0.3) {
                Sounds.damagedSound(characterScreen, Sounds.gaster_blaster_master)
                if(blastAnim != null) {
                    playAnim(blastAnim!!)
                }
            } else if (animType < 0.6) {
                Sounds.damagedSound(characterScreen, Sounds.slice)
                if(sliceAnim != null) {
                    playAnim(sliceAnim!!)
                }
            } else {
                Sounds.damagedSound(characterScreen, Sounds.impact)
                if(impactAnim != null) {
                    playAnim(impactAnim!!)
                }
            }
        }
        else{
            Sounds.damagedSound(characterScreen, Sounds.impact)
        }
        hitAnim()
    }

    fun hitAnim(){
        var hitY = ObjectAnimator.ofFloat(
            character_images, "translationY", 0f, 20f * Random
                .nextFloat(),
            0f, 20f * Random.nextFloat(), 0f, 20f * Random.nextFloat(), 0f
        )
        hitY.duration = 300
        hitY.start()

        var hitX = ObjectAnimator.ofFloat(
            character_images, "translationX", 0f, -10f * Random
                .nextFloat(),
            0f, -10f * Random.nextFloat(), 0f, -10f * Random.nextFloat(), 0f
        )
        hitX.duration = 300
        hitX.start()
    }

    open fun playRestAnim() {
        if (character.damageAnimSetting) {
            var restAnim = ObjectAnimator.ofFloat(characterScreen.rest_animation, "alpha", 0f,
                1f, 0.75f, 0.25f,
                0f)
            restAnim.duration = 200
            restAnim.start()
        }
    }
}