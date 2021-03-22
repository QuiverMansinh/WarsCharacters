//package com.example.imperialassault
//
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.view.View
//import android.view.Window
//import android.widget.SeekBar
//import kotlinx.android.synthetic.main.screen_settings.*
//
//class CharacterSettingsScreen(val screen: CharacterScreen) {
//    var settingsScreen:Dialog? = null
//    val character = screen.character
//    init{
//        initSettingsDialog()
//    }
//    private fun initSettingsDialog() {
//        settingsScreen = Dialog(screen)
//        settingsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        settingsScreen!!.setCancelable(false)
//        settingsScreen!!.setContentView(R.layout.screen_settings)
//        settingsScreen!!.setCanceledOnTouchOutside(true)
//        settingsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        settingsScreen!!.toggleDamageAnim!!.isChecked = screen.animateDamage
//        settingsScreen!!.toggleDamageAnim.setOnClickListener {
//            screen.animateDamage = settingsScreen!!.toggleDamageAnim!!.isChecked
//            character.damageAnimSetting = screen.animateDamage
//            Sounds.selectSound()
//        }
//
//        settingsScreen!!.toggleConditionAnim!!.isChecked = screen.animateConditions
//        settingsScreen!!.toggleConditionAnim.setOnClickListener {
//            screen.animateConditions = settingsScreen!!.toggleConditionAnim!!.isChecked
//            character.conditionAnimSetting = screen.animateConditions
//            Sounds.selectSound()
//            screen.updateConditionIcons()
//        }
//
//        settingsScreen!!.toggleActionUsage.isChecked = screen.actionUsage
//        settingsScreen!!.toggleActionUsage.setOnClickListener {
//            screen.actionUsage = settingsScreen!!.toggleActionUsage.isChecked
//            character.actionUsageSetting = screen.actionUsage
//            Sounds.selectSound()
//            if (screen.actionUsage) {
//                if (screen.activated) {
//                    screen.turnOnActionButtons()
//                }
//            } else {
//                screen.turnOffActionButtons()
//            }
//        }
//        settingsScreen!!.imageSettingButton.setOnClickListener {
//            settingsScreen!!.imageSetting.visibility = View.VISIBLE
//            setPreviewImage(character.imageSetting)
//            settingsScreen!!.settingsMenu.visibility = View.INVISIBLE
//
//        }
//        settingsScreen!!.imagePreview.setOnClickListener {
//            settingsScreen!!.imageSetting.visibility = View.INVISIBLE
//            settingsScreen!!.settingsMenu.visibility = View.VISIBLE
//            Sounds.selectSound()
//        }
//        settingsScreen!!.imageAuto.setOnClickListener {
//            setPreviewImage(-1)
//        }
//        settingsScreen!!.imageDefault.setOnClickListener {
//            setPreviewImage(0)
//        }
//        settingsScreen!!.imageTier1.setOnClickListener {
//            setPreviewImage(1)
//        }
//        settingsScreen!!.imageTier2.setOnClickListener {
//            setPreviewImage(2)
//        }
//        settingsScreen!!.imageTier3.setOnClickListener {
//            setPreviewImage(3)
//        }
//
//        settingsScreen!!.soundEffectsVolume.progress = (character.soundEffectsSetting*100).toInt()
//        settingsScreen!!.soundEffectsVolume.setOnSeekBarChangeListener(object : SeekBar
//        .OnSeekBarChangeListener{
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                Sounds.selectSound()
//                character.soundEffectsSetting = p1.toFloat()/100
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//
//
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//
//            }
//
//        })
//        screen.settingsScreen = settingsScreen
//    }
//
//    private fun setPreviewImage(setting:Int) {
//        Sounds.selectSound()
//        character.imageSetting = setting
//
//        character.updateCharacterImages(screen)
//        settingsScreen!!.imagePreview.setImageBitmap(character.currentImage)
//        settingsScreen!!.imageAuto.alpha = 0.5f
//        settingsScreen!!.imageDefault.alpha = 0.5f
//        settingsScreen!!.imageTier1.alpha = 0.5f
//        settingsScreen!!.imageTier2.alpha = 0.5f
//        settingsScreen!!.imageTier3.alpha = 0.5f
//
//        when (setting) {
//            -1 -> {
//                settingsScreen!!.imageAuto.alpha = 1f
//                settingsScreen!!.imageSettingButton.text = "AUTO"
//            }
//            0 -> {
//                settingsScreen!!.imageDefault.alpha = 1f
//                settingsScreen!!.imageSettingButton.text = "DEFAULT"
//            }
//            1 -> {
//                settingsScreen!!.imageTier1.alpha = 1f
//                settingsScreen!!.imageSettingButton.text = "TIER 1"
//            }
//            2 -> {
//                settingsScreen!!.imageTier2.alpha = 1f
//                settingsScreen!!.imageSettingButton.text = "TIER 2"
//            }
//            3 -> {
//                settingsScreen!!.imageTier3.alpha = 1f
//                settingsScreen!!.imageSettingButton.text = "TIER 3"
//            }
//        }
//    }
//}