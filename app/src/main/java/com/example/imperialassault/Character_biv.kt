package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap

class Character_biv : Character {

    var card6Images = ArrayList<Bitmap?>()
    var card5Images = ArrayList<Bitmap?>()

    constructor(context: Context){
        //default values
        name = "Biv Bodhrik"
        name_short = "biv"
        type = "Hero"
        defence_dice = "black"

        strength = "BGY"
        insight = "B  "
        tech = "BG "

        strengthWounded = "BGR"
        insightWounded  = "R  "
        techWounded  = "BR "

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

        //Get Images
        //Update images

        //update strain, update damage, xp, cards, weapons

        loadImages(context)

        bio_title = "Vicious Vanguard"
        bio_quote = "\"You don't kill as many stormtroopers as I have by playing it safe.\""
        bio_text ="The Rebellion has ties to many smugglers, bandits, thieves, mercenaries, and " +
                "people of less than stellar reputation. Mysterious figures who are not official " +
                "Alliance operatives, but who are sympathetic to the Rebel cause and will " +
                "occasionally do errands or favors for the right price. \n\nWith no true fear of" +
                " " +
                "death or injury, nor patience for cowardice or hesitation, Biv holds the line. Having no qualms about Looting the corpses of his fallen foes, Biv eagerly uses their scavenged weapons and armor against the Empire to great effect; particularly preferring to use a Repeating Blaster he recently pilfered from a fallen Heavy Stormtrooper. " +
                "\n\nBiv is highly proficient in Melee Combat, due to his once daily sparing " +
                "sessions with CIS MagnaGuards during the Clone Wars; he often enjoys fighting his foes at Point Blank range, where his great strength can easily be brought to bear. His years as a prisoner were not spent lying down either; Biv eagerly committed to a stringent physical training program to increase his general strength, endurance, and stamina. " +
                "\n\nBiv holds a bloodthirsty vendetta against Kayn Somos for his many abuses " +
                "during his incarceration; luckily his years of imprisonment have taught him patience. Vengeance is only a matter of time..."
    }

    override fun loadImages(context: Context){
        super.loadImages(context)
        card6Images = loadCardTierImages(context,"card6")
        card5Images = loadCardTierImages(context,"card5")
    }

    //TODO alter for reward, duplicates, tier
    override fun updateCharacterImages(){
        super.updateCharacterImages()

        //card5 overlay
        overlay1 = null
        if(xpCardsEquipped[4]){
            if(card5Images[tier] != null){
                overlay1 = card5Images[tier]
            }
        }
        println( " "+ xpCardsEquipped[4] + " " + overlay1 + "BIV")

        //card6 tier images
        if(xpCardsEquipped[5]){
            if(card6Images[tier] != null){
                currentImage = card6Images[tier]
            }
        }
    }
}