package com.example.imperialassault

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_character_view.view.*
import kotlinx.android.synthetic.main.activity_load_screen.*
import kotlinx.android.synthetic.main.dialog_edit_save.*
import kotlinx.android.synthetic.main.save_load_item.view.*
import kotlinx.coroutines.*


class LoadScreen : AppCompatActivity() {

    var saveDialog:Dialog? = null
    var width = 0f
    var height = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setContentView(R.layout.activity_load_screen)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()

        val database = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            MainActivity.data = database!!.getCharacterDAO().getAll()
        }

        saveDialog = Dialog(this)
        saveDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        saveDialog!!.setCancelable(false)
        saveDialog!!.setContentView(R.layout.dialog_edit_save)
        saveDialog!!.setCanceledOnTouchOutside(true)
        saveDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        saveDialog!!.edit_load_file_name.requestFocus()
        saveDialog!!.apply.setOnClickListener {
            onApply(saveDialog!!.apply)
            saveDialog!!.cancel()
            true
        }
        saveDialog!!.delete.setOnClickListener {
            onDelete(saveDialog!!.delete)
            saveDialog!!.cancel()
            if(loadedCharacters.size <= 1) {
                onBackPressed()
            }
            true
        }

        listSaveFiles(MainActivity.data)
        if(loadedCharacters.size <= 1){
            showNoSavesFoundToast()
        }
        println("no save files found " + loadedCharacters.size)
    }

    fun setAnimation(){
        /*if(Build.VERSION.SDK_INT>20) {
            val fade = android.transition.Fade()
            fade.setDuration(100);
            getWindow().setExitTransition(fade);
            getWindow().setEnterTransition(fade);
        }*/
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(hasFocus){
            listView.animate().alpha(1f)
           for(i in 0..listView.count-1) {
                try {
                    val anim = ObjectAnimator.ofFloat(
                        listView.get(i), "translationX", -width
                            .toFloat() * (i + 2), 0f
                    )
                    anim.interpolator = DecelerateInterpolator()
                    anim.duration=(100*(i+2)).toLong()
                    anim.start()

                }
                catch (e: Exception){
                    //println(i)
                }
            }

        }
    }


    private lateinit var listView:ListView

    var fileNames = ArrayList<String?>()
    var loadedCharacters = ArrayList<Character>()
    var adapter:ArrayAdapter<String>? = null

    fun listSaveFiles(data: List<CharacterData>?){
        listView = findViewById<ListView>(R.id.load_screen_list)
        listView.divider = null
        listView.dividerHeight = 0




        if( data != null) {


            for(i in 0..data.size-1){
                fileNames.add(data[i].fileName)
                val character = selectCharacter(data[i].characterName)
                loadedCharacters.add(character)
                loadedCharacters[i].loadPortraitImage(this)
            }
            fileNames.add("")
            loadedCharacters.add(Character())

            adapter = LoadFileAdapter(this, fileNames, loadedCharacters, data)
            listView.adapter= adapter


            listView.isClickable = true
            listView.setOnItemClickListener  { parent, view, position, id ->
                if(position<listView.count-1) {
                    //println("" + data[position])
                    loadedCharacters[position].file_name = ""+data[position].fileName
                    loadedCharacters[position].id = data[position].id

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

                    loadedCharacters[position].weapon1 = data[position].weapon1
                    loadedCharacters[position].weapon1 = data[position].weapon2
                    loadedCharacters[position].accessory1 = data[position].accessory1
                    loadedCharacters[position].accessory2 = data[position].accessory2
                    loadedCharacters[position].accessory3 = data[position].accessory3
                    loadedCharacters[position].helmet = data[position].helmet
                    loadedCharacters[position].armour = data[position].armour

                    //TODO rewards and mods

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


                    MainActivity.selectedCharacter = loadedCharacters[position]

                    listView.animate().alpha(0f)
                    val intent = Intent(this, Character_view::class.java)

                    /*
                    for(i in 0..loadedCharacters.size-1){
                        if(i != position){
                            if(loadedCharacters[i].portraitImage!=null) {
                                var image: BitmapDrawable =
                                    loadedCharacters[i].portraitImage as BitmapDrawable
                                image.getBitmap().recycle();
                            }
                            loadedCharacters[i].portraitImage = null
                        }

                    }

                    adapter!!.notifyDataSetChanged()
*/
                    intent.putExtra("CharacterName", loadedCharacters[position].name_short)
                    intent.putExtra("Load", true)


                        startActivity(intent);
                    for(i in 0..listView.count-1) {
                        try {

                         var image = listView.get(i).character_image as ImageView
                         var b = image.drawable as BitmapDrawable
                            b.bitmap.recycle()
                        }
                        catch (e: Exception){
                            //println(i)
                        }

                    }

                    //finish()
                }
            }
            listView.setOnItemLongClickListener{ parent, view, position, id ->
                //fileNames.removeAt(position)
                //loadedCharacters.removeAt(position)
                positionEditing = position
                listView.animate().alpha(0f)
                saveDialog!!.show()
                saveDialog!!.edit_load_file_name.setText("" + fileNames[position])

                true
            }

        }

    }

    var positionEditing = -1
    fun onApply(view: View){
        fileNames[positionEditing] =  edit_load_file_name.text.toString()
        adapter!!.notifyDataSetChanged()
        //TODO update database


    }
    fun onDelete(view: View){
        fileNames.removeAt(positionEditing)
        loadedCharacters.removeAt(positionEditing)
        //TODO delete from database
        val database = AppDatabase.getInstance(this)
        database!!.getCharacterDAO().delete(MainActivity.data!![positionEditing])
        adapter!!.notifyDataSetChanged()
    }


    fun selectCharacter(characterName: String?):Character{
        var character = Character();
        when (characterName) {
            "biv" -> {
                character = Character_biv(this)
            }
            "davith" -> {
                character = Character_davith(this)
            }
            "diala" -> {
                character = Character_diala(this)
            }
            "drokdatta" -> {
                character = Character_drokkatta(this)
            }
            "fenn" -> {
                character = Character_fenn(this)
            }
            "gaarkhan" -> {
                character = Character_gaarkhan(this)
            }
            "gideon" -> {
                character = Character_gideon(this)
            }
            "jarrod" -> {
                character = Character_jarrod(this)
            }
            "jyn" -> {
                character = Character_jyn(this)
            }
            "loku" -> {
                character = Character_loku(this)
            }
            "kotun" -> {
                character = Character_kotun(this)
            }
            "mak" -> {
                character = Character_mak(this)
            }
            "mhd19" -> {
                character = Character_mhd19(this)
            }
            "murne" -> {
                character = Character_murne(this)
            }
            "onar" -> {
                character = Character_onar(this)
            }
            "saska" -> {
                character = Character_saska(this)
            }
            "shyla" -> {
                character = Character_shyla(this)
            }
            "verena" -> {
                character = Character_verena(this)
            }
            "vinto" -> {
                character = Character_vinto(this)
            }
            "drokkatta" -> {
                character = Character_drokkatta(this)
            }
            "ct1701" -> {
                character = Character_ct1701(this)
            }
            "tress" -> {
                character = Character_tress(this)
            }
        }
        return character
    }
    override fun onBackPressed() {
        for(i in 0..listView.count-1) {
            try {

                var image = listView.get(i).character_image as ImageView
                var b = image.drawable as BitmapDrawable
                b.bitmap.recycle()


                b = listView.get(i).save_file_back.background as BitmapDrawable
                b.bitmap.recycle()


            }
            catch (e: Exception){
                //println(i)
            }

        }
        super.onBackPressed()
        //finish()
    }

    fun showNoSavesFoundToast(){
        val noSavesFoundToast= Dialog(this)

        noSavesFoundToast.requestWindowFeature(Window.FEATURE_NO_TITLE)
        noSavesFoundToast.setCancelable(false)
        noSavesFoundToast.setContentView(R.layout.dialog_no_savefiles_found)


        noSavesFoundToast.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        noSavesFoundToast!!. show()
        println("sdfsdfsdfsdfsdf")
        MainScope().launch {
                delay(2000)
                noSavesFoundToast.cancel()
                onBackPressed()
            }

    }
}

class LoadFileAdapter(
    val context: Activity, val fileNames: ArrayList<String?>, val characters
    : ArrayList<Character>, val data: List<CharacterData>?
)
    : ArrayAdapter<String>(context, R.layout.save_load_item, fileNames){
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val button = inflater.inflate(R.layout.save_load_item, null, true)
        if (position < fileNames.size - 1) {
            val saveNameText = button.findViewById(R.id.save_file_name) as TextView
            saveNameText.text = fileNames[position]

            val saveCharacterImage = button.findViewById(R.id.save_file_image) as ImageView

            saveCharacterImage.setImageDrawable(characters[position].portraitImage)

            var level = 5
            println("spent " + data!![position].xpSpent + "total " + data!![position].totalXP)
            if (data!![position].xpSpent <= 1) {
                level = 1
            } else if (data!![position].xpSpent <= 4) {
                level = 2
            } else if (data!![position].xpSpent <= 7) {
                level = 3
            } else if (data!![position].xpSpent <= 10) {
                level = 4
            }
            button.save_file_level.setText("Lv " + level)
            button.save_file_character.setText("" + characters[position].name)
            button.save_file_date.setText("" + data!![position].date)



        } else {
            button.visibility = View.INVISIBLE
        }
        return button
    }



}

