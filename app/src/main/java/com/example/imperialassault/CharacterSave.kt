//package com.example.imperialassault
//
//import android.app.Dialog
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.view.Window
//import androidx.lifecycle.Observer
//import androidx.work.*
//import kotlinx.android.synthetic.main.dialog_save.*
//
//class CharacterSave (val screen: CharacterScreen) {
//
//    //endregion
//    //************************************************************************************************************
//    //region Saving
//    //************************************************************************************************************
//    var saveDialog:Dialog?=null
//    val character = screen.character
//    init{
//        initSaveDialog()
//    }
//
//
//
//    fun quickSave() {
//        //if(secondsSinceLastSave > 3) {
//        //val character = MainActivity.selectedCharacter
//
//        val saveWorkRequestBuilder = OneTimeWorkRequest.Builder(saveWorker::class.java)
//        val data = Data.Builder()
//
//        saveWorkRequestBuilder.setInputData(data.build())
//        saveWorkRequestBuilder.addTag("save")
//
//        val saveWorkRequest =saveWorkRequestBuilder.build()
//
//        WorkManager.getInstance(screen).enqueue(saveWorkRequest)
//        WorkManager.getInstance(screen)
//            .getWorkInfosByTagLiveData("save")
//            .observe(screen, Observer<List<WorkInfo>> {
//                    workStatusList ->
//                val currentWorkStatus = workStatusList ?.getOrNull(0)
//                if (currentWorkStatus ?.state ?.isFinished == true)
//                {
//                    WorkManager.getInstance(screen)
//                        .getWorkInfosByTagLiveData("save").removeObservers(screen)
//                    println("Save Finished")
//
//
//                }
//            })
//
//    }
//
//    fun firstManualSave() {
//
//        println("FIRST MANUAL SAVE character " + screen.character)
//        MainActivity.selectedCharacter!!.file_name = "" + screen.saveDialog!!.save_name.text.toString()
//        quickSave()
//
//
//
//    }
//
//
//
//
//
//
//
//
//
//    fun convertItemIDToString(itemIds: ArrayList<Int>): String {
//        var itemString = ""
//        for (i in 0..itemIds.size - 1) {
//            itemString += "," + itemIds[i]
//        }
//        return itemString
//    }
////endregion
//
//}
//
//class saveWorker(val context: Context, params: WorkerParameters): Worker
//    (context,
//    params){
//
//
//
//    override fun doWork(): Result {
//        val database = AppDatabase.getInstance(context)
//        var saveFile = getCharacterData(MainActivity.selectedCharacter!!.file_name)
//        if (MainActivity.selectedCharacter!!.id != -1) {
//            saveFile.id = MainActivity.selectedCharacter!!.id
//            database!!.getCharacterDAO().update(saveFile)
//            println("update save")
//        } else {
//
//            MainActivity.selectedCharacter!!.id=database!!.getCharacterDAO().getPrimaryKey(database!!.getCharacterDAO().insert(saveFile))
//            println("insert save")
//
//        }
//
//        return Result.success()
//    }
//
//    fun getCharacterData(fileName: String): CharacterData {
//        val character = MainActivity.selectedCharacter!!;
//        var data = CharacterData(
//            fileName,
//            System.currentTimeMillis(),
//            character.name_short,
//            character.damage,
//            character.strain,
//            character.token,
//            character.wounded,
//            character.conditionsActive[0],
//            character.conditionsActive[1],
//            character.conditionsActive[2],
//            character.conditionsActive[3],
//            character.conditionsActive[4],
//            character.totalXP,
//            character.xpSpent,
//            character.xpCardsEquipped[0],
//            character.xpCardsEquipped[1],
//            character.xpCardsEquipped[2],
//            character.xpCardsEquipped[3],
//            character.xpCardsEquipped[4],
//            character.xpCardsEquipped[5],
//            character.xpCardsEquipped[6],
//            character.xpCardsEquipped[7],
//            character.xpCardsEquipped[8],
//            character.weapons.getOrElse(0) { -1 },
//            character.weapons.getOrElse(1) { -1 },
//            character.accessories.getOrElse(0) { -1 },
//            character.accessories.getOrElse(1) { -1 },
//            character.accessories.getOrElse(2) { -1 },
//            character.helmet,
//            character.armor.getOrElse(0) { -1 },
//            convertItemIDToString(character.mods),
//            convertItemIDToString(character.rewards),
//            character.background,
//            character.conditionsActive[0],
//            character.conditionsActive[1],
//            character.conditionsActive[2],
//            character.conditionsActive[3],
//            character.conditionsActive[4],
//            character.killCount[0],
//            character.killCount[1],
//            character.killCount[2],
//            character.killCount[3],
//            character.killCount[4],
//            character.killCount[5],
//            character.killCount[6],
//            character.killCount[7],
//            character.assistCount[0],
//            character.assistCount[1],
//            character.assistCount[2],
//            character.assistCount[3],
//            character.assistCount[4],
//            character.assistCount[5],
//            character.assistCount[6],
//            character.assistCount[7],
//            character.movesTaken,
//            character.attacksMade,
//            character.interactsUsed,
//            character.timesWounded,
//            character.timesRested,
//            character.timesWithdrawn,
//            character.activated,
//            character.damageTaken,
//            character.strainTaken,
//            character.specialActions,
//            character.timesFocused,
//            character.timesHidden,
//            character.timesStunned,
//            character.timesBleeding,
//            character.timesWeakened,
//            character.cratesPickedUp,
//            character.rewardObtained,
//            character.withdrawn,
//
//            character.damageAnimSetting,
//            character.conditionAnimSetting,
//            character.actionUsageSetting,
//            character.soundEffectsSetting,
//            character.imageSetting,
//
//            false
//        )
//
//
//        return data
//    }
//
//    fun convertItemIDToString(itemIds: ArrayList<Int>): String {
//        var itemString = ""
//        for (i in 0..itemIds.size - 1) {
//            itemString += "," + itemIds[i]
//        }
//        return itemString
//    }
//
//}