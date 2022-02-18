package com.glasswellapps.iact.imperial
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.glasswellapps.iact.loading.LoadedCharacter
import com.glasswellapps.iact.R
import com.glasswellapps.iact.character_screen.XPScreen
import com.glasswellapps.iact.character_screen.controllers.BackgroundController
import com.glasswellapps.iact.character_screen.views.BioView
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.inventory.*
import kotlinx.android.synthetic.main.dialog_imperial_options.*

class ImperialOptions(private var imperialScreen: ImperialScreen) {
    private val optionsMenu:Dialog = Dialog(imperialScreen)
    private val backgroundMenu = BackgroundController(imperialScreen)
    private val conditionController = ImperialConditions(imperialScreen)
    private val bioView= BioView(imperialScreen)
    init{
        optionsMenu.requestWindowFeature(Window.FEATURE_NO_TITLE)
        optionsMenu.setCancelable(false)
        optionsMenu.setContentView(R.layout.dialog_imperial_options)
        optionsMenu.setCanceledOnTouchOutside(true)
        optionsMenu.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        optionsMenu.imperial_background.setOnClickListener { onBackground() }
        optionsMenu.imperial_rewards_button.setOnClickListener { onReward() }
        optionsMenu.imperial_accessory_button.setOnClickListener { onAccessory() }
        optionsMenu.imperial_armour_button.setOnClickListener { onArmour() }
        optionsMenu.imperial_melee_button.setOnClickListener { onMelee() }
        optionsMenu.imperial_ranged_button.setOnClickListener{onRange()}
        optionsMenu.imperial_xp_button.setOnClickListener { onXPScreen() }
        optionsMenu.imperial_condition_button.setOnClickListener { onCondition() }
        optionsMenu.imperial_bio_button.setOnClickListener { onBio() }
        optionsMenu.imperial_remove_button.setOnClickListener { onRemove() }
    }
    fun onSelect(){
        Sounds.selectSound()
        optionsMenu.dismiss()
    }
    fun onNavigate(){
        onSelect()
        LoadedCharacter.setIsInteractable(player.isLocal)
        LoadedCharacter.setActiveCharacter(player.character)
    }
    fun onReward() {
       onNavigate()
        val intent = Intent(imperialScreen, RewardsScreen::class.java)
        imperialScreen.startActivity(intent)
    }
    private fun onAccessory() {
        onNavigate()
        val intent = Intent(imperialScreen, AccScreen::class.java)
        imperialScreen.startActivity(intent)
    }
    private fun onArmour() {
        val intent = Intent(imperialScreen, ArmorScreen::class.java)
        onNavigate()
        imperialScreen.startActivity(intent)
    }
    private fun onMelee() {
        onNavigate()
        val intent = Intent(imperialScreen, MeleeScreen::class.java)
        imperialScreen.startActivity(intent)
    }
    private fun onRange() {
        val intent = Intent(imperialScreen, RangedScreen::class.java)
        onNavigate()
        imperialScreen.startActivity(intent)
    }
    private fun onXPScreen() {
        val intent = Intent(imperialScreen, XPScreen::class.java)
        onNavigate()
        imperialScreen.startActivity(intent)
    }
    private fun onBackground(){
        onSelect()
        backgroundMenu.showDialog(player.character, player.view.backgroundView, player.view.camouflageView)
    }
    private fun onBio(){
        onSelect()
        bioView.showDialog(player.character)
    }
    private fun onCondition(){
        onSelect()
        conditionController.onAddCondition(player)
    }
    private fun onRemove(){
        onSelect()
    }

    private lateinit var player: NetworkPlayer

    fun onShowDialog(player: NetworkPlayer) {
        if(player.character != null) {
            this.player = player
            optionsMenu.player_name.text = player.character.name.toUpperCase()
            optionsMenu.player_level.text = "Level " + player.character.getLevel()
            player.character.loadPortraitImage(imperialScreen)
            optionsMenu.player_portrait.setImageDrawable(player.character.portraitImage)
            Sounds.buttonSound()

            if (player.isLocal){
                optionsMenu.imperial_condition_button.isClickable = true
                optionsMenu.imperial_background.isClickable = true
                optionsMenu.imperial_condition_button.alpha = 1f
                optionsMenu.imperial_background.alpha = 1f
            }
            else {
                optionsMenu.imperial_condition_button.isClickable = false
                optionsMenu.imperial_background.isClickable = false
                optionsMenu.imperial_condition_button.alpha = 0.1f
                optionsMenu.imperial_background.alpha = 0.1f
            }

            optionsMenu.show()
        }
        else{
            Sounds.negativeSound()
        }
    }
}