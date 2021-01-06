package com.example.imperialassault

import android.content.Context

class Character_mak : Character {

    constructor(context: Context){
        //default values
        name = "Mak Eshka'rey"
        name_short = "mak"
        type = "Hero"
        defence_dice = "white"
        strength = "BG"
        insight = "BG"
        tech = "BGY"
        background = "interior"
        health_default = 10
        endurance_default = 5
        speed_default = 5

        health = 10
        endurance = 5
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

        getImages(context)
    }


}