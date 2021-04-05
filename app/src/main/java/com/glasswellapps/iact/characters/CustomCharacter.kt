package com.glasswellapps.iact.characters

import android.content.Context
import com.glasswellapps.iact.R

class CustomCharacter : Character {

    constructor(context: Context){
        name = ""
        name_short = ""
        type = "Hero"
        defence_dice = ""

        strength = "   "
        insight = "   "
        tech = "   "

        strengthWounded = "   "
        insightWounded  = "   "
        techWounded  = "   "

        background = "interior"
        health_default = 13
        endurance_default = 4
        speed_default = 4

        health = 13
        endurance = 4
        speed = 4

        totalXP = 0
        damage = 0
        strain = 0
        token = 0
        wounded = 0

        xpScores = intArrayOf(1,1,2,2,3,3,4,4,0)
        xpEndurances = intArrayOf(0,0,0,0,0,0,0,0,0)
        xpHealths = intArrayOf(0,0,0,0,4,0,0,0,0)
        xpSpeeds = intArrayOf(0,0,0,0,0,0,0,0,0)
        xpItems = intArrayOf(-1,-1,-1,-1,-1,-1,-1,-1,-1)

        loadImages(context)

        bio_title = ""
        bio_quote = "\"\""
        bio_text =""
    }

    override fun loadImages(context: Context){
        super.loadImages(context)
        updateCharacterImages(context)
    }

    override fun loadPortraitImage(context:Context){
        portraitImage = context.resources.getDrawable(R.drawable.portrait_biv)
    }
}