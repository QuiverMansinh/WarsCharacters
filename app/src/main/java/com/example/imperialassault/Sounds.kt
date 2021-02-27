package com.example.imperialassault

import android.content.Context
import android.media.MediaPlayer

class Sounds {

    var soundplayer: MediaPlayer = MediaPlayer()

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
                soundplayer = MediaPlayer.create(context, R.raw.stabby_stabby)
                return soundplayer.start()
            }
            6 -> {
                soundplayer = MediaPlayer.create(context, R.raw.lightsaber_stabby_stabby)
                return soundplayer.start()
            }
            7 -> {
                soundplayer = MediaPlayer.create(context, R.raw.gaster_blaster_master)
                return soundplayer.start()
            }
        }
    }
}