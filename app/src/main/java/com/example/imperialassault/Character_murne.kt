package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap

class Character_murne : Character {

    constructor(context: Context){
        //default values
        name = "Murne Rin"
        name_short = "murne"
        type = "Hero"
        defence_dice = "black"

        strength = "B  "
        insight = "BGY"
        tech = "BG "

        strengthWounded = "R  "
        insightWounded  = "BGR"
        techWounded  = "BR "

        background = "interior"
        health_default = 12
        endurance_default = 4
        speed_default = 4

        health = 12
        endurance = 4
        speed = 4

        xp = 0
        damage = 0
        strain = 0
        token = 0
        wounded = 0

        xpScores = intArrayOf(1,1,2,2,3,3,4,4,0)
        xpEndurances = intArrayOf(0,0,0,0,0,0,0,0,0)
        xpHealths = intArrayOf(0,0,0,2,0,0,0,0,0)
        xpSpeeds = intArrayOf(0,0,0,0,0,0,0,0,0)

        //Get Images
        //Update images

        //update strain, update damage, xp, cards, weapons

        loadImages(context)

        bio_title = "Covert Agent"
        bio_quote = "\"The Imperial military police couldn't take me alive, and neither will you!\""
        bio_text ="Mak wields a modified Longblaster- a civilian hunting weapon he has " +
                "re-purposed in an attempt to disguise his assassinations- by averting the use of" +
                " military grade weapons. He prefers to take out high-priority targets from long " +
                "range, but isn't opposed to a more intimate fight if he must. Mak also commonly utilizes an array of tech from his connections in the Bothan Spynet or his past allegiance with the ISB. Mak has a talent of “melting” in and out of combat; waylaying his foes and causing distractions to catch them unprepared. Thanks to his agility and stealth, enemies have a hard time pining this elusive Bothan down. In his spare time, Mak attempts to gather volunteers to aid him in removing the threat of the Imperial \"Shadow Suits\" and end any advantage the Empire could have gained from their creation."
    }

    //TODO alter for reward, duplicates, tier
    override fun getCharacterImage(): Bitmap?{
        return tierImages[0];
    }
}