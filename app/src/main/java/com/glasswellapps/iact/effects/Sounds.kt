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

    var sPBuilder = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)


    var soundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(sPBuilder.build())
        .build()

    lateinit var sEPool: ArrayList<Int>
    var loaded = false

    fun sounEPool(context: Context) {
        if(!loaded) {
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
                soundPool.load(context, R.raw.negative, 1)
            )
            loaded =true
        }
    }

    fun play(soundId : Int){
        var volume = 1f
        if(MainActivity.selectedCharacter != null) {
            volume= MainActivity.selectedCharacter!!.soundEffectsSetting
        }
        soundPool.play(soundId, volume, volume, 1, 0, 1f)
    }

    fun selectSound(){
       play(sEPool[select])
    }
    fun negativeSound(){
        play(sEPool[negative])
    }
    fun strainSound(){
        play(sEPool[strain])
    }

    fun equipSound(context: Context, equipSoundType: Int) {
        when (equipSoundType) {
            select -> { play(sEPool[select]) }
            lightSaber -> {
                play(sEPool[lightSaber]) }
            electric -> { play(sEPool[electric]) }
            shing -> {
                play(sEPool[shing]) }
            equip_gun -> {
                play(sEPool[equip_gun]) }
            equip_impact -> { play(sEPool[equip_impact]) }
            equip_armor -> {  play(sEPool[equip_armor]) }
            equip_clothing -> { play(sEPool[equip_clothing]) }
            droid -> {  play(sEPool[droid])
            }
        }
    }

    fun attackSound() {
        val character = MainActivity.selectedCharacter!!
        var whichSound = 0
        if(character.weapons.size>0) {
            var whichWeapon =(Math.random()*character.weapons.size).toInt()
            var index = character.weapons[whichWeapon]
            whichSound = Items.itemsArray!![index].soundType

        }

        when (whichSound) {
            0->{
                play(sEPool[impact])
            }
            shing -> {
                play(sEPool[slice])
            }
            lightSaber -> {
                play(sEPool[lightsaber_stabby_stabby])
            }
            electric -> {
                play(sEPool[electric])
            }
            equip_gun -> { play(sEPool[gaster_blaster_master]) }
            equip_impact -> {
                play(sEPool[impact])
            }
        }
    }

    fun damagedSound(context: Context, damageType: Int) {
        when (damageType) {
            slice -> {
                play(sEPool[slice])
            }
            impact -> {
                play(sEPool[impact]) }
            gaster_blaster_master -> {
                play(sEPool[gaster_blaster_master])
            }
        }
    }

    fun conditionSound(conditionType:Int){
        selectSound()
    }

    fun movingSound(){
        play(sEPool[moving])
    }
}