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

    var strengthWounded = ""
    var insightWounded = ""
    var techWounded = ""


    var health_default = 10
    var endurance_default = 5
    var speed_default = 5

    var health = 10
    var endurance = 5
    var speed = 4

    var xpScores = intArrayOf(1,1,2,2,3,3,4,4,0)
    var xpEndurances: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var xpHealths: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var xpSpeeds: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)


    var bio_title = ""
    var bio_quote = ""
    var bio_text = ""

    var power:Bitmap?=null
    var power_wounded:Bitmap?=null

    //Images
    var currentImage:Bitmap? = null
    var layer2:Bitmap? = null
    var layer1:Bitmap? = null
    var layer1OnTop = false

    var tier = 0
    var tierImages = ArrayList<Bitmap?>()

    var xpCardImages  = ArrayList<Bitmap>()
    var companionCard:Bitmap? = null
    var portraitImage:Bitmap? = null
    var portraitRow = 0
    var portraitCol = 0

    //****************************************************************************************************
    //region To Save
    //****************************************************************************************************
    var damage = 0
    var strain = 0
    var token = 0
    var wounded = 0

    var conditionsActive = booleanArrayOf(false,false,false,false,false)

    var totalXP = 0
    var xpSpent = 0
    var xpCardsEquipped: BooleanArray= booleanArrayOf(false,false,false,false,false,false,false,false,false)

    var weapon1 = ""
    var weapon2 = ""
    var accessory1 = ""
    var accessory2 = ""
    var accessory3 = ""
    var helmet = ""
    var armour = ""

    var background = "interior"

    //stats
    // villain, leader, vehicle,creature,  guard, droid, scum, trooper
    var killCount = arrayOf(0,0,0,0,0,0,0,0)
    var assistCount = arrayOf(0,0,0,0,0,0,0,0)

    var movesTaken = 0
    var attacksMade = 0
    var interactsUsed = 0
    var timesWounded = 0
    var timesRested = 0
    var timesWithdrawn = 0
    var activated = 0
    var damageTaken = 0
    var strainTaken = 0
    var specialActions = 0
    var timesFocused = 0
    var timesHidden = 0
    var timesStunned = 0
    var timesBleeding = 0
    var timesWeakened = 0
    var cratesPickedUp = 0

    var withdrawn = false
    var rewardObtained = false

    //endregion
    //****************************************************************************************************

    open fun loadImages(context: Context){
        loadTierImages(context)
        loadXPCardImages(context)
        loadPowerImages(context)
        loadPortraitImage(context)
    }

    open fun loadPowerImages(context: Context) {
        power = getBitmap(context,"characters/" + name_short + "/power.png")
        power_wounded = getBitmap(context,"characters/" + name_short + "/power_wounded.png")
    }

    open fun loadTierImages(context: Context){
        val images = java.util.ArrayList<Bitmap?>()
        for (i in 0..3) {
            val image = getBitmap(context, "characters/" + name_short + "/images/tier" + i + "image.png")
            images.add(image)
        }
        tierImages = images
    }

    open fun loadXPCardImages(context: Context){
        val images = java.util.ArrayList<Bitmap>()
        for (i in 1..9) {
            val image = getBitmap(context, "characters/" + name_short + "/xpcards/card" + i + ".jpg")
            if (image != null) {
                images.add(image)
            }
        }
        xpCardImages = images
    }

    open fun getBackgroundImage(context: Context): Bitmap? {
        val image = getBitmap(context, "backgrounds/background_"+ background + ".png")
       return image;
    }

    open fun loadPortraitImage(context:Context){
        var allChSel = BitmapFactory.decodeResource(context.resources,R.drawable
            .allcharacterselect_21)
        allChSel = Bitmap.createScaledBitmap(allChSel,2547,850,false)

        var row = 6
        var col = 4
        var width = 2547/col
        var height = 850/row

        portraitImage = Bitmap.createBitmap(allChSel,0+(width*portraitCol),0+(height*portraitRow), height, height)
    }


    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open(path)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            //e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                //e.printStackTrace()
            }
        }
        return null
    }

    open fun loadCardTierImages(context: Context, cards:String): ArrayList<Bitmap?>{
        val images = java.util.ArrayList<Bitmap?>()
        for (i in 0..3) {
            val image = getBitmap(context, "characters/" + name_short + "/images/tier" + i +
                    "image_"+cards+".png")

            images.add(image)

        }
        return images
    }

    open fun updateCharacterImages(){

        tier = 0
        //TODO item conditions
        //tier3 [9xp && (1 tier2 || tier3 item)] || 11xp)
        if(xpSpent>=11){
            tier = 3
        }
        //tier2 [6xp && (1 tier2 item)] || 8xp)
        else if(xpSpent >= 8){
            tier = 2
        }
        //tier1 [3xp && (1 tier1 item)] || 5xp)
        else if(xpSpent >= 5) {
            tier = 1
        }

        currentImage = tierImages[tier]
    }
}