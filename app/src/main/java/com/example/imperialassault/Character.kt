package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

open class Character {
    var name = ""
    var name_short = ""
    var type = ""
    var defence_dice = ""
    var strength = ""
    var insight = ""
    var tech = ""
    var background = "interior"
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

    var xpScores: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var xpEndurances: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var xpHealths: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var xpSpeeds: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

    var tierImages = ArrayList<Bitmap>()
    var rewardImages = ArrayList<Bitmap>()
    var cardImages  = ArrayList<Bitmap>()
    var weaponImages  = ArrayList<Bitmap>()

    //get current image, get extras from stats
    //get current card images


    fun loadImages(context: Context){
        tierImages = loadTierImages(context)
        print(tierImages)/*
        rewardImages  = getRewardImages(context, name_short)
        cardImages  =getCardImages(context, name_short)
        weaponImages  = getXPCardImages(context, name_short)*/
    }

    open fun loadTierImages(context: Context): java.util.ArrayList<Bitmap> {
        val images = java.util.ArrayList<Bitmap>()
        for (i in 0..4) {
            val image = getBitmap(context, "characters/" + name_short + "/images/tier" + i + "image.png")
            if (image != null) {
                images.add(image)
            }
        }
        return images
    }

    open fun getBackgroundImage(context: Context): Bitmap? {
        val image = getBitmap(context, "backgrounds/background_"+ background + ".png")
       return image;
    }


    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open(path)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    open fun getCharacterImage(): Bitmap?{
        return tierImages[0];
    }
}