package com.example.imperialassault

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import kotlinx.android.synthetic.main.activity_character_view.view.*
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

            if(loadedCharacters.size == 0) {
                showNoSavesFoundToast()
            }
            true
        }

        listSaveFiles(MainActivity.data)
        if(loadedCharacters.size ==0){
            showNoSavesFoundToast()
        }
        println("no save files" + loadedCharacters.size)
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
                        listView[i], "translationX", -width
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
    var adapter:LoadFileAdapter? = null

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



            adapter = LoadFileAdapter(this, fileNames, loadedCharacters, data)
            listView.adapter= adapter


            listView.isClickable = true
            listView.setOnItemClickListener  { parent, view, position, id ->
                if(position<listView.count) {

                    listView.alpha = 0f

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

                    if(data[position].weapon1!=-1){ loadedCharacters[position].weapons.add(data[position].weapon1)}
                    if(data[position].weapon2!=-1){ loadedCharacters[position].weapons.add(data[position].weapon2)}
                    if(data[position].accessory1!=-1){ loadedCharacters[position].accessories.add(data[position].accessory1)}
                    if(data[position].accessory2!=-1){ loadedCharacters[position].accessories.add(data[position].accessory2)}
                    if(data[position].accessory3!=-1){ loadedCharacters[position].accessories.add(data[position].accessory3)}
                    loadedCharacters[position].helmet = data[position].helmet
                    if(data[position].armour != -1) { loadedCharacters[position].armor.add(data[position].armour) }



                    //TODO rewards and mods
                    loadedCharacters[position].rewards = convertStringToItemID(""+data[position].rewards)
                    loadedCharacters[position].mods = convertStringToItemID(""+data[position].mods)

                    println("Load Mods "+data[position].mods)

                    println( " "+loadedCharacters[position].mods.size)
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
                    val intent = Intent(this, CharacterScreen::class.java)

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
                    //adapter!!.finish()

                    for(i in 0..data.size-1){

                        var portrait = loadedCharacters[i].portraitImage as BitmapDrawable
                        //portrait.bitmap.recycle()
                    }

                    finish()
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

    fun convertStringToItemID(itemString:String):ArrayList<Int> {
        var itemIds = arrayListOf<Int>()
        var itemStrings = itemString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for(itemId in itemStrings){
            if(itemId.isNotEmpty()){
                itemIds.add(itemId.toInt())
            }
        }
        return itemIds
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
        adapter!!.notifyDataSetChanged()
        saveDialog!!.cancel()


        val deleteWorkRequestBuilder = OneTimeWorkRequest.Builder(deleteWorker::class.java)
        val data = Data.Builder()
        data.putInt("position", positionEditing)
        deleteWorkRequestBuilder.setInputData(data.build())



        WorkManager.getInstance(this).enqueue(deleteWorkRequestBuilder.build())


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


            super.onBackPressed()


    }

    fun showNoSavesFoundToast(){
        val noSavesFoundToast= Dialog(this)

        noSavesFoundToast.requestWindowFeature(Window.FEATURE_NO_TITLE)
        noSavesFoundToast.setCancelable(false)
        noSavesFoundToast.setContentView(R.layout.dialog_no_savefiles_found)


        noSavesFoundToast.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        noSavesFoundToast!!. show()
        MainScope().launch {
                delay(1000)
                noSavesFoundToast.cancel()

            }

    }
}

class LoadFileAdapter(
    val context: Activity, val fileNames: ArrayList<String?>, val characters
    : ArrayList<Character>, val data: List<CharacterData>?
)
    : ArrayAdapter<String>(context, R.layout.save_load_item, fileNames){

    var saveFiles = arrayListOf<View>()

    init{
        val inflater = context.layoutInflater
        for(i in 0..fileNames.size-1){

            val button = inflater.inflate(R.layout.save_load_item, null, true)

                val saveNameText = button.findViewById(R.id.save_file_name) as TextView
                saveNameText.text = fileNames[i]

                val saveCharacterImage = button.findViewById(R.id.save_file_image) as ImageView

                saveCharacterImage.setImageDrawable(characters[i].portraitImage)

                var level = 5
                println("spent " + data!![i].xpSpent + "total " + data!![i].totalXP)
                if (data!![i].xpSpent <= 1) {
                    level = 1
                } else if (data!![i].xpSpent <= 4) {
                    level = 2
                } else if (data!![i].xpSpent <= 7) {
                    level = 3
                } else if (data!![i].xpSpent <= 10) {
                    level = 4
                }
                button.save_file_level.setText("Lv " + level)
                button.save_file_character.setText("" + characters[i].name)
                button.save_file_date.setText("" + data!![i].date)


            saveFiles.add(button)
        }
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        return saveFiles[position]
    }

    fun finish(){
        for(i in 0..saveFiles.size-1){
            var image = saveFiles[i].save_file_image as ImageView
            var b = image.drawable as BitmapDrawable
            b.bitmap.recycle()
            b = saveFiles[i].save_file_back.background as BitmapDrawable
            b.bitmap.recycle()

        }
    }

}

//TODO?????????
class deleteWorker(val context: Context, params: WorkerParameters): Worker
    (context,
    params){



    override fun doWork(): Result {
        var positionEditing = inputData.getInt("position",-1)
        if(positionEditing!=-1) {
            val database = AppDatabase.getInstance(context)
            database!!.getCharacterDAO().deleteById(MainActivity.data!![positionEditing].id)
        }
        return Result.success()
    }

}

