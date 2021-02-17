package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import kotlinx.android.synthetic.main.activity_character_view.*
import java.io.InputStream

open class Character {
    var name = ""
    var name_short = ""
    var file_name = "autosave"
    var id = -1

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
    var glowImage:Bitmap?=null

    var startingMeleeWeapon:Bitmap?=null
    var startingRangedWeapon:Bitmap?=null

    var tier = 0
    //var tierImages = ArrayList<Bitmap?>()

    var xpCardImages  = ArrayList<Bitmap>()
    var companionCard:Bitmap? = null
    var portraitImage:Drawable? = null
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

    var weapons = arrayListOf<Int>()
    var accessories = arrayListOf<Int>()
    var helmet = false
    var armor = arrayListOf<Int>()
    var rewards  = arrayListOf<Int>()
    var mods = arrayListOf<Int>()

    //TODO remove
    var meleeMods = arrayListOf<Int>()

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
        //loadTierImages(context)

        loadXPCardImages(context)
        loadPowerImages(context)



    }

    open fun loadPowerImages(context: Context) {
        power = getBitmap(context,"characters/" + name_short + "/power.png")
        power_wounded = getBitmap(context,"characters/" + name_short + "/power_wounded.png")
    }
    open fun loadTierImage(context: Context, tier:Int){
        val image = getBitmap(context, "characters/" + name_short + "/images/tier" + tier + "image.png")
        currentImage = image
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
        val image = getBitmap(context, "backgrounds/background_"+ background + ".jpg")
        return image;
    }

    open fun loadPortraitImage(context:Context){
        portraitImage = context.resources.getDrawable(R.drawable.portrait_fenn)
    }


    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var bitmap:Bitmap? = null
        val options = BitmapFactory.Options()
        for(i in 1..32) {
            try {
                inputStream = assetManager.open(path)
                options.inSampleSize = i
                bitmap = BitmapFactory.decodeStream(inputStream,null,options)
                break
            } catch (outOfMemoryError:OutOfMemoryError) {

                println("next size"+i)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    open fun loadCardTierImage(context: Context, tier:Int, cards:String):Bitmap?{
        val image = getBitmap(context, "characters/" + name_short + "/images/tier" + tier +
                "image_"+cards+".png")
        return image
    }


    open fun updateCharacterImages(context:Context){

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
        println("glow tier"+tier)
        //glowImage =  getBitmap(context, "characters/" + name_short + "/images/glow"+tier+".png")
        //println(glowImage)

        loadTierImage(context,tier)



    }
}