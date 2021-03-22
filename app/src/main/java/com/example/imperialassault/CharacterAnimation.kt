package com.example.imperialassault

import android.animation.ObjectAnimator
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlin.random.Random

class CharacterAnimation (val screen: CharacterScreen) {
    //endregion
    //************************************************************************************************************
    //region Animations
    //************************************************************************************************************


    private fun playDamageAnim() {
        if (screen.animateDamage) {
            val animType = Math.random();
            if (animType < 0.3) {
                Sounds.damagedSound(screen, Sounds.gaster_blaster_master)
                screen.damage_animation.setBackgroundDrawable(MainActivity.blastAnim)
                screen.damage_animation.visibility = FrameLayout.VISIBLE
                MainActivity.blastAnim!!.setVisible(true, true)
                MainActivity.blastAnim!!.start()

            } else if (animType < 0.6) {
                Sounds.damagedSound(screen, Sounds.slice)
                screen.damage_animation.setBackgroundDrawable(MainActivity.sliceAnim)
                screen.damage_animation.visibility = FrameLayout.VISIBLE
                MainActivity.sliceAnim!!.setVisible(true, true)
                MainActivity.sliceAnim!!.start()

            } else {
                Sounds.damagedSound(screen, Sounds.impact)
                screen.damage_animation.setBackgroundDrawable(MainActivity.impactAnim)
                screen.damage_animation.visibility = FrameLayout.VISIBLE
                MainActivity.impactAnim!!.setVisible(true, true)
                MainActivity.impactAnim!!.start()

            }

        }
        else{
            Sounds.damagedSound(screen, Sounds.impact)
        }
        hitAnim();
    }

    fun hitAnim(){
        var hitY = ObjectAnimator.ofFloat(
            screen.character_images, "translationY", 0f, 20f * Random
                .nextFloat(),
            0f, 20f * Random.nextFloat(), 0f, 20f * Random.nextFloat(), 0f
        )
        hitY.setDuration(300)
        hitY.start()

        var hitX = ObjectAnimator.ofFloat(
            screen.character_images, "translationX", 0f, -10f * Random
                .nextFloat(),
            0f, -10f * Random.nextFloat(), 0f, -10f * Random.nextFloat(), 0f
        )
        hitX.setDuration(300)
        hitX.start()
    }

    private fun playRestAnim() {
        if (screen.animateDamage) {
            var restAnim = ObjectAnimator.ofFloat(screen.rest_animation, "alpha", 0f, 1f, 0.75f, 0.25f, 0f)
            restAnim.duration = 200
            restAnim.start()
        }
    }

    //endregion
}