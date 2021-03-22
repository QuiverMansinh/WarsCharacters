//package com.example.imperialassault
//
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.text.method.LinkMovementMethod
//import android.view.View
//import android.view.Window
//import kotlinx.android.synthetic.main.activity_character_screen.*
//import kotlinx.android.synthetic.main.credits_to_us.*
//import kotlinx.android.synthetic.main.dialog_background.*
//import kotlinx.android.synthetic.main.dialog_bio.*
//import kotlinx.android.synthetic.main.dialog_options.*
//import kotlinx.android.synthetic.main.dialog_save.*
//import kotlinx.android.synthetic.main.dialog_show_card.*
//import kotlinx.android.synthetic.main.screen_settings.*
//
//class CharacterOptions (val screen: CharacterScreen){
//    var optionsDialog:Dialog?=null
//    var bioDialog: Dialog? = null
//    var backgroundDialog: Dialog? = null
//    var developersCreditsScreen: Dialog? = null
//    val character = screen.character
//
//    init{
//        initOptionsDialog()
//        initBioDialog()
//        initBackgroundDialog()
//        initCreditsScreenDialog()
//        initSaveDialog()
//        initStatsScreenDialog()
//        initSettingsDialog()
//
//    }
//    private fun initOptionsDialog() {
//        optionsDialog = Dialog(screen)
//        optionsDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        optionsDialog!!.setCancelable(false)
//        optionsDialog!!.setContentView(R.layout.dialog_options)
//        optionsDialog!!.setCanceledOnTouchOutside(true)
//        optionsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        optionsDialog!!.bioOption.setOnClickListener {
//            onBiography(optionsDialog!!.bioOption)
//            true
//        }
//        optionsDialog!!.powerOption.setOnClickListener {
//            onPower(optionsDialog!!.powerOption)
//            true
//        }
//        optionsDialog!!.settingsOption.setOnClickListener {
//            onSettings(optionsDialog!!.settingsOption)
//            true
//        }
//        optionsDialog!!.saveOption.setOnClickListener {
//            onSave(optionsDialog!!.saveOption)
//            true
//        }
//        optionsDialog!!.statsOption.setOnClickListener {
//            onStatistics(optionsDialog!!.statsOption)
//            true
//        }
//        optionsDialog!!.backgroundOption.setOnClickListener {
//            onBackground(optionsDialog!!.backgroundOption)
//            true
//        }
//        optionsDialog!!.creditsOption.setOnClickListener {
//            onCredits(optionsDialog!!.backgroundOption)
//            true
//        }
//
//
//    }
//
//    private fun initBioDialog() {
//        bioDialog = Dialog(screen)
//        bioDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        bioDialog!!.setCancelable(false)
//        bioDialog!!.setContentView(R.layout.dialog_bio)
//        bioDialog!!.setCanceledOnTouchOutside(true)
//        bioDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        bioDialog!!.bio_layout.setOnClickListener {
//            Sounds.selectSound()
//            bioDialog!!.dismiss()
//            true
//        }
//    }
//
//    private fun initCreditsScreenDialog() {
//        developersCreditsScreen =
//            Dialog(screen, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
//        developersCreditsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        developersCreditsScreen!!.setCancelable(false)
//        developersCreditsScreen!!.setContentView(R.layout.credits_to_us)
//        developersCreditsScreen!!.setCanceledOnTouchOutside(true)
//        developersCreditsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        developersCreditsScreen!!.textView41.setMovementMethod(LinkMovementMethod.getInstance())
//        developersCreditsScreen!!.textView43.setMovementMethod(LinkMovementMethod.getInstance())
//        developersCreditsScreen!!.textView44.setMovementMethod(LinkMovementMethod.getInstance())
//    }
//
//
//    private fun initBackgroundDialog() {
//        backgroundDialog = Dialog(screen)
//        backgroundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        backgroundDialog!!.setCancelable(false)
//        backgroundDialog!!.setContentView(R.layout.dialog_background)
//        backgroundDialog!!.setCanceledOnTouchOutside(true)
//        backgroundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        backgroundDialog!!.desert_background.setOnClickListener {
//            onBackgroundDesert(backgroundDialog!!.desert_background)
//            //quickSave()
//        }
//        backgroundDialog!!.snow_background.setOnClickListener {
//            onBackgroundSnow(backgroundDialog!!.snow_background)
//            //quickSave()
//        }
//        backgroundDialog!!.jungle_background.setOnClickListener {
//            onBackgroundJungle(backgroundDialog!!.jungle_background)
//            // quickSave()
//        }
//        backgroundDialog!!.interior_background.setOnClickListener {
//            onBackgroundInterior(backgroundDialog!!.interior_background)
//            //  quickSave()
//        }
//    }
//
//    //endregion
//    //************************************************************************************************************
//    //region Options Menu
//    //************************************************************************************************************
//
//    fun onShowOptions(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.show()
//    }
//
//    fun onBiography(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.dismiss()
//        bioDialog!!.bio_title.setText(screen.character.bio_title)
//        bioDialog!!.bio_quote.setText(screen.character.bio_quote)
//        bioDialog!!.bio_text.setText(screen.character.bio_text)
//        bioDialog!!.show()
//    }
//
//    fun onPower(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.dismiss()
//        if (!screen.isWounded) {
//            showCardDialog!!.card_image.setImageBitmap(character.power)
//        } else {
//            showCardDialog!!.card_image.setImageBitmap(character.power_wounded)
//        }
//
//        showCardDialog!!.show()
//    }
//
//    fun onSave(view: View) {
//        Sounds.selectSound()
//        if (character.file_name.equals("autosave")) {
//            saveDialog!!.show()
//        } else {
//
//            quickSave()
//        }
//        optionsDialog!!.dismiss()
//
//    }
//
//    fun onBackground(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.dismiss()
//        backgroundDialog!!.show()
//    }
//
//    fun onSettings(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.dismiss()
//        settingsScreen!!.imageSetting.visibility = View.INVISIBLE
//        settingsScreen!!.settingsMenu.visibility = View.VISIBLE
//
//        settingsScreen!!.toggleDamageAnim!!.isChecked = animateDamage
//        settingsScreen!!.toggleConditionAnim!!.isChecked = animateConditions
//        settingsScreen!!.toggleActionUsage!!.isChecked = actionUsage
//        settingsScreen!!.soundEffectsVolume.progress = (character.soundEffectsSetting*100).toInt()
//
//        when (character.imageSetting) {
//            -1 -> {
//                settingsScreen!!.imageSettingButton.text = "AUTO"
//            }
//            0 -> {
//                settingsScreen!!.imageSettingButton.text = "DEFAULT"
//            }
//            1 -> {
//                settingsScreen!!.imageSettingButton.text = "TIER 1"
//            }
//            2 -> {
//                settingsScreen!!.imageSettingButton.text = "TIER 2"
//            }
//            3 -> {
//                settingsScreen!!.imageSettingButton.text = "TIER 3"
//            }
//        }
//        settingsScreen!!.show()
//
//    }
//
//    fun onStatistics(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.dismiss()
//        initStatsScreen()
//        statsScreen!!.show()
//    }
//
//    fun onCredits(view: View) {
//        Sounds.selectSound()
//        optionsDialog!!.dismiss()
//        developersCreditsScreen!!.show()
//    }
//
//
//    //Backgrounds
//    fun onBackgroundSnow(view: View) {
//        Sounds.selectSound()
//        character.background = "snow"
//        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_snow.jpg"))
//        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_snow.png"))
//    }
//
//    fun onBackgroundJungle(view: View) {
//        Sounds.selectSound()
//        character.background = "jungle"
//        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_jungle.jpg"))
//        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_jungle.png"))
//    }
//
//    fun onBackgroundDesert(view: View) {
//        Sounds.selectSound()
//        character.background = "desert"
//        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_desert.jpg"))
//        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_desert.png"))
//    }
//
//    fun onBackgroundInterior(view: View) {
//        Sounds.selectSound()
//        character.background = "interior"
//        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_interior.jpg"))
//        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_interior.png"))
//    }
//
//    private fun initStatsScreenDialog() {
//        statsScreen = Dialog(screen)
//        statsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        statsScreen!!.setCancelable(false)
//        statsScreen!!.setContentView(R.layout.screen_stats)
//        statsScreen!!.setCanceledOnTouchOutside(true)
//        statsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        screen.statsScreen = statsScreen
//    }
//
//    //TODO convert to work, save stats, save random, save custom image?
//    private fun initSaveDialog() {
//        saveDialog = Dialog(screen)
//        saveDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        saveDialog!!.setCancelable(false)
//        saveDialog!!.setContentView(R.layout.dialog_save)
//        saveDialog!!.setCanceledOnTouchOutside(true)
//        saveDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        saveDialog!!.save_button.setOnClickListener {
//            if (character.file_name.equals("autosave")) {
//
//                firstManualSave()
//            } else {
//                quickSave()
//            }
//            Sounds.selectSound()
//            saveDialog!!.dismiss()
//            true
//        }
//        saveDialog!!.cancel_button.setOnClickListener {
//            Sounds.selectSound()
//            saveDialog!!.dismiss()
//            true
//        }
//    }
//}