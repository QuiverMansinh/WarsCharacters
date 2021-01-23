package com.example.imperialassault

import android.content.Context
import android.graphics.Bitmap
import com.example.imperialassault.Character

class Character_verena : Character {
    var tier1duplicate:Bitmap? = null
    var tier1duplicate2:Bitmap? = null
    var tier2duplicate:Bitmap? = null
    var tier3duplicate:Bitmap? = null
    var tier3duplicate2:Bitmap? = null

    constructor(context: Context){
        //default values
        name = "Verena Talos"
        name_short = "verena"
        type = "Hero"
        defence_dice = "black"

        strength = "BG "
        insight = "BG "
        tech = "BG "

        strengthWounded = "BR "
        insightWounded  = "BR "
        techWounded  = "BR "

        background = "interior"
        health_default = 12
        endurance_default = 5
        speed_default = 4

        health = 12
        endurance = 5
        speed = 4

        totalXP = 0
        damage = 0
        strain = 0
        token = 0
        wounded = 0

        xpScores = intArrayOf(1,1,2,2,3,3,4,4,0)
        xpEndurances = intArrayOf(0,0,0,0,0,0,0,1,0)
        xpHealths = intArrayOf(0,0,0,0,0,0,0,0,0)
        xpSpeeds = intArrayOf(0,0,0,0,0,0,0,0,0)

        portraitRow = 2
        portraitCol = 3
        //Get Images
        //Update images

        //update strain, update damage, xp, cards, weapons

        loadImages(context)

        bio_title = "Master Combatant"
        bio_quote = "\"The friend of my enemy is my shield.\""
        bio_text ="Stubborn, determined, noble, and stern, Verena has earned her place by skill and effort- a fact she is very proud of. " +
                "Preferring to face her foes in CQC, Verena delights in re-purposing her enemies weaponry against them; regularly disarming her opponent with quick strikes and dispatching the foe with their own weapon. " +
                "\n\nEver the pragmatist, her minimalistic kit includes her CorSec issued- " +
                "Military Blaster, two spare ammo clips, a Datapad, and the Fighting Knife her brother used in the attempt on her life; a constant reminder of his treachery. " +
                "She hopes to return the blade to him in kind some day."    }

    //TODO alter for reward, duplicates, tier
    override fun loadImages(context: Context){
        super.loadImages(context)

        tier1duplicate = getBitmap(context, "characters/verena/images/tier1image_duplicate.png")
        tier1duplicate2 = getBitmap(context, "characters/verena/images/tier1image_duplicate2.png")

        tier2duplicate = getBitmap(context, "characters/verena/images/tier2image_duplicate.png")

        tier3duplicate = getBitmap(context, "characters/verena/images/tier3image_duplicate.png")
        tier3duplicate2 = getBitmap(context, "characters/verena/images/tier3image_duplicate2.png")

    }

    //TODO alter for reward, duplicates, tier
    override fun updateCharacterImages(context: Context){
        super.updateCharacterImages(context)

        var ran = Math.random()

        if(tier == 1){
            if(ran<1f/3){
                currentImage = tier1duplicate
            }
            else if(ran<2f/3){
                currentImage = tier1duplicate2
            }
        }
        else if(tier == 2){
            if(ran<0.5){
                currentImage = tier2duplicate
            }
        }
        else if(tier == 3){
            if(ran<1f/3){
                currentImage = tier3duplicate
            }
            else if(ran<2f/3){
                currentImage = tier3duplicate2
            }
        }

        //todo helmet
    }
    override fun loadPortraitImage(context:Context){
        portraitImage = context.resources.getDrawable(R.drawable.portrait_verena)
    }
}