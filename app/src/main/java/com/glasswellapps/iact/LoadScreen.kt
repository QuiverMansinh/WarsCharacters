package com.glasswellapps.iact

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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.glasswellapps.iact.database.AppDatabase
import com.glasswellapps.iact.database.CharacterData
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.characters.*
import kotlinx.android.synthetic.main.dialog_edit_save.*
import kotlinx.android.synthetic.main.save_load_item.view.*
import kotlinx.coroutines.*


class LoadScreen : AppCompatActivity() {

    var saveDialog:Dialog? = null
    var width = 0f
    var height = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_load_screen)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()



        loadData()


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


        println("no save files" + loadedCharacters.size)
    }

    fun loadData(){
        val database = AppDatabase.getInstance(this)
        MainActivity.data = database!!.getCharacterDAO().getAll()
        listSaveFiles(MainActivity.data)
        if(loadedCharacters.size ==0){
            showNoSavesFoundToast()
        }
    }


    fun slideAnimation(){

        for (i in 0..listView.adapter.count - 1) {
            println("ANIMATED LOAD")
            var saveFileView =  listView.adapter.getView(i,listView,listView)
            listView.adapter.getView(i,listView,listView).animate().alpha(1f)
            var anim = ObjectAnimator.ofFloat(
                saveFileView,
                "translationX", -(i.toFloat()/2 + 1) *saveFileView.width.toFloat(),
                0f
            )
            anim.duration = ((i.toFloat()/2 + 1)  * 200).toLong()

            println(""+anim.duration + " " + anim.values[0])

            anim.start()

        }
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {

        if(hasFocus) {
            slideAnimation()

        }
    }


    private lateinit var listView:ListView

    var fileNames = ArrayList<String?>()
    var loadedCharacters = ArrayList<com.glasswellapps.iact.characters.Character>()
    var adapter: LoadFileAdapter? = null

    fun listSaveFiles(data: List<CharacterData>?){
        listView = findViewById<ListView>(R.id.load_screen_list)
        listView.animate().alpha(1f)
        listView.divider = null
        listView.dividerHeight = 0




        if( data != null) {

            for(i in 0..data.size-1){
                if(!data[i].deleted){
                    fileNames.add(data[i].fileName)
                    val character = selectCharacter(data[i].characterName)
                    loadedCharacters.add(character)
                    loadedCharacters[i].loadPortraitImage(this)
                }
                else{
                    positionEditing = i
                    onDelete(this.listView)
                }
            }

            adapter = LoadFileAdapter(this, fileNames, loadedCharacters, data)
            listView.adapter= adapter

            listView.isClickable = true
            listView.setOnItemClickListener  { parent, view, position, id ->
                if(position<listView.count) {
                    Sounds.selectSound()



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

                    loadedCharacters[position].damageAnimSetting = data[position].damageSetting
                    loadedCharacters[position].soundEffectsSetting = data[position].soundEffectsSetting
                    loadedCharacters[position].actionUsageSetting = data[position].actionUsageSetting
                    loadedCharacters[position].imageSetting = data[position].imageSetting
                    loadedCharacters[position].conditionAnimSetting = data[position].conditionAnimSetting

                    MainActivity.selectedCharacter = loadedCharacters[position]

                    for (i in 0..listView.adapter.count - 1) {

                        if(i!=position) {
                            listView.adapter.getView(i, listView, listView).alpha=0f
                        }
                    }
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
                    for(i in 0..data.size-1){

                        var portrait = loadedCharacters[i].portraitImage as BitmapDrawable
                        //portrait.bitmap.recycle()
                    }

                    startActivity(intent)
                    finish()
                    //adapter!!.finish()



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
        Sounds.selectSound()
//        fileNames[positionEditing] =  edit_load_file_name.text.toString()
       // adapter!!.notifyDataSetChanged()
     //   slideAnimation()
        //TODO update database
        MainActivity.data!![positionEditing].fileName =  saveDialog!!.edit_load_file_name.text.toString()
        val database = AppDatabase.getInstance(this)

        database!!.getCharacterDAO().update(MainActivity.data!![positionEditing])

        startActivity(intent)
        finish()

    }


    fun onDelete(view: View){
        Sounds.selectSound()
        if(saveDialog!=null) {
            saveDialog!!.cancel()
        }


        val deleteWorkRequestBuilder = OneTimeWorkRequest.Builder(deleteWorker::class.java)
        val data = Data.Builder()
        data.putInt("position", positionEditing)
        deleteWorkRequestBuilder.setInputData(data.build())
        deleteWorkRequestBuilder.addTag("delete")

        val deleteWorkRequest = deleteWorkRequestBuilder.build()

        WorkManager.getInstance(this).enqueue(deleteWorkRequest)
/*
        fileNames.removeAt(positionEditing)
        loadedCharacters.removeAt(positionEditing)
        adapter!!.saveFiles.removeAt(positionEditing)
        adapter!!.notifyDataSetChanged()

*/

        WorkManager.getInstance(this)
            .getWorkInfosByTagLiveData("delete")
            .observe(this, Observer<List<WorkInfo>> {
                    workStatusList ->
                val currentWorkStatus = workStatusList ?.getOrNull(0)
                if (currentWorkStatus ?.state ?.isFinished == true)
                {
                    WorkManager.getInstance(this)
                        .getWorkInfosByTagLiveData("delete").removeObservers(this)
                    println("DELETE FINISHED")


                    startActivity(intent)
                    finish()

                }
            })




    }


    fun selectCharacter(characterName: String?): com.glasswellapps.iact.characters.Character {
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


        val intent = Intent(this, MainActivity::class.java)


        startActivity(intent)
        finishAffinity()

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
    : ArrayList<com.glasswellapps.iact.characters.Character>, val data: List<CharacterData>?
)
    : ArrayAdapter<String>(context, R.layout.save_load_item, fileNames){

    open var saveFiles = arrayListOf<View>()

    init{
        val inflater = context.layoutInflater
        for(i in 0..fileNames.size-1){

            val button = inflater.inflate(R.layout.save_load_item, null, true)
            button.alpha = 0f
                val saveNameText = button.findViewById(R.id.save_file_name) as TextView
                saveNameText.text = fileNames[i]

                val saveCharacterImage = button.findViewById(R.id.save_file_image) as ImageView

                saveCharacterImage.setImageDrawable(characters[i].portraitImage)

                var level = 5

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

    open fun getSaveFile(position : Int):View{
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
            MainActivity.data!![positionEditing].deleted = true
           //database!!.getCharacterDAO().update(MainActivity.data!![positionEditing])
            database!!.getCharacterDAO().deleteById(MainActivity.data!![positionEditing].id)
        }
        return Result.success()
    }

}

