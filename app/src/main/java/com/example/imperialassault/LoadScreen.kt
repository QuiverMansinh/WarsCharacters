package com.example.imperialassault

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch


class LoadScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)
        val database = AppDatabase.getInstance(this)


        var data:List<CharacterData>? = null

            lifecycleScope.launch {

                data = database!!.getCharacterDAO().getAll()
                println(data)
                listSaveFiles(data)

            }

    }
    private lateinit var listView:ListView

    companion object{
        var selectedCharacter:Character? = null
    }

    var loadedCharacters = ArrayList<Character>()
    fun listSaveFiles(data : List<CharacterData>?){
        listView = findViewById<ListView>(R.id.load_screen_list)
        listView.divider = null
        listView.dividerHeight = 0
        if( data != null) {
            var fileNames = ArrayList<String?>()

            for(i in 0..data.size-1){
                fileNames.add(data[i].fileName)
                val character = selectCharacter(data[i].characterName)
                loadedCharacters.add(character)
            }
            fileNames.add("")
            loadedCharacters.add(Character())

            val adapter = SaveFileAdapter(this,fileNames, loadedCharacters)
            listView.adapter= adapter


            listView.isClickable = true
            listView.setOnItemClickListener  { parent, view, position, id ->
                if(position<listView.count-1) {
                    println("" + data[position])
                    loadedCharacters[position].damage = data[position].damage
                    loadedCharacters[position].strain = data[position].strain
                    loadedCharacters[position].token = data[position].token
                    loadedCharacters[position].wounded = data[position].wounded

                    loadedCharacters[position].conditionsActive = booleanArrayOf(
                        data[position].conditionsActive1,
                        data[position].conditionsActive2,
                        data[position].conditionsActive3,
                        data[position].conditionsActive4,
                        data[position].conditionsActive5
                    )

                    loadedCharacters[position].totalXP = data[position].totalXP
                    loadedCharacters[position].xpSpent = data[position].xpSpent
                    loadedCharacters[position].xpCardsEquipped = booleanArrayOf(
                        data[position].xpCardsEquipped1,
                        data[position].xpCardsEquipped2,
                        data[position].xpCardsEquipped3,
                        data[position].xpCardsEquipped4,
                        data[position].xpCardsEquipped5,
                        data[position].xpCardsEquipped6,
                        data[position].xpCardsEquipped7,
                        data[position].xpCardsEquipped8,
                        data[position].xpCardsEquipped9
                    )

                    loadedCharacters[position].weapon1 = data[position].weapon1.toString()
                    loadedCharacters[position].weapon1 = data[position].weapon2.toString()
                    loadedCharacters[position].accessory1 = data[position].accessory1.toString()
                    loadedCharacters[position].accessory2 = data[position].accessory2.toString()
                    loadedCharacters[position].accessory3 = data[position].accessory3.toString()
                    loadedCharacters[position].helmet = data[position].helmet.toString()
                    loadedCharacters[position].armour = data[position].armour.toString()

                    loadedCharacters[position].background = data[position].background.toString()

                    loadedCharacters[position].killCount = arrayOf(
                        data[position].killVillian,
                        data[position].killLeader,
                        data[position].killVehicle,
                        data[position].killCreature,
                        data[position].killGuard,
                        data[position].killDroid,
                        data[position].killScum,
                        data[position].killTrooper
                    )

                    loadedCharacters[position].assistCount = arrayOf(
                        data[position].assistVillian,
                        data[position].assistLeader,
                        data[position].assistVehicle,
                        data[position].assistCreature,
                        data[position].assistGuard,
                        data[position].assistDroid,
                        data[position].assistScum,
                        data[position].assistTrooper
                    )

                    loadedCharacters[position].movesTaken = data[position].moves
                    loadedCharacters[position].attacksMade = data[position].attacks
                    loadedCharacters[position].interactsUsed = data[position].interact
                    loadedCharacters[position].timesWounded = data[position].timesWounded
                    loadedCharacters[position].timesRested = data[position].rested
                    loadedCharacters[position].timesWithdrawn = data[position].timesWithdrawn
                    loadedCharacters[position].activated = data[position].activated
                    loadedCharacters[position].damageTaken = data[position].damageTaken
                    loadedCharacters[position].strainTaken = data[position].strainTaken
                    loadedCharacters[position].specialActions = data[position].special

                    loadedCharacters[position].timesFocused = data[position].timesFocused
                    loadedCharacters[position].timesHidden = data[position].timesHidden
                    loadedCharacters[position].timesStunned = data[position].timesStunned
                    loadedCharacters[position].timesBleeding = data[position].timesBleeding
                    loadedCharacters[position].timesWeakened = data[position].timesWeakened
                    loadedCharacters[position].cratesPickedUp = data[position].cratesPickedUp
                    loadedCharacters[position].rewardObtained = data[position].rewardObtained
                    loadedCharacters[position].withdrawn = data[position].withdrawn

                    selectedCharacter = loadedCharacters[position]


                    val intent = Intent(this, Character_view::class.java)
                    intent.putExtra("CharacterName", loadedCharacters[position].name_short)
                    intent.putExtra("Load", true)
                    startActivity(intent);
                    finish()
                }
            }
        }


    }
    fun selectCharacter(characterName : String?):Character{
        var character = Character();
        when (characterName) {
            "biv" -> { character = Character_biv(this) }
            "davith" -> { character = Character_davith(this) }
            "diala" -> { character = Character_diala(this) }
            "drokdatta" -> { character = Character_drokkatta(this) }
            "fenn" -> { character = Character_fenn(this) }
            "gaarkhan" -> { character = Character_gaarkhan(this) }
            "gideon" -> { character = Character_gideon(this) }
            "jarrod" -> { character = Character_jarrod(this) }
            "jyn" -> { character = Character_jyn(this) }
            "loku" -> { character = Character_loku(this) }
            "kotun" -> { character = Character_kotun(this) }
            "mak" -> { character = Character_mak(this) }
            "mhd19" -> { character = Character_mhd19(this) }
            "murne" -> { character = Character_murne(this) }
            "onar" -> { character = Character_onar(this) }
            "saska" -> { character = Character_saska(this) }
            "shyla" -> { character = Character_shyla(this) }
            "verena" -> { character = Character_verena(this) }
            "vinto" -> { character = Character_vinto(this) }
            "drokkatta" -> { character = Character_drokkatta(this) }
            "ct1701" -> { character = Character_ct1701(this) }
            "tress" -> { character = Character_tress(this) }
        }
        return character
    }

}

class SaveFileAdapter(val context: Activity, val fileNames : ArrayList<String?>, val characters
:ArrayList<Character>)
    :ArrayAdapter<String>(context,R.layout.save_load_item,fileNames){
    override fun getView(position: Int, view: View?, parent:ViewGroup):View{
        val inflater = context.layoutInflater
        val button = inflater.inflate(R.layout.save_load_item, null, true)
        if(position < fileNames.size-1) {
            val saveNameText = button.findViewById(R.id.save_file_name) as TextView
            saveNameText.text = fileNames[position]

            val saveCharacterImage = button.findViewById(R.id.save_file_image) as ImageView
            saveCharacterImage.setImageBitmap(characters[position].portraitImage)
        }
        else{
            button.visibility = View.INVISIBLE
        }
        return button
    }
}