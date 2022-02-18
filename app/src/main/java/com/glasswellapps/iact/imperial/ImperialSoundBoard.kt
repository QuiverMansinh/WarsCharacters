package com.glasswellapps.iact.imperial

import android.view.View
import com.glasswellapps.iact.character_screen.controllers.ButtonPressedHandler
import com.glasswellapps.iact.effects.Sounds

class ImperialSoundBoard(
    blaster:View,
    impact:View,
    slice:View,

    door:View,
    terminal:View,
    crate:View,
    moving:View,

    droid:View,
    trooper:View,
    vehicle:View,

    lightSaberAttack:View,
    lightSaber:View) {

    init{
        blaster.setOnClickListener { Sounds.play(Sounds.gaster_blaster_master)
        ButtonPressedHandler.onButtonPressed(blaster)}

        impact.setOnClickListener { Sounds.play(Sounds.impact)
            ButtonPressedHandler.onButtonPressed(impact)}

        slice.setOnClickListener { Sounds.play(Sounds.slice)
            ButtonPressedHandler.onButtonPressed(slice)}

        door.setOnClickListener { Sounds.play(Sounds.door)
            ButtonPressedHandler.onButtonPressed(door)}

        terminal.setOnClickListener { Sounds.terminalSound()
            ButtonPressedHandler.onButtonPressed(terminal)}

        crate.setOnClickListener { Sounds.crateSound()
            ButtonPressedHandler.onButtonPressed(crate)}

        moving.setOnClickListener { Sounds.movingSound()
            ButtonPressedHandler.onButtonPressed(moving)}

        droid.setOnClickListener { Sounds.play(Sounds.droid)
            ButtonPressedHandler.onButtonPressed(droid)}

        trooper.setOnClickListener { Sounds.play(Sounds.scream)
            ButtonPressedHandler.onButtonPressed(trooper)}

        vehicle.setOnClickListener { Sounds.play(Sounds.atat)
            ButtonPressedHandler.onButtonPressed(vehicle)}

        lightSaberAttack.setOnClickListener { Sounds.play(Sounds.lightsaber_stabby_stabby)
            ButtonPressedHandler.onButtonPressed(lightSaberAttack)}

        lightSaber.setOnClickListener { Sounds.play(Sounds.lightSaber)
            ButtonPressedHandler.onButtonPressed(lightSaber)}
    }


}