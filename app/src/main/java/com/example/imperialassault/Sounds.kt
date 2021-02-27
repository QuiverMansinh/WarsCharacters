package com.example.imperialassault

import android.content.Context
import android.media.MediaPlayer

object Sounds {

    var soundplayer: MediaPlayer = MediaPlayer()
    var currentWeaponTypes = arrayListOf<Int>(0,0)

    fun weaponSound(context: Context, weaponSoundType: Int) {
        when (weaponSoundType) {
            1 -> {
                soundplayer = MediaPlayer.create(context, R.raw.shing)
                return soundplayer.start()
            }
            2 -> {
                soundplayer = MediaPlayer.create(context, R.raw.light_saber)
                return soundplayer.start()
            }
            3 -> {
                soundplayer = MediaPlayer.create(context, R.raw.electric)
                return soundplayer.start()
            }
            4 -> {
                soundplayer = MediaPlayer.create(context, R.raw.gun_loading)
                return soundplayer.start()
            }
            5 -> {
                soundplayer = MediaPlayer.create(context, R.raw.item_equip)
                return soundplayer.start()
            }
        }
    }
    fun impactSound(context: Context){
        var whichSound = 0
        if (currentWeaponTypes[0]==0){
            whichSound = 1
        }else if (currentWeaponTypes[0]==0) whichSound = 0
        else if (currentWeaponTypes[0]!=0 && currentWeaponTypes[1]!=0) whichSound =
            (Math.random()*2).toInt()
        when(currentWeaponTypes[whichSound]){
            1 -> {
                soundplayer = MediaPlayer.create(context, R.raw.stabby_stabby)
                return soundplayer.start()
            }
            2 -> {
                soundplayer = MediaPlayer.create(context, R.raw.lightsaber_stabby_stabby)
                return soundplayer.start()
            }
            3 -> {
                soundplayer = MediaPlayer.create(context, R.raw.electric)
                return soundplayer.start()
            }
            4 -> {
                soundplayer = MediaPlayer.create(context, R.raw.gaster_blaster_master)
                return soundplayer.start()
            }
            5 -> {
                soundplayer = MediaPlayer.create(context, R.raw.bit_punch)
                return soundplayer.start()
            }
        }
    }
    fun youGotHurt(context: Context,whichWeapon:Int){
        when(whichWeapon){
            1 -> {
                soundplayer = MediaPlayer.create(context, R.raw.stabby_stabby)
                return soundplayer.start()
            }
            2 -> {
                soundplayer = MediaPlayer.create(context, R.raw.electric)
                return soundplayer.start()
            }
            3 -> {
                soundplayer = MediaPlayer.create(context, R.raw.gaster_blaster_master)
                return soundplayer.start()
            }
        }
    }
}