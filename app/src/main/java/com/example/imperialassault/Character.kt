package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap

open class Character {
    var name = ""
    var name_short = ""
    var type = ""
    var defence_dice = ""
    var strength = ""
    var insight = ""
    var tech = ""
    var background = ""
    var health_default = 10
    var endurance_default = 5
    var speed_default = 5

    var health = 10
    var endurance = 5
    var speed = 4

    var xp = 0
    var damage = 0
    var strain = 0
    var token = 0
    var wounded = 0

    var xpScores: IntArray = intArrayOf(0,0,0,0,0,0,0,0,0)
    var xpEndurances: IntArray = intArrayOf(0,0,0,0,0,0,0,0,0)
    var xpHealths: IntArray = intArrayOf(0,0,0,0,0,0,0,0,0)
    var xpSpeeds: IntArray = intArrayOf(0,0,0,0,0,0,0,0,0)

    var tierImages = ArrayList<Bitmap?>()
    var rewardImages = ArrayList<Bitmap?>()
    var cardImages  = ArrayList<Bitmap?>()
    var weaponImages  = ArrayList<Bitmap?>()

    //get current image, get extras from stats
    //get current card images

    fun getImages(context: Context){
        tierImages = Assets.instance.getTierImages(context, name_short)
        print(tierImages)/*
        rewardImages  = Assets.instance.getRewardImages(context, name_short)
        cardImages  =Assets.instance.getCardImages(context, name_short)
        weaponImages  = Assets.instance.getXPCardImages(context, name_short)*/
    }
}