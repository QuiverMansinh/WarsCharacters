package com.glasswellapps.iact.character

import android.content.Context
import com.glasswellapps.iact.characters.*
import com.glasswellapps.iact.database.AppDatabase

class CharacterBuilder {
    companion object {
        open fun create(characterName:String, context: Context):Character {
            var character:Character = Character();
            when (characterName) {
                "biv" -> {
                    character = Character_biv(context)
                }
                "davith" -> {
                    character = Character_davith(context)
                }
                "diala" -> {
                    character = Character_diala(context)
                }
                "drokdatta" -> {
                    character = Character_drokkatta(context)
                }
                "fenn" -> {
                    character = Character_fenn(context)
                }
                "gaarkhan" -> {
                    character = Character_gaarkhan(context)
                }
                "gideon" -> {
                    character = Character_gideon(context)
                }
                "jarrod" -> {
                    character = Character_jarrod(context)
                }
                "jyn" -> {
                    character = Character_jyn(context)
                }
                "loku" -> {
                    character = Character_loku(context)
                }
                "kotun" -> {
                    character = Character_kotun(context)
                }
                "mak" -> {
                    character = Character_mak(context)
                }
                "mhd19" -> {
                    character = Character_mhd19(context)
                }
                "murne" -> {
                    character = Character_murne(context)
                }
                "onar" -> {
                    character = Character_onar(context)
                }
                "saska" -> {
                    character = Character_saska(context)
                }
                "shyla" -> {
                    character = Character_shyla(context)
                }
                "verena" -> {
                    character = Character_verena(context)
                }
                "vinto" -> {
                    character = Character_vinto(context)
                }
                "drokkatta" -> {
                    character = Character_drokkatta(context)
                }
                "ct1701" -> {
                    character = Character_ct1701(context)
                }
                "tress" -> {
                    character = Character_tress(context)
                }
                "custom" -> {
                    character = CustomCharacter(context)
                    val database = AppDatabase.getInstance(context)
                    val data = database!!.getCustomDAO().getAll()
                    character.name = data[0].characterName!!
                    character.health_default = data[0].health
                    character.endurance_default = data[0].endurance
                    character.speed_default = data[0].speed
                    character.defence_dice = data[0].defence
                }
            }
            return character
        }

    }
}