package com.example.imperialassault

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool

object Sounds {

    // TODO please implement soundpool

    var soundplayer: MediaPlayer = MediaPlayer()


    var currentWeaponTypes = arrayListOf<Int>(0, 0)

    val default = 0
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

    var sPBuilder = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)


    var soundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(sPBuilder.build())
        .build()

    lateinit var sEPool: ArrayList<Int>

    fun sounEPool(context: Context) {
        sEPool = arrayListOf(
            soundPool.load(context, R.raw.equip, 1),
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
        )
    }

    fun equipSound(context: Context, equipSoundType: Int) {
        when (equipSoundType) {
            default -> {
                soundPool.play(
                    sEPool[default],
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

    fun attackSound(context: Context) {
        var whichSound = 0

        if (currentWeaponTypes[0] == 0) {
            whichSound = 1
        } else if (currentWeaponTypes[0] == 0) whichSound = 0
        else if (currentWeaponTypes[0] != 0 && currentWeaponTypes[1] != 0) whichSound =
            (Math.random() * 2).toInt()
        when (currentWeaponTypes[whichSound]) {
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

    fun damagedSound(context: Context, whichWeapon: Int) {
        when (whichWeapon) {
            shing -> {
                soundPool.play(
                    sEPool[slice],
                    1f, 1f, 1, 0, 1f)
            }
            equip_impact -> {
                soundPool.play(
                    sEPool[impact],
                    1f, 1f, 1, 0, 1f)
            }
            equip_gun -> {
                soundPool.play(
                    sEPool[gaster_blaster_master],
                    1f, 1f, 1, 0, 1f)
            }
        }
    }
}