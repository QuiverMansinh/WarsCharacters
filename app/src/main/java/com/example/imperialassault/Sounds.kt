package com.example.imperialassault

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import kotlin.math.roundToInt

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
                soundPool.load(context, R.raw.moving, 1)
            )
            loaded=true
        }
    }

    fun selectSound(){
        soundPool.play(sEPool[select], 1f, 1f, 1, 0, 1f)
    }
    fun strainSound(){
        soundPool.play(sEPool[strain], 1f, 1f, 1, 0, 1f)
    }

    fun equipSound(context: Context, equipSoundType: Int) {
        when (equipSoundType) {
            select -> {
                soundPool.play(
                    sEPool[select],
                1f, 1f, 1, 0, 1f)
            }
            lightSaber -> {
                soundPool.play(
                    sEPool[lightSaber],
                    1f, 1f, 1, 0, 1f)
            }
            electric -> {
                soundPool.play(
                    sEPool[electric],
                    1f, 1f, 1, 0, 1f)
            }
            shing -> {
                soundPool.play(
                    sEPool[shing],
                    1f, 1f, 1, 0, 1f)
            }
            equip_gun -> {
                soundPool.play(
                    sEPool[equip_gun],
                    1f, 1f, 1, 0, 1f)
            }
            equip_impact -> {
                soundPool.play(
                    sEPool[equip_impact],
                    1f, 1f, 1, 0, 1f)
            }
            equip_armor -> {
                soundPool.play(
                    sEPool[equip_armor],
                    1f, 1f, 1, 0, 1f)
            }
            equip_clothing -> {
                soundPool.play(
                    sEPool[equip_clothing],
                    1f, 1f, 1, 0, 1f)
            }
            droid -> {
                soundPool.play(
                    sEPool[droid],
                    1f, 1f, 1, 0, 1f)
            }
        }
    }

    fun attackSound() {
        val character = MainActivity.selectedCharacter!!
        var whichSound = 0
        if(character.weapons.size>0) {
            var whichWeapon =(Math.random()*character.weapons.size).toInt()
            var index = character.weapons[whichWeapon]
            if(character.weapons[whichWeapon]>=Items.meleeArray!!.size){
                index-=Items.meleeArray!!.size
                whichSound = Items.rangedArray!![index].soundType
            }
            else{
                whichSound = Items.meleeArray!![index].soundType
            }
        }

        when (whichSound) {
            0->{
                soundPool.play(
                    sEPool[impact],
                    1f, 1f, 1, 0, 1f)
            }
            shing -> {
                soundPool.play(
                    sEPool[slice],
                    1f, 1f, 1, 0, 1f)
            }
            lightSaber -> {
                soundPool.play(
                    sEPool[lightsaber_stabby_stabby],
                    1f, 1f, 1, 0, 1f)
            }
            electric -> {
                soundPool.play(
                    sEPool[electric],
                    1f, 1f, 1, 0, 1f)
            }
            equip_gun -> {
                soundPool.play(
                    sEPool[gaster_blaster_master],
                    1f, 1f, 1, 0, 1f)
            }
            equip_impact -> {
                soundPool.play(
                    sEPool[impact],
                    1f, 1f, 1, 0, 1f)
            }
        }
    }

    fun damagedSound(context: Context, damageType: Int) {
        when (damageType) {
            slice -> {
                soundPool.play(
                    sEPool[slice],
                    1f, 1f, 1, 0, 1f)
            }
            impact -> {
                soundPool.play(
                    sEPool[impact],
                    1f, 1f, 1, 0, 1f)
            }
            gaster_blaster_master -> {
                soundPool.play(
                    sEPool[gaster_blaster_master],
                    1f, 1f, 1, 0, 1f)
            }
        }
    }

    fun conditionSound(conditionType:Int){

    }

    fun movingSound(){
        soundPool.play(
            sEPool[moving],
            1f, 1f, 1, 0, 1f)
    }
}