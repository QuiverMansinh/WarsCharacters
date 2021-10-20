package com.glasswellapps.iact.effects

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.glasswellapps.iact.inventory.Items
import com.glasswellapps.iact.MainActivity
import com.glasswellapps.iact.R

object Sounds {


    val select = 0
    val lightSaber = 1
    val electric = 2
    val shing = 3
    val equip_gun = 4
    val equip_impact = 5
    val equip_armor = 6
    val equip_clothing = 7
    val droid = 8
    val slice = 9
    val lightsaber_stabby_stabby = 10
    val gaster_blaster_master = 11
    val impact = 12
    val strain = 13
    val moving = 14
    val negative = 15
    val special = 16
    val crate = 17

    var sPBuilder = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)


    var soundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(sPBuilder.build())
        .build()

    var sEPool: ArrayList<Int>? = null
    var loaded = false

    fun sounEPool(context: Context) {

            sEPool = arrayListOf(
                soundPool.load(context, R.raw.select, 1),
                soundPool.load(context, R.raw.light_saber, 1),
                soundPool.load(context, R.raw.electric, 1),
                soundPool.load(context, R.raw.shing, 1),
                soundPool.load(context, R.raw.equip_gun, 1),
                soundPool.load(context, R.raw.equip_impact, 1),
                soundPool.load(context, R.raw.equip_armor, 1),
                soundPool.load(context, R.raw.equip_clothing, 1),
                soundPool.load(context, R.raw.droid, 1),
                soundPool.load(context, R.raw.slice, 1),
                soundPool.load(context, R.raw.lightsaber_stabby_stabby, 1),
                soundPool.load(context, R.raw.gaster_blaster_master, 1),
                soundPool.load(context, R.raw.impact, 1),
                soundPool.load(context, R.raw.strain, 1),
                soundPool.load(context, R.raw.moving, 1),
                soundPool.load(context, R.raw.negative, 1),
                soundPool.load(context, R.raw.special, 1),
                soundPool.load(context, R.raw.crate, 1)
            )

        if(sEPool!=null) {
            for (i in 0..sEPool!!.size - 1) {
                System.out.println(sEPool!![i]);
            }
        }
    }

    fun reset(context: Context){
        if(sEPool == null) {
            sounEPool(context);
        }
    }



    fun play(soundId : Int){
        var volume = 1f
        if(MainActivity.selectedCharacter != null) {
            volume= MainActivity.selectedCharacter!!.soundEffectsSetting
        }
        if(sEPool!=null) {
            soundPool.play(sEPool!![soundId], volume, volume, 1, 0, 1f)
        }
    }

    fun selectSound(){
        try {
            play(select)
        }
        catch (e:Exception){
            System.out.println(e)

        }
    }
    fun negativeSound(){
        try {
            play(sEPool!![negative])
        }
        catch (e:Exception){
            System.out.println(e)

        }
    }
    fun strainSound(){
        try{
            play(strain)
        }
        catch (e:Exception){
            System.out.println(e)

        }
    }

    fun equipSound(context: Context, equipSoundType: Int) {
        try {
            when (equipSoundType) {
                select -> {
                    play(select)
                }
                lightSaber -> {
                    play(lightSaber)
                }
                electric -> {
                    play(electric)
                }
                shing -> {
                    play(shing)
                }
                equip_gun -> {
                    play(equip_gun)
                }
                equip_impact -> {
                    play(equip_impact)
                }
                equip_armor -> {
                    play(equip_armor)
                }
                equip_clothing -> {
                    play(equip_clothing)
                }
                droid -> {
                    play(droid)
                }
            }
        }
        catch (e:Exception){
            System.out.println(e)

        }
    }

    fun attackSound() {
        try{
            val character = MainActivity.selectedCharacter!!
            var whichSound = 0
            if(character.weapons.size>0) {
                var whichWeapon =(Math.random()*character.weapons.size).toInt()
                var index = character.weapons[whichWeapon]
                whichSound = Items.itemsArray!![index].soundType

            }

            when (whichSound) {
                0->{
                    play(impact)
                }
                shing -> {
                    play(slice)
                }
                lightSaber -> {
                    play(lightsaber_stabby_stabby)
                }
                electric -> {
                    play(electric)
                }
                equip_gun -> { play(gaster_blaster_master) }
                equip_impact -> {
                    play(impact)
                }
            }
        }
        catch (e:Exception){
            System.out.println(e)

        }
    }

    fun damagedSound(context: Context, damageType: Int) {
        try {
            when (damageType) {
                slice -> {
                    play(slice)
                }
                impact -> {
                    play(impact)
                }
                gaster_blaster_master -> {
                    play(gaster_blaster_master)
                }
            }
        }
        catch (e:Exception){
            System.out.println(e)

        }
    }

    fun conditionSound(conditionType:Int){
        selectSound()
    }

    fun movingSound(){
        try{
            play(moving)
        }
        catch (e:Exception){
            System.out.println(e)
        }
    }

    fun specialSound(){
        try{
            play(special)
        }
        catch (e:Exception){
            System.out.println(e)
        }
    }

    fun crateSound(){
        try{
            play(crate)
        }
        catch (e:Exception){
            System.out.println(e)
        }
    }
}