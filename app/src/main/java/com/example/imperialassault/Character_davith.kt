package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap
import com.example.imperialassault.Character

class Character_davith : Character {
    
    //ALS ancient light saber
    var ALSImages = ArrayList<Bitmap?>()
    var card6ALSImages = ArrayList<Bitmap?>()
    var card9ALSImages = ArrayList<Bitmap?>()

    constructor(context: Context) {
        //default values
        name = "Davith Elso"
        name_short = "davith"
        type = "Hero"
        defence_dice = "white"

        strength = "BG "
        insight = "BGY"
        tech = "B  "

        strengthWounded = "BR "
        insightWounded = "BGR"
        techWounded = "R  "

        background = "interior"
        health_default = 11
        endurance_default = 4
        speed_default = 4

        health = 11
        endurance = 4
        speed = 4


        damage = 0
        strain = 0
        token = 0
        wounded = 0

        totalXP = 0
        xpEndurances = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        xpHealths = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        xpSpeeds = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

        portraitRow = 1
        portraitCol = 0
        //Get Images
        //Update images

        //update strain, update damage, xp, cards, weapons

        loadImages(context)

        bio_title = "Exiled Jedi"
        bio_quote = "\"Once discovered, the best defense is an unrelenting assault.\""
        bio_text = "Due to the roving Jedi-Hunters still searching for him and his ilk; " +
                "Davith decided to set aside his Lightsaber for now, keeping it hidden somewhere secret and safe until necessary. " +
                "He instead brandishes a deadly Heirloom Dagger when facing his foes in combat; the blade being the only memento he has left of his parents before he joined the Jedi order. " +
                "\n\nHe is quite skilled in the ways of the force; able to alter the movement of " +
                "his enemies, and increase his own inherent talents to an advanced level. When enhanced by the force, Elso’s Speed allows him to dart amongst his foes without them even comprehending his movements. " +
                "Davith has also been trained to use the force to Mask His Presence from his enemies Eyesight and Cloud The Minds of the unwitting. " +
                "\n\nHaving successfully taken down a huge number of the Imperial leadership, " +
                "Davith is a true threat to the Empire at large. \"Hawkbat\" will find vengeance for his friends, and one day the Emperor will fall- for all of his crimes. "
    }

    //TODO alter for reward, duplicates, tier
    override fun loadImages(context: Context) {
        super.loadImages(context)
    }

    //TODO alter for reward, duplicates, tier
    override fun updateCharacterImages(context: Context) {
        super.updateCharacterImages(context)

        //card6 and card9 combined tier images

        if (xpCardsEquipped[5] && xpCardsEquipped[8]) {
            var card69Image = loadCardTierImage(context, tier, "card6_card9")
            if (card69Image != null) {
                currentImage = card69Image
            }
        }

        //card6 tier images
        else if (xpCardsEquipped[5]) {
            //TODO check ancient light saber

            var card6Image = loadCardTierImage(context, tier, "card6")
            if (card6Image != null) {
                currentImage = card6Image
            }


        }
        //card9 tier images
        else if (xpCardsEquipped[8]) {
            //TODO check ancient light saber
            var card9Image = loadCardTierImage(context, tier, "card9")
            if (card9Image != null) {
                currentImage = card9Image
            }
        }
    }
    override fun loadPortraitImage(context:Context){
        portraitImage = context.resources.getDrawable(R.drawable.portrait_davith)
    }
}