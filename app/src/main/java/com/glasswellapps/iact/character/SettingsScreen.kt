package com.glasswellapps.iact.character
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.SeekBar
import com.glasswellapps.iact.R
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.screen_settings.*

class SettingsScreen (val characterScreen: CharacterScreen){
    var settingsScreen: Dialog = Dialog(characterScreen)
    var character = characterScreen.character

    init{
        settingsScreen.requestWindowFeature(Window.FEATURE_NO_TITLE)
        settingsScreen.setCancelable(false)
        settingsScreen.setContentView(R.layout.screen_settings)
        settingsScreen.setCanceledOnTouchOutside(true)
        settingsScreen.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        settingsScreen.toggleDamageAnim.isChecked =  character.damageAnimSetting
        settingsScreen.toggleDamageAnim.setOnClickListener {
            character.damageAnimSetting = settingsScreen.toggleDamageAnim.isChecked
            Sounds.selectSound()
        }

        settingsScreen.toggleConditionAnim.isChecked = character.conditionAnimSetting
        settingsScreen.toggleConditionAnim.setOnClickListener {
            character.conditionAnimSetting = settingsScreen.toggleConditionAnim.isChecked
            Sounds.selectSound()
            characterScreen.conditions.updateConditionIcons()
        }

        settingsScreen.toggleActionUsage.isChecked = character.actionUsageSetting
        settingsScreen.toggleActionUsage.setOnClickListener {
            character.actionUsageSetting = settingsScreen.toggleActionUsage.isChecked
            Sounds.selectSound()
            if (character.actionUsageSetting) {
                if (character.isActivated) {
                    characterScreen.turnOnActionButtons()
                }
            } else {
                characterScreen.turnOffActionButtons()
            }
        }
        settingsScreen.imageSettingButton.setOnClickListener {
            settingsScreen.imageSetting.visibility = View.VISIBLE
            setPreviewImage(character.imageSetting)
            settingsScreen.settingsMenu.visibility = View.INVISIBLE

        }
        settingsScreen.imagePreview.setOnClickListener {
            settingsScreen.imageSetting.visibility = View.INVISIBLE
            settingsScreen.settingsMenu.visibility = View.VISIBLE
            Sounds.selectSound()
        }
        settingsScreen.imageAuto.setOnClickListener {
            setPreviewImage(-1)
        }
        settingsScreen.imageDefault.setOnClickListener {
            setPreviewImage(0)
        }
        settingsScreen.imageTier1.setOnClickListener {
            setPreviewImage(1)
        }
        settingsScreen.imageTier2.setOnClickListener {
            setPreviewImage(2)
        }
        settingsScreen.imageTier3.setOnClickListener {
            setPreviewImage(3)
        }

        settingsScreen.soundEffectsVolume.progress = (character.soundEffectsSetting*100).toInt()
        settingsScreen.soundEffectsVolume.setOnSeekBarChangeListener(object : SeekBar
        .OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Sounds.selectSound()
                character.soundEffectsSetting = p1.toFloat()/100
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        settingsScreen.soundEffectsVolume.progress = (character.soundEffectsSetting*100).toInt()
    }
    private fun setPreviewImage(setting:Int) {
        Sounds.selectSound()
        character.imageSetting = setting

        character.updateCharacterImages(characterScreen)
        settingsScreen.imagePreview.setImageBitmap(character.currentImage)
        settingsScreen.imageAuto.alpha = 0.5f
        settingsScreen.imageDefault.alpha = 0.5f
        settingsScreen.imageTier1.alpha = 0.5f
        settingsScreen.imageTier2.alpha = 0.5f
        settingsScreen.imageTier3.alpha = 0.5f

        when (setting) {
            -1 -> {
                settingsScreen.imageAuto.alpha = 1f
                settingsScreen.imageSettingButton.text = "AUTO"
            }
            0 -> {
                settingsScreen.imageDefault.alpha = 1f
                settingsScreen.imageSettingButton.text = "DEFAULT"
            }
            1 -> {
                settingsScreen.imageTier1.alpha = 1f
                settingsScreen.imageSettingButton.text = "TIER 1"
            }
            2 -> {
                settingsScreen.imageTier2.alpha = 1f
                settingsScreen.imageSettingButton.text = "TIER 2"
            }
            3 -> {
                settingsScreen.imageTier3.alpha = 1f
                settingsScreen.imageSettingButton.text = "TIER 3"
            }
        }
    }

    fun showDialog() {
        settingsScreen!!.imageSetting.visibility = View.INVISIBLE
        settingsScreen!!.settingsMenu.visibility = View.VISIBLE

        settingsScreen!!.toggleDamageAnim!!.isChecked = character.damageAnimSetting
        settingsScreen!!.toggleConditionAnim!!.isChecked = character.conditionAnimSetting
        settingsScreen!!.toggleActionUsage!!.isChecked = character.actionUsageSetting
        settingsScreen!!.soundEffectsVolume.progress = (character.soundEffectsSetting*100).toInt()

        when (character.imageSetting) {
            -1 -> {
                settingsScreen!!.imageSettingButton.text = "AUTO"
            }
            0 -> {
                settingsScreen!!.imageSettingButton.text = "DEFAULT"
            }
            1 -> {
                settingsScreen!!.imageSettingButton.text = "TIER 1"
            }
            2 -> {
                settingsScreen!!.imageSettingButton.text = "TIER 2"
            }
            3 -> {
                settingsScreen!!.imageSettingButton.text = "TIER 3"
            }
        }
        settingsScreen!!.show()

    }
}