package com.example.imperialassault

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri

object Sounds {

    // TODO please implement soundpool

    var soundplayer: MediaPlayer = MediaPlayer()


    var currentWeaponTypes = arrayListOf<Int>(0, 0)

    val default = 0
    val lightSaber = 1
    val electric = 2
    val slice = 3
    val blaster = 4
    val impact = 5
    val armor = 6
    val clothing = 7
    val astromech = 8

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
            soundPool.load(context, R.raw.electric, 1),
            soundPool.load(context, R.raw.gaster_blaster_master, 1),
            soundPool.load(context, R.raw.impact, 1),
        )
    }

    fun equipSound(context: Context, equipSoundType: Int) {
        when (equipSoundType) {
            default -> {
                soundPool.play(
                    sEPool[0],
                1f, 1f, 1, 0, 1f)
            }
            lightSaber -> {
                soundPool.play(
                    sEPool[1],
                    1f, 1f, 1, 0, 1f)
            }
            electric -> {
                soundPool.play(
                    sEPool[2],
                    1f, 1f, 1, 0, 1f)
            }
            slice -> {
                soundPool.play(
                    sEPool[3],
                    1f, 1f, 1, 0, 1f)
            }
            blaster -> {
                soundPool.play(
                    sEPool[4],
                    1f, 1f, 1, 0, 1f)
            }
            impact -> {
                soundPool.play(
                    sEPool[5],
                    1f, 1f, 1, 0, 1f)
            }
            armor -> {
                soundPool.play(
                    sEPool[6],
                    1f, 1f, 1, 0, 1f)
            }
            clothing -> {
                soundPool.play(
                    sEPool[7],
                    1f, 1f, 1, 0, 1f)
            }
            astromech -> {
                soundPool.play(
                    sEPool[8],
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

    fun damagedSound(context: Context, whichWeapon: Int) {
        when (whichWeapon) {
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