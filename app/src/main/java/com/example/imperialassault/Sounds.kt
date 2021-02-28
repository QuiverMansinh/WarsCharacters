package com.example.imperialassault

import android.content.Context
import android.media.MediaPlayer

object Sounds {

    // TODO please implement soundpool
    var soundplayer: MediaPlayer = MediaPlayer()


    var currentWeaponTypes = arrayListOf<Int>(0,0)

    val default = 0
    val lightSaber = 1
    val electric = 2
    val slice = 3
    val blaster = 4
    val impact = 5
    val armor = 6
    val clothing = 7
    val astromech = 8

    fun equipSound(context: Context, equipSoundType: Int) {
        when (equipSoundType) {
            default ->{
                soundplayer = MediaPlayer.create(context, R.raw.equip)
                return soundplayer.start()
            }
            lightSaber -> {
                soundplayer = MediaPlayer.create(context, R.raw.light_saber)
                return soundplayer.start()
            }
            electric -> {
                soundplayer = MediaPlayer.create(context, R.raw.electric)
                return soundplayer.start()
            }
            slice -> {
                soundplayer = MediaPlayer.create(context, R.raw.shing)
                return soundplayer.start()
            }
            blaster -> {
                soundplayer = MediaPlayer.create(context, R.raw.equip_gun)
                return soundplayer.start()
            }
            impact -> {
                soundplayer = MediaPlayer.create(context, R.raw.equip_impact)
                return soundplayer.start()
            }
            armor -> {
                soundplayer = MediaPlayer.create(context, R.raw.equip_armor)
                return soundplayer.start()
            }
            clothing -> {
                soundplayer = MediaPlayer.create(context, R.raw.equip_clothing)
                return soundplayer.start()
            }
            astromech -> {
                soundplayer = MediaPlayer.create(context, R.raw.droid)
                return soundplayer.start()
            }
        }
    }
    fun attackSound(context: Context){
        var whichSound = 0

        if (currentWeaponTypes[0]==0){
            whichSound = 1
        }
        else if (currentWeaponTypes[0]==0) whichSound = 0
        else if (currentWeaponTypes[0]!=0 && currentWeaponTypes[1]!=0) whichSound = (Math.random()*2).toInt()
        when(currentWeaponTypes[whichSound]){
            slice -> {
                soundplayer = MediaPlayer.create(context, R.raw.slice)
                return soundplayer.start()
            }
            lightSaber -> {
                soundplayer = MediaPlayer.create(context, R.raw.lightsaber_stabby_stabby)
                return soundplayer.start()
            }
            electric -> {
                soundplayer = MediaPlayer.create(context, R.raw.electric)
                return soundplayer.start()
            }
            blaster -> {
                soundplayer = MediaPlayer.create(context, R.raw.gaster_blaster_master)
                return soundplayer.start()
            }
            impact -> {
                soundplayer = MediaPlayer.create(context, R.raw.impact)
                return soundplayer.start()
            }
        }
    }
    fun damagedSound(context: Context,whichWeapon:Int){
        when(whichWeapon){
            slice -> {
                soundplayer = MediaPlayer.create(context, R.raw.slice)
                return soundplayer.start()
            }
            impact -> {
                soundplayer = MediaPlayer.create(context, R.raw.impact)
                return soundplayer.start()
            }
            blaster -> {
                soundplayer = MediaPlayer.create(context, R.raw.gaster_blaster_master)
                return soundplayer.start()
            }
        }
    }
}