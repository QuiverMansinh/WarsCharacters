package com.glasswellapps.iact.character_screen.controllers

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.glasswellapps.iact.R
import com.glasswellapps.iact.character_screen.SaveWorker
import com.glasswellapps.iact.characters.Character
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.effects.WorkingAnimations
import com.glasswellapps.iact.loading.LoadedCharacter
import kotlinx.android.synthetic.main.dialog_save.*

open class SavingController (val context:Activity, protected val saving_icon: View){
    private val saveDialog = Dialog(context)

    lateinit var character:Character
    init{
        saveDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        saveDialog.setCancelable(false)
        saveDialog.setContentView(R.layout.dialog_save)
        saveDialog.setCanceledOnTouchOutside(true)
        saveDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        saveDialog.save_button.setOnClickListener {
            if(character != null) {
                if (character.file_name == "autosave") {
                    firstManualSave()
                } else {
                    quickSave()
                }
            }
            Sounds.selectSound()
            saveDialog.dismiss()
        }
        saveDialog.cancel_button.setOnClickListener {
            Sounds.selectSound()
            saveDialog.dismiss()
        }
        LoadedCharacter.clearCharactersToSave()

    }
    fun addCharacterToSave(character: Character){
        if(character==null){return}
        LoadedCharacter.addCharacterToSave(character)
    }

    fun showSaveDialog(){
        saveDialog.show()
    }
    open fun quickSave() {

        //if(secondsSinceLastSave > 3) {
        //val character = MainActivity.selectedCharacter
        onSavingStarted()
        val saveWorkRequestBuilder = OneTimeWorkRequest.Builder(SaveWorker::class.java)
        val data = Data.Builder()
        saveWorkRequestBuilder.setInputData(data.build())
        saveWorkRequestBuilder.addTag("save")

        val saveWorkRequest =saveWorkRequestBuilder.build()

        WorkManager.getInstance(context).enqueue(saveWorkRequest)
        WorkManager.getInstance(context)
            .getWorkInfosByTagLiveData("save")
            .observe(context as LifecycleOwner, Observer<List<WorkInfo>> {
                    workStatusList ->
                val currentWorkStatus = workStatusList ?.getOrNull(0)
                if (currentWorkStatus ?.state ?.isFinished == true)
                {
                    WorkManager.getInstance(context)
                        .getWorkInfosByTagLiveData("save").removeObservers(context as LifecycleOwner)
                    onSavingFinished()
                }
            })
    }
    protected open fun onSavingFinished(){
        WorkingAnimations.stopAnimation()
    }
    protected open fun onSavingStarted(){
        WorkingAnimations.startSpinningAnimation(saving_icon)
    }


    private fun firstManualSave() {
        if(character==null){return}
        println("FIRST MANUAL SAVE character $character")
        character.file_name = "" + saveDialog.save_name.text.toString()
        quickSave()
    }

    fun clearCharactersToSave() {
        LoadedCharacter.clearCharactersToSave()
    }
}