package com.example.imperialassault
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

import kotlinx.android.synthetic.main.activity_character_view.*

import kotlinx.android.synthetic.main.dialog_action_menu.*
import kotlinx.android.synthetic.main.dialog_assist.*
import kotlinx.android.synthetic.main.dialog_bio.*
import kotlinx.android.synthetic.main.dialog_conditions.*
import kotlinx.android.synthetic.main.dialog_options.*
import kotlinx.android.synthetic.main.dialog_save.*
import kotlinx.android.synthetic.main.dialog_show_card.*

import kotlinx.android.synthetic.main.screen_stats.*
import kotlinx.android.synthetic.main.screen_xp_select.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.random.Random

var height = 0f
var width = 0f

class Character_view : AppCompatActivity() {
    var character:Character = Character();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_view)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()



        initDialogs()
        initScreen()
        initAnimations()
        initKillTracker()
    }
    //************************************************************************************************************
    //region Main Screen
    //************************************************************************************************************
    var isWounded = false

    fun initScreen(){
        var load = intent.getBooleanExtra("Load", false)
        var characterName: String = intent.getStringExtra("CharacterName").toString()

        if (!load) {
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
            MainActivity.selectedCharacter = character

        } else {
            character = MainActivity.selectedCharacter!!
        }

        name.setText("" + character.name);


        when (character.defence_dice) {
            "white" -> ImageViewCompat.setImageTintList(
                defence,
                ColorStateList.valueOf(Color.WHITE)
            )
            "black" -> ImageViewCompat.setImageTintList(
                defence,
                ColorStateList.valueOf(Color.BLACK)
            )
            "" -> defence.visibility = View.INVISIBLE

        }
        setDiceColor(strength1, character.strength[0]);
        setDiceColor(strength2, character.strength[1]);
        setDiceColor(strength3, character.strength[2]);

        setDiceColor(insight1, character.insight[0]);
        setDiceColor(insight2, character.insight[1]);
        setDiceColor(insight3, character.insight[2]);

        setDiceColor(tech1, character.tech[0]);
        setDiceColor(tech2, character.tech[1]);
        setDiceColor(tech3, character.tech[2]);

        character_type.setText(character.type)

        updateImages()
        updateStats()
        initDamageAndStrain()
        updateStats()

        initConditions()
        updateConditionIcons()

        if(character.name_short == "jarrod"){
            companion.visibility = View.VISIBLE
        }
        else{
            companion.visibility = View.GONE
        }

        background_image.setImageBitmap(character.getBackgroundImage(this))

    }

    fun setDiceColor(dice: ImageView, color: Char) {
        dice.visibility = ImageView.VISIBLE
        when(color){
            'B' -> ImageViewCompat.setImageTintList(
                dice, ColorStateList.valueOf(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dice_blue
                    )
                )
            )
            'G' -> ImageViewCompat.setImageTintList(
                dice, ColorStateList.valueOf(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dice_green
                    )
                )
            )
            'Y' -> ImageViewCompat.setImageTintList(
                dice, ColorStateList.valueOf(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dice_yellow
                    )
                )
            )
            'R' -> ImageViewCompat.setImageTintList(
                dice, ColorStateList.valueOf(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dice_red
                    )
                )
            )
            ' ' -> dice.visibility = ImageView.GONE
        }
    }

    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var bitmap:Bitmap? = null
        val options = BitmapFactory.Options()
        for(i in 1..32) {
            try {
                inputStream = assetManager.open(path)
                options.inSampleSize = i
                println(i)
                bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                break
            } catch (outOfMemoryError: OutOfMemoryError) {

                println("next size" + i)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    open fun updateImages(){
        character.updateCharacterImages(this)
        character_image.setImageBitmap(character.currentImage)
        if(character.layer1OnTop){
            character_layer1.elevation=1f
        }

        if(character.layer2 != null){
            character_layer2.visibility = View.VISIBLE
            character_layer2.foreground = BitmapDrawable(resources, character.layer2);
        }
        else{
            character_layer2.visibility = View.GONE
        }

        if(character.layer1 != null){
            character_layer1.visibility = View.VISIBLE
            character_layer1.foreground = BitmapDrawable(resources, character.layer1);
        }
        else{
            character_layer1.visibility = View.GONE
        }
    }

    fun onShowCompanionCard(view: View) {
        showCardDialog!!.card_image.setImageBitmap(character.companionCard)
        showCardDialog!!.show()
    }

    open fun updateStats(){
        character.health= character.health_default
        character.endurance = character.endurance_default
        character.speed = character.speed_default
        health.setTextColor(Color.BLACK)
        endurance.setTextColor(Color.BLACK)
        speed.setTextColor(Color.BLACK)

        for(i in 0..character.xpCardsEquipped.size-1){
            if(character.xpCardsEquipped[i]){
                if(character.xpHealths[i]!=0) {
                    character.health += character.xpHealths[i]
                    health.setTextColor(Color.GREEN)
                }
                if(character.xpEndurances[i]!=0) {
                    character.endurance += character.xpEndurances[i]
                    endurance.setTextColor(Color.GREEN)
                }
                if(character.xpSpeeds[i]!=0) {
                    character.speed += character.xpSpeeds[i]
                    speed.setTextColor(Color.GREEN)
                }
            }
        }
        if(isWounded){
            character.endurance--
            endurance.setTextColor(Color.RED)
            character.speed--
            speed.setTextColor(Color.RED)
        }

        health.setText("" + character.health);
        endurance.setText("" + character.endurance);
        speed.setText("" + character.speed);
    }

    fun onHide(view: View){
        view.visibility = View.INVISIBLE
    }



    //endregion
    //************************************************************************************************************
    //region Damage and Strain
    //************************************************************************************************************

    fun onAddStrain(view: View) {
        if(character.strain < character.endurance) {
            character.strain++
            character.strainTaken++

            if(minus_strain.alpha==0f){
                minus_strain.animate().alpha(1f)
            }

            add_strain.setText("" + character.strain)

        }
        else{
            addDamage()

        }
        playRestAnim()

    }
    fun onMinusStrain(view: View) {
        if(character.strain >0) {
            character.strain--
            add_strain.setText("" + character.strain)
        }
        if(character.strain==0){
            if(minus_strain.alpha>0f){
                minus_strain.animate().alpha(0f)
            }
        }

    }
    fun onAddDamage(view: View) {
        if(addDamage()) {
            playDamageAnim()

        }
    }
    fun addDamage():Boolean{


        if(character.damage < character.health*2) {
            character.damage++
            character.damageTaken++

            if(minus_damage.alpha==0f){
                minus_damage.animate().alpha(1f)
            }

            if(character.damage < character.health) {

                add_damage.setText("" + character.damage)
            }
            else if(character.damage < character.health*2){

                character.wounded = character.damage-character.health
                add_damage.setText("" + character.wounded)
                if(!isWounded) {
                    wounded.animate().alpha(1f)

                    setDiceColor(strength1, character.strengthWounded[0]);
                    setDiceColor(strength2, character.strengthWounded[1]);
                    setDiceColor(strength3, character.strengthWounded[2]);

                    setDiceColor(insight1, character.insightWounded[0]);
                    setDiceColor(insight2, character.insightWounded[1]);
                    setDiceColor(insight3, character.insightWounded[2]);

                    setDiceColor(tech1, character.techWounded[0]);
                    setDiceColor(tech2, character.techWounded[1]);
                    setDiceColor(tech3, character.techWounded[2]);


                    character.timesWounded++
                    isWounded = true
                    updateStats()
                }

            }
            else{
                character.withdrawn = true
                character.timesWithdrawn++
                add_damage.setText("" + character.health)
                val slide = ObjectAnimator.ofFloat(
                    character_images, "translationY", 0f, 0f,
                    character_image.height.toFloat()
                )
                slide.setDuration(1500)
                slide.start()
            }

            var hitY = ObjectAnimator.ofFloat(
                character_images, "translationY", 0f, 20f * Random
                    .nextFloat(),
                0f, 20f * Random.nextFloat(), 0f, 20f * Random.nextFloat(), 0f
            )
            hitY .setDuration(300)
            hitY .start()

            var hitX = ObjectAnimator.ofFloat(
                character_images, "translationX", 0f, -10f * Random
                    .nextFloat(),
                0f, -10f * Random.nextFloat(), 0f, -10f * Random.nextFloat(), 0f
            )
            hitX .setDuration(300)
            hitX .start()

            return true
        }
        return false
    }



    fun onMinusDamage(view: View) {
        if(character.damage >0) {

            character.damage--
            character.withdrawn = false
            if(character.damage < character.health) {
                add_damage.setText("" + character.damage)
                if(isWounded) {
                    character.wounded = 0
                    wounded.animate().alpha(0f)

                    setDiceColor(strength1, character.strength[0]);
                    setDiceColor(strength2, character.strength[1]);
                    setDiceColor(strength3, character.strength[2]);

                    setDiceColor(insight1, character.insight[0]);
                    setDiceColor(insight2, character.insight[1]);
                    setDiceColor(insight3, character.insight[2]);

                    setDiceColor(tech1, character.tech[0]);
                    setDiceColor(tech2, character.tech[1]);
                    setDiceColor(tech3, character.tech[2]);

                    isWounded = false
                    updateStats()
                }
            }
            else if(character.damage < character.health*2){
                character.wounded = character.damage-character.health
                add_damage.setText("" + character.wounded)
                val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
                slide.setDuration(500)
                slide.start()
            }

        }
        if(character.damage==0){
            if(minus_damage.alpha>0f){
                minus_damage.animate().alpha(0f)
            }
        }
    }
    fun onUnwound(view: View) {
        playRestAnim()
        character.damage = 0
        character.wounded = 0
        add_damage.setText("" + character.damage)
        wounded.animate().alpha(0f)

        setDiceColor(strength1, character.strength[0]);
        setDiceColor(strength2, character.strength[1]);
        setDiceColor(strength3, character.strength[2]);

        setDiceColor(insight1, character.insight[0]);
        setDiceColor(insight2, character.insight[1]);
        setDiceColor(insight3, character.insight[2]);

        setDiceColor(tech1, character.tech[0]);
        setDiceColor(tech2, character.tech[1]);
        setDiceColor(tech3, character.tech[2]);

        isWounded = false
        updateStats()
        unwoundDialog!!.cancel()
    }
    fun initDamageAndStrain() {
        if(character.damage>0) {
            if (minus_damage.alpha == 0f) {
                minus_damage.animate().alpha(1f)
            }

            if (character.damage < character.health) {
                add_damage.setText("" + character.damage)

            } else {
                character.wounded = character.damage - character.health
                add_damage.setText("" + character.wounded)
                wounded.animate().alpha(1f)

                setDiceColor(strength1, character.strengthWounded[0]);
                setDiceColor(strength2, character.strengthWounded[1]);
                setDiceColor(strength3, character.strengthWounded[2]);

                setDiceColor(insight1, character.insightWounded[0]);
                setDiceColor(insight2, character.insightWounded[1]);
                setDiceColor(insight3, character.insightWounded[2]);

                setDiceColor(tech1, character.techWounded[0]);
                setDiceColor(tech2, character.techWounded[1]);
                setDiceColor(tech3, character.techWounded[2]);

                isWounded = true
                println()
                println("withdrawn" + character.withdrawn)
                println()
                if (character.withdrawn){
                    println("SLIDE DOWN")

                    wounded.animate().translationY(character_image.height.toFloat())
                }

            }
        }

        if(character.strain>0) {
            if (minus_strain.alpha == 0f) {
                minus_strain.animate().alpha(1f)
            }

            add_strain.setText("" + character.strain)
        }

        add_damage.setOnLongClickListener {
            if (character.damage >= character.health) {
                unwoundDialog!!.show()
            }
            true
        }
        add_strain.setOnLongClickListener {
            if (actionsLeft > 0) {
                restDialog!!.show()
            } else {
                showNoActionsLeftToast()
            }
            true
        }
    }

    //endregion
    //************************************************************************************************************
    //region Turn Actions
    //************************************************************************************************************
    var actionsLeft = 0;
    var activated = false
    fun onActivate(view: View) {
        if(!activated){
            val flipUnactive = ObjectAnimator.ofFloat(unactive, "scaleX", 1f, 0f, 0f, 0f)
            flipUnactive.setDuration(300)
            flipUnactive.start()

            val flipActive = ObjectAnimator.ofFloat(active, "scaleX", 0f, 0f, 0f, 1f)
            flipActive.setDuration(300)
            flipActive.start()


            action1.visibility = View.VISIBLE
            action_button1.visibility = View.VISIBLE
            action_button1.animate().alpha(1f)
            action2.visibility = View.VISIBLE
            action_button2.visibility = View.VISIBLE
            action_button2.animate().alpha(1f)
            actionsLeft = 2;
            activated = true

            character.activated++
        }
        else{
            onEndActivation(view)
        }
    }

    fun onEndActivation(view: View){

        endActivationDialog!!.cancel()
        removeCondition(weakened)
        val flipUnactive = ObjectAnimator.ofFloat(unactive, "scaleX", 0f, 0f, 0f, 1f)
        flipUnactive.setDuration(300)
        flipUnactive.start()

        val flipActive = ObjectAnimator.ofFloat(active, "scaleX", 1f, 0f, 0f, 0f)
        flipActive.setDuration(300)
        flipActive.start()

        action_button1.animate().alpha(0f)
        action_button1.visibility = View.GONE

        action_button2.animate().alpha(0f)
        action_button2.visibility = View.GONE
        activated = false

    }

    fun onEndActivationNo(view: View){
        endActivationDialog!!.cancel()
    }


    fun onAction(view: View) {

        action_menu.visibility = View.INVISIBLE
        action_menu.alpha = 0f

        if(actionsLeft>0) {
            //todo add focus symbol to attack
            if (conditionsActive[focused]) {
                attack_focused.visibility = View.VISIBLE
            } else {
               attack_focused.visibility = View.GONE
            }

            if (conditionsActive[hidden]) {
                attack_hidden.visibility = View.VISIBLE
            } else {
                attack_hidden.visibility = View.GONE
            }

            //todo add stun symbol and deactivate to move, special and attack
            if (conditionsActive[stunned]) {
                action_stunned_attack.visibility = View.VISIBLE
                action_stunned_move.visibility = View.VISIBLE
                action_stunned_special.visibility = View.VISIBLE
                action_remove_stun.visibility = View.VISIBLE
            } else {
                action_stunned_attack.visibility = View.INVISIBLE
                action_stunned_move.visibility = View.INVISIBLE
                action_stunned_special.visibility = View.INVISIBLE
                action_remove_stun.visibility = View.GONE
            }

            if (conditionsActive[bleeding]) {
                action_remove_bleeding.visibility = View.VISIBLE
            } else {
                action_remove_bleeding.visibility = View.GONE
            }

            action_menu.visibility = View.VISIBLE
            action_menu.animate().alpha(1f)
        }

    }

    fun actionCompleted(){
        if(actionsLeft >0) {
            actionsLeft--;
            if (actionsLeft == 1) {
                action1.visibility = View.INVISIBLE
            } else if (actionsLeft == 0) {
                action2.visibility = View.INVISIBLE
            }
            if (conditionsActive[bleeding]) {
                onAddStrain(add_strain)
            }
        }
        if(actionsLeft <=0) {

            action_menu.visibility = View.INVISIBLE
            if(activated) {
                endActivationDialog!!.show()
            }
        }
    }

    fun onAttack(view: View) {
        if(actionsLeft>0 ) {
            if(!conditionsActive[stunned]) {
                removeCondition(focused)
                removeCondition(hidden)
                actionCompleted()
                onAction(action_complete)
                character.attacksMade++
            }
        }
        else{
            showNoActionsLeftToast()
        }
    }
    fun onMove(view: View) {
        if(actionsLeft>0 ) {
            if(!conditionsActive[stunned]) {
                actionCompleted()
                onAction(action_complete)
                character.movesTaken++
            }
        }
        else{
            showNoActionsLeftToast()
        }
    }
    fun onSpecial(view: View) {
        if(actionsLeft>0) {
            if(!conditionsActive[stunned]) {
                actionCompleted()
                onAction(action_complete)
                character.specialActions++
            }
        }
        else{
            showNoActionsLeftToast()
        }
    }
    fun onInteract(view: View) {
        if(actionsLeft>0) {
            actionCompleted()
            onAction(action_complete)
            character.interactsUsed++
        }
        else{
            showNoActionsLeftToast()
        }
    }
    fun onRest(view: View) {
        if(actionsLeft>0) {
            character.strain -= character.endurance

            if (character.strain < 0) {
                for (i in 1..-character.strain) {
                    onMinusDamage(view)
                }
                character.strain = 0;
            }
            add_strain.setText("" + character.strain)
            playRestAnim()
            character.timesRested++
            restDialog!!.cancel()

            actionCompleted()
        }
        else{
            showNoActionsLeftToast()
        }
    }

    fun onRemoveStun(view: View) {
        if(actionsLeft>0) {
            removeCondition(stunned)
            onAction(action_complete)
            actionCompleted()
        }
        else{
            showNoActionsLeftToast()
        }
    }

    fun onRemoveBleeding(view: View) {
        if(actionsLeft>0) {
            removeCondition(bleeding)
            onAction(action_complete)
            actionCompleted()
        }
        else{
            showNoActionsLeftToast()
        }
    }
    fun showNoActionsLeftToast(){
        val noActionsLeftToast=Toast(this)
        noActionsLeftToast!!.duration = Toast.LENGTH_SHORT
        noActionsLeftToast!!.view = layoutInflater.inflate(
            R.layout.toast_no_actions_left,
            character_view_group,
            false
        )
        noActionsLeftToast!!.setGravity(Gravity.CENTER, 0, 0)
        noActionsLeftToast!!. show()
    }

    //endregion
    //************************************************************************************************************
    //region Show Cards
    //************************************************************************************************************

    fun onShowFocusedCard(view: View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_focused))
        showCardDialog!!.show()
    }
    fun onShowStunnedCard(view: View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_stunned))
        showCardDialog!!.show()
    }
    fun onShowWeakenedCard(view: View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_weakened))
        showCardDialog!!.show()
    }
    fun onShowBleedingCard(view: View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_bleeding))
        showCardDialog!!.show()
    }
    fun onShowHiddenCard(view: View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_hidden))
        showCardDialog!!.show()
    }

    fun onShowCard(view: View){
        when(view.getTag()){
            hidden -> onShowHiddenCard(view)
            focused -> onShowFocusedCard(view)
            stunned -> onShowStunnedCard(view)
            bleeding -> onShowBleedingCard(view)
            weakened -> onShowWeakenedCard(view)
        }
    }



    var sideMenuState = 0

//endregion
    //************************************************************************************************************
    //region Options Menu
    //************************************************************************************************************

    fun onShowOptions(view: View) {
        optionsDialog!!.show()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        println("save")
    }

    fun onBiography(view: View) {
        optionsDialog!!.cancel()
        bioDialog!!.bio_title.setText(character.bio_title)
        bioDialog!!.bio_quote.setText(character.bio_quote)
        bioDialog!!.bio_text.setText(character.bio_text)
        bioDialog!!.show()
    }
    fun onPower(view: View) {
        optionsDialog!!.cancel()
        if(!isWounded) {
            showCardDialog!!.card_image.setImageBitmap(character.power)
        }
        else{
            showCardDialog!!.card_image.setImageBitmap(character.power_wounded)
        }

        showCardDialog!!.show()
    }
    fun onSave(view: View) {

        optionsDialog!!.cancel()
        saveDialog!!.show()
    }
    fun onBackground(view: View) {
        optionsDialog!!.cancel()
        backgroundDialog!!.show()
    }
    fun onSettings(view: View) {
        optionsDialog!!.cancel()
        settingsScreen!!.show()
    }
    fun onStatistics(view: View) {
        optionsDialog!!.cancel()
        initStatsScreen()
        statsScreen!!.show()
    }

    fun onCredits(view: View) {
        optionsDialog!!.cancel()
        creditsScreen!!.show()
    }

    //Backgrounds
    fun onBackgroundSnow(view: View) {
        character.background = "snow"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_snow.png"))
    }
    fun onBackgroundJungle(view: View) {
        character.background = "jungle"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_jungle.png"))
    }
    fun onBackgroundDesert(view: View) {
        character.background = "desert"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_desert.png"))
    }
    fun onBackgroundInterior(view: View) {
        character.background = "interior"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_interior.png"))
    }

    fun onSaveCharacter(view: View) {
        saveCharacter()
        saveDialog!!.cancel()
    }



    //endregion
    //************************************************************************************************************
    //region Side Navigation
    //************************************************************************************************************


    fun onExtendDown(view: View) {
        val lp = kill_tracker_bar.layoutParams
        lp.height = menu_bar.height+22
        kill_tracker_bar.layoutParams = lp
        var h = menu_bar.height.toFloat()+88+44
        if(sideMenuState>-1){
            sideMenuState--
            kill_tracker_bar.animate().translationYBy(h)
            menu_bar.animate().translationYBy(h)
        }
        when(sideMenuState){
            -1 -> {
                extend_down_button.animate().alpha(0f);
                extend_up_button.animate().alpha(1f)
                kill_tracker_bar.animate().translationY(h)
                menu_bar.animate().translationY(h)
            }
            0 -> {
                extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(1f)
                kill_tracker_bar.animate().translationY(0f)
                menu_bar.animate().translationY(0f)
            }
            1 -> {
                extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(0f)
                kill_tracker_bar.animate().translationY(-h)
                menu_bar.animate().translationY(-h)
            }
        }


    }
    fun onExtendUp(view: View) {
        val lp = kill_tracker_bar.layoutParams
        lp.height = menu_bar.height+22
        kill_tracker_bar.layoutParams = lp
        var h = menu_bar.height.toFloat()+88+44
        if(sideMenuState<1){
            sideMenuState++
            kill_tracker_bar.animate().translationYBy(-h)
            menu_bar.animate().translationYBy(-h)
        }
        when(sideMenuState){
            -1 -> {
                extend_down_button.animate().alpha(0f);
                extend_up_button.animate().alpha(1f)
                kill_tracker_bar.animate().translationY(h)
                menu_bar.animate().translationY(h)
            }
            0 -> {
                extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(1f)
                kill_tracker_bar.animate().translationY(0f)
                menu_bar.animate().translationY(0f)
            }
            1 -> {
                extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(0f)
                kill_tracker_bar.animate().translationY(-h)
                menu_bar.animate().translationY(-h)
            }
        }

    }

    fun onReward(view: View) {
        val intent = Intent(this, Item_Select_Screen::class.java)
        intent.putExtra("tab", "reward")
        //intent.putExtra("Load",false)
        startActivity(intent);
    }
    fun onAccessory(view: View) {
        val intent = Intent(this, Item_Select_Screen::class.java)
        intent.putExtra("tab", "accessory")
        //intent.putExtra("Load",false)
        startActivity(intent);
    }
    fun onArmour(view: View) {
        val intent = Intent(this, Item_Select_Screen::class.java)
        intent.putExtra("tab", "armour")
        //intent.putExtra("Load",false)
        startActivity(intent);
    }
    fun onMelee(view: View) {
        val intent = Intent(this, Item_Select_Screen::class.java)
        intent.putExtra("tab", "melee")
        //intent.putExtra("Load",false)
        startActivity(intent);
    }
    fun onRange(view: View) {
        val intent = Intent(this, Item_Select_Screen::class.java)
        intent.putExtra("tab", "range")
        //intent.putExtra("Load",false)
        startActivity(intent);
    }
    fun onXPScreen(view: View) {
        initXPScreen()
        xpSelectScreen!!.show()

    }


    //endregion
    //************************************************************************************************************
    //region Kill Tracker
    //************************************************************************************************************

    //TODO add to stats
    var killCounts = ArrayList<TextView>()
    val villain =0
    val leader =1
    val vehicle =2
    val creature =3
    val guard =4
    val droid =5
    val scum =6
    val trooper =7

    fun villainKillDown(view: View) {killCountDown(villain)}
    fun villainKillUp(view: View) {killCountUp(villain)}
    fun leaderKillDown(view: View) {killCountDown(leader)}
    fun leaderKillUp(view: View) {killCountUp(leader)}
    fun vehicleKillDown(view: View) {killCountDown(vehicle)}
    fun vehicleKillUp(view: View) {killCountUp(vehicle)}
    fun creatureKillDown(view: View) {killCountDown(creature)}
    fun creatureKillUp(view: View) {killCountUp(creature)}
    fun guardKillDown(view: View) {killCountDown(guard)}
    fun guardKillUp(view: View) {killCountUp(guard)}
    fun droidKillDown(view: View) {killCountDown(droid)}
    fun droidKillUp(view: View) {killCountUp(droid)}
    fun scumKillDown(view: View) {killCountDown(scum)}
    fun scumKillUp(view: View) {killCountUp(scum)}
    fun trooperKillDown(view: View) {killCountDown(trooper)}
    fun trooperKillUp(view: View) {killCountUp(trooper)}

    fun killCountUp(type: Int){
        var killCount = Integer.parseInt(killCounts[type].text.toString())
        killCount++
        character.killCount[type]=killCount
        killCounts[type].setText("" + killCount)
    }
    fun killCountDown(type: Int){
        var killCount = Integer.parseInt(killCounts[type].text.toString())
        if(killCount>0) {
            killCount--
            character.killCount[type]=killCount
            killCounts[type].setText("" + killCount)
        }
    }

    fun onAssist(view: View) {
        when(view.tag){
            villain -> {
                character.assistCount[villain]++
            }
            leader -> {
                character.assistCount[leader]++
            }
            vehicle -> {
                character.assistCount[vehicle]++
            }
            creature -> {
                character.assistCount[creature]++
            }
            guard -> {
                character.assistCount[guard]++
            }
            droid -> {
                character.assistCount[droid]++
            }
            scum -> {
                character.assistCount[scum]++
            }
            trooper -> {
                character.assistCount[trooper]++
            }
        }
        assistDialog!!.cancel()
    }
    fun initKillTracker(){

        killCounts.add(villain_count)
        villain_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_villian))
            assistDialog!!.assist_button.setTag(villain)
            assistDialog!!.show()
            true
        }
        killCounts.add(leader_count)
        leader_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_leader))
            assistDialog!!.assist_button.setTag(leader)
            assistDialog!!.show()
            true
        }
        killCounts.add(vehicle_count)
        vehicle_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(
                resources.getDrawable(
                    R.drawable
                        .icon_vehicle
                )
            )
            assistDialog!!.assist_button.setTag(vehicle)
            assistDialog!!.show()
            true
        }
        killCounts.add(creature_count)
        creature_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_creature))
            assistDialog!!.assist_button.setTag(creature)
            assistDialog!!.show()
            true
        }
        killCounts.add(guard_count)
        guard_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_gaurd))
            assistDialog!!.assist_button.setTag(guard)
            assistDialog!!.show()
            true
        }
        killCounts.add(droid_count)
        droid_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_droid))
            assistDialog!!.assist_button.setTag(droid)
            assistDialog!!.show()
            true
        }
        killCounts.add(scum_count)
        scum_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_scum))
            assistDialog!!.assist_button.setTag(scum)
            assistDialog!!.show()
            true
        }
        killCounts.add(trooper_count)
        trooper_button.setOnLongClickListener {
            assistDialog!!.assist_icon.setImageDrawable(
                resources.getDrawable(
                    R.drawable
                        .icon_trooper
                )
            )
            assistDialog!!.assist_button.setTag(trooper)
            assistDialog!!.show()
            true
        }

        for(i in 0..killCounts.size-1){
            killCounts[i].text = ""+character.killCount[i]
        }
    }

    //endregion
    //************************************************************************************************************
    //region Conditions
    //************************************************************************************************************
    var hidden = 0
    var focused = 1
    var weakened = 2
    var bleeding = 3
    var stunned = 4
    var conditionViews = ArrayList<ImageView>()
    var conditionsActive = booleanArrayOf(false, false, false, false, false)
    var conditionDrawable = intArrayOf(
        R.drawable.condition_hidden,
        R.drawable.condition_focused,
        R.drawable.condition_weakened,
        R.drawable.condition_bleeding,
        R.drawable.condition_stunned
    )

    fun initConditions(){
        conditionsActive = character.conditionsActive
        conditionViews.add(condition1)
        conditionViews.add(condition2)
        conditionViews.add(condition3)
        conditionViews.add(condition4)
        conditionViews.add(condition5)
        for (i in 0..conditionViews.size - 1) {
            conditionViews[i].setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View): Boolean {
                    removeCondition(v.getTag() as Int)
                    actionCompleted()
                    return true
                }
            }
            )
        }
        hidden_all.setOnLongClickListener {
            removeCondition(hidden)
            true
        }
        focused_all.setOnLongClickListener {
            removeCondition(focused)

            true
        }
        weakened_all.setOnLongClickListener {
            removeCondition(weakened)

            true
        }
        bleeding_all.setOnLongClickListener {
            removeCondition(bleeding)
            actionCompleted()
            true
        }
        stunned_all.setOnLongClickListener {
            removeCondition(stunned)
            actionCompleted()
            true
        }
    }
    fun onAddCondition(view: View) {
        conditionsDialog!!.show()
    }
    fun onWeakened(view: View) {
        conditionsActive[weakened] =!conditionsActive[weakened]
        if(conditionsActive[weakened]) {
            character.timesWeakened++
        }
        else{
            character.timesWeakened--
        }
        updateConditionIcons()
    }
    fun onBleeding(view: View) {
        conditionsActive[bleeding] =!conditionsActive[bleeding]
        if(conditionsActive[bleeding]) {
            character.timesBleeding++
        }else{
            character.timesBleeding--
        }
        updateConditionIcons()
    }
    fun onStunned(view: View) {
        conditionsActive[stunned] =!conditionsActive[stunned]
        if(conditionsActive[stunned]) {
            character.timesStunned++
        }
        else{
            character.timesStunned--
        }
        updateConditionIcons()
    }
    fun onHidden(view: View) {
        conditionsActive[hidden] =!conditionsActive[hidden]
        if(conditionsActive[hidden]) {
            character.timesHidden++
        }
        else{
            character.timesHidden--
        }
        updateConditionIcons()
    }
    fun onFocused(view: View) {
        conditionsActive[focused] =!conditionsActive[focused]
        if(conditionsActive[focused]) {
            character.timesFocused++
        }
        else{
            character.timesFocused--
        }
        updateConditionIcons()
    }


    fun removeCondition(conditionType: Int){

        if(actionsLeft>0 || conditionType==hidden||conditionType==focused||conditionType==weakened) {
            conditionsActive[conditionType] = false
            updateConditionIcons()
        }
        else{
            showNoActionsLeftToast()
        }
    }

    fun updateConditionIcons(){
        for(i in 0..conditionViews.size-1){
            conditionViews[i].visibility = View.GONE
        }

        var active = 0;
        for(i in 0..conditionsActive.size-1){
            if(conditionsActive[i]){
                active++;
            }
        }
        character.conditionsActive = conditionsActive

        if(active<5) {
            show_conditions.visibility = View.VISIBLE
            show_all_conditions.visibility = View.INVISIBLE

            var conditionType = 0;
            for (i in 0..active - 1) {
                conditionViews[i].visibility = View.VISIBLE
                for (j in conditionType..conditionDrawable.size - 1) {
                    if (conditionsActive[j]) {
                        conditionViews[i].setImageDrawable(resources.getDrawable(conditionDrawable[conditionType]))
                        conditionViews[i].setTag(conditionType)
                        conditionType = j + 1;
                        break
                    }
                    conditionType = j + 1;
                }
            }
        }
        else{
            show_conditions.visibility = View.GONE
            show_all_conditions.visibility = View.VISIBLE
        }
        if(active>2){
            conditions_row2.visibility = View.VISIBLE
        }
        else{
            conditions_row2.visibility = View.GONE
        }
        if(!conditionsActive[hidden]) {
            conditionsDialog!!.hidden_select.alpha = 1f
        }
        else{
            conditionsDialog!!.hidden_select.alpha = 0.5f
        }

        if(!conditionsActive[hidden]) {
            conditionsDialog!!.hidden_select.alpha = 1f
        }
        else{
            conditionsDialog!!.hidden_select.alpha = 0.5f
        }

        if(!conditionsActive[focused]) {
            conditionsDialog!!.focused_select.alpha=1f
        }
        else{
            conditionsDialog!!.focused_select.alpha = 0.5f
        }

        if(!conditionsActive[stunned]) {
            conditionsDialog!!.stunned_select.alpha = 1f
        }
        else{
            conditionsDialog!!.stunned_select.alpha = 0.5f
        }
        if(!conditionsActive[bleeding]) {
            conditionsDialog!!.bleeding_select.alpha = 1f
        }
        else{
            conditionsDialog!!.bleeding_select.alpha = 0.5f
        }
        if(!conditionsActive[weakened]) {
            conditionsDialog!!.weakened_select.alpha = 1f
        }
        else{
            conditionsDialog!!.weakened_select.alpha = 0.5f
        }
    }

    //endregion
    //************************************************************************************************************
    //region Animations
    //************************************************************************************************************
    /*
    var blastAnim:AnimationDrawable = AnimationDrawable()
    var impactAnim :AnimationDrawable= AnimationDrawable()
    var restAnim:AnimationDrawable= AnimationDrawable()
    var sliceAnim:AnimationDrawable= AnimationDrawable()
*/
    fun initAnimations(){
        rest_animation.setBackgroundDrawable(MainActivity.restAnim)
        rest_animation.visibility = FrameLayout.INVISIBLE
    }

    fun playDamageAnim(){
        val animType = Math.random();
        if(animType<0.3){
            damage_animation.setBackgroundDrawable( MainActivity.blastAnim)
            damage_animation.visibility = FrameLayout.VISIBLE
            MainActivity.blastAnim.setVisible(true, true)
            MainActivity.blastAnim.start()
        }
        else if(animType<0.6){
            damage_animation.setBackgroundDrawable( MainActivity.sliceAnim)
            damage_animation.visibility = FrameLayout.VISIBLE
            MainActivity.sliceAnim.setVisible(true, true)
            MainActivity.sliceAnim.start()
        }
        else{
            damage_animation.setBackgroundDrawable( MainActivity.impactAnim)
            damage_animation.visibility = FrameLayout.VISIBLE
            MainActivity.impactAnim.setVisible(true, true)
            MainActivity.impactAnim.start()
        }


    }
    fun playRestAnim(){
        rest_animation.visibility = FrameLayout.VISIBLE
        MainActivity.restAnim.setVisible(true, true)
        MainActivity.restAnim.start()
    }




    //endregion
    //************************************************************************************************************
    //region Dialogs and Screens
    //************************************************************************************************************

    var restDialog:Dialog? = null
    var unwoundDialog:Dialog? = null
    var conditionsDialog:Dialog? = null
    //var actionDialog:Dialog? = null
    var showCardDialog:Dialog? = null
    var endActivationDialog:Dialog? = null
    var assistDialog:Dialog? = null
    var optionsDialog:Dialog? = null
    var bioDialog:Dialog? = null
    var saveDialog:Dialog? = null
    var backgroundDialog:Dialog? = null
    var creditsScreen:Dialog? = null
    var settingsScreen:Dialog? = null
    var statsScreen:Dialog? = null
    var xpSelectScreen:Dialog?=null

    fun initDialogs(){
        restDialog = Dialog(this)
        restDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        restDialog!!.setCancelable(false)
        restDialog!!.setContentView(R.layout.dialog_rest)
        restDialog!!.setCanceledOnTouchOutside(true)
        restDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        unwoundDialog = Dialog(this)
        unwoundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        unwoundDialog!!.setCancelable(false)
        unwoundDialog!!.setContentView(R.layout.dialog_unwound)
        unwoundDialog!!.setCanceledOnTouchOutside(true)
        unwoundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        conditionsDialog = Dialog(this)
        conditionsDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        conditionsDialog!!.setCancelable(false)
        conditionsDialog!!.setContentView(R.layout.dialog_conditions)
        conditionsDialog!!.setCanceledOnTouchOutside(true)
        conditionsDialog!!.stunned_select.setOnLongClickListener {
            onShowStunnedCard(conditionsDialog!!.stunned_select)
            true
        }
        conditionsDialog!!.bleeding_select.setOnLongClickListener {
            onShowBleedingCard(conditionsDialog!!.bleeding_select)
            true
        }
        conditionsDialog!!.weakened_select.setOnLongClickListener {
            onShowWeakenedCard(conditionsDialog!!.weakened_select)
            true
        }
        conditionsDialog!!.focused_select.setOnLongClickListener {
            onShowFocusedCard(conditionsDialog!!.focused_select)
            true
        }
        conditionsDialog!!.hidden_select.setOnLongClickListener {
            onShowHiddenCard(conditionsDialog!!.hidden_select)
            true
        }
        conditionsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        optionsDialog = Dialog(this)
        optionsDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        optionsDialog!!.setCancelable(false)
        optionsDialog!!.setContentView(R.layout.dialog_options)
        optionsDialog!!.setCanceledOnTouchOutside(true)
        optionsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        optionsDialog!!.bioOption.setOnClickListener{
            onBiography(optionsDialog!!.bioOption)
            true
        }
        optionsDialog!!.powerOption.setOnClickListener{
            onPower(optionsDialog!!.powerOption)
            true
        }
        optionsDialog!!.settingsOption.setOnClickListener{
            onSettings(optionsDialog!!.settingsOption)
            true
        }
        optionsDialog!!.saveOption.setOnClickListener{
            onSave(optionsDialog!!.saveOption)
            true
        }
        optionsDialog!!.statsOption.setOnClickListener{
            onStatistics(optionsDialog!!.statsOption)
            true
        }
        optionsDialog!!.backgroundOption.setOnClickListener{
            onBackground(optionsDialog!!.backgroundOption)
            true
        }

        /*
        actionDialog = Dialog(this)
        actionDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        actionDialog!!.setCancelable(false)
        actionDialog!!.setContentView(R.layout.dialog_action_menu)
        actionDialog!!.setCanceledOnTouchOutside(true)
        actionDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
         */
        //TODO replace dialog with overlay


        showCardDialog = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        showCardDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        showCardDialog!!.setContentView(R.layout.dialog_show_card)
        showCardDialog!!.setCancelable(false)
        showCardDialog!!.setCanceledOnTouchOutside(true)
        showCardDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        showCardDialog!!.show_card_dialog.setOnClickListener {
            showCardDialog!!.cancel()
            true
        }

        endActivationDialog = Dialog(this)
        endActivationDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        endActivationDialog!!.setCancelable(false)
        endActivationDialog!!.setContentView(R.layout.dialog_end_activation)
        endActivationDialog!!.setCanceledOnTouchOutside(true)
        endActivationDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        assistDialog = Dialog(this)
        assistDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        assistDialog!!.setCancelable(false)
        assistDialog!!.setContentView(R.layout.dialog_assist)
        assistDialog!!.setCanceledOnTouchOutside(true)
        assistDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        bioDialog = Dialog(this)
        bioDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bioDialog!!.setCancelable(false)
        bioDialog!!.setContentView(R.layout.dialog_bio)
        bioDialog!!.setCanceledOnTouchOutside(true)
        bioDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        saveDialog = Dialog(this)
        saveDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        saveDialog!!.setCancelable(false)
        saveDialog!!.setContentView(R.layout.dialog_save)
        saveDialog!!.setCanceledOnTouchOutside(true)
        saveDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        //TODO setOnClickListenters save

        backgroundDialog = Dialog(this)
        backgroundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        backgroundDialog!!.setCancelable(false)
        backgroundDialog!!.setContentView(R.layout.dialog_background)
        backgroundDialog!!.setCanceledOnTouchOutside(true)
        backgroundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        //TODO setOnClickListenters backgrounds

        settingsScreen= Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        settingsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        settingsScreen!!.setCancelable(false)
        settingsScreen!!.setContentView(R.layout.screen_settings)
        settingsScreen!!.setCanceledOnTouchOutside(true)
        settingsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        statsScreen = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        statsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        statsScreen!!.setCancelable(false)
        statsScreen!!.setContentView(R.layout.screen_stats)
        statsScreen!!.setCanceledOnTouchOutside(true)
        statsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        creditsScreen = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        creditsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        creditsScreen!!.setCancelable(false)
        creditsScreen!!.setContentView(R.layout.screen_stats)
        creditsScreen!!.setCanceledOnTouchOutside(true)
        creditsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        xpSelectScreen = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        xpSelectScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        xpSelectScreen!!.setCancelable(false)
        xpSelectScreen!!.setContentView(R.layout.screen_xp_select)
        xpSelectScreen!!.setCanceledOnTouchOutside(true)
    }
    //endregion
    //************************************************************************************************************
    //region Stats Screen
    //************************************************************************************************************
    fun initStatsScreen(){
        statsScreen!!.stats_name.setText("" + character.name)
        if(character.portraitImage==null) {
            character.loadPortraitImage(this)
        }
        statsScreen!!.stats_portrait_image.setImageDrawable(character.portraitImage)
        var level = 5
        if(character.xpSpent <= 1){
            level = 1
        }
        else if(character.xpSpent <= 4){
            level = 2
        }
        else if(character.xpSpent <= 7){
            level = 3
        }
        else if(character.xpSpent <= 10){
            level = 4
        }

        statsScreen!!.stats_level.setText("" + level)
        statsScreen!!.stats_moves.setText("" + character.movesTaken)
        statsScreen!!.stats_attacks.setText("" + character.attacksMade)
        statsScreen!!.stats_interacts.setText("" + character.interactsUsed)
        statsScreen!!.stats_wounded.setText("" + character.timesWounded)
        statsScreen!!.stats_rested.setText("" + character.timesRested)
        statsScreen!!.stats_withdrawn.setText("" + character.timesWithdrawn)
        statsScreen!!.stats_activated.setText("" + character.activated)
        statsScreen!!.stats_damaged.setText("" + character.damageTaken)
        statsScreen!!.stats_strain.setText("" + character.strainTaken)
        statsScreen!!.stats_specials.setText("" + character.specialActions)
        statsScreen!!.stats_focused.setText("" + character.timesFocused)
        statsScreen!!.stats_hidden.setText("" + character.timesHidden)
        statsScreen!!.stats_stunned.setText("" + character.timesStunned)
        statsScreen!!.stats_bleeding.setText("" + character.timesBleeding)
        statsScreen!!.stats_weakened.setText("" + character.timesWeakened)
        statsScreen!!.stats_crates.setText("" + character.cratesPickedUp)

        if(character.rewardObtained) {
            statsScreen!!.stats_reward_obtained.setText("Yes")
        }
        else{
            statsScreen!!.stats_reward_obtained.setText("No")
        }

        var totalKills = 0
        var totalAssists = 0
        for(i in 0..character.killCount.size-1){
            totalKills += character.killCount[i]
            totalAssists += character.assistCount[i]
        }
        statsScreen!!.stats_kill_total.setText("" + totalKills)
        statsScreen!!.stats_kill_villain.setText("" + character.killCount[villain])
        statsScreen!!.stats_kill_vehicle.setText("" + character.killCount[vehicle])
        statsScreen!!.stats_kill_creature.setText("" + character.killCount[creature])
        statsScreen!!.stats_kill_leader.setText("" + character.killCount[leader])
        statsScreen!!.stats_kill_guardian.setText("" + character.killCount[guard])
        statsScreen!!.stats_kill_droid.setText("" + character.killCount[droid])
        statsScreen!!.stats_kill_scum.setText("" + character.killCount[scum])
        statsScreen!!.stats_kill_trooper.setText("" + character.killCount[trooper])

        statsScreen!!.stats_assist_total.setText("" + totalAssists)
        statsScreen!!.stats_assist_villain.setText("" + character.assistCount[villain])
        statsScreen!!.stats_assist_vehicle.setText("" + character.assistCount[vehicle])
        statsScreen!!.stats_assist_creature.setText("" + character.assistCount[creature])
        statsScreen!!.stats_assist_leader.setText("" + character.assistCount[leader])
        statsScreen!!.stats_assist_guardian.setText("" + character.assistCount[guard])
        statsScreen!!.stats_assist_droid.setText("" + character.assistCount[droid])
        statsScreen!!.stats_assist_scum.setText("" + character.assistCount[scum])
        statsScreen!!.stats_assist_trooper.setText("" + character.assistCount[trooper])

        if(character.timesWounded>0) {
            statsScreen!!.stats_kill_death_ratio.setText("" + totalKills.toFloat() / character.timesWounded)
        }
        else{
            statsScreen!!.stats_kill_death_ratio.setText("-")
        }
    }

    //endregion
    //************************************************************************************************************
    //region XP Screen
    //************************************************************************************************************
    var xpCardImages = arrayListOf<ImageView>()

    fun initXPScreen() {


        xpCardImages.add(xpSelectScreen!!.XPCard1)
        xpCardImages.add(xpSelectScreen!!.XPCard2)
        xpCardImages.add(xpSelectScreen!!.XPCard3)
        xpCardImages.add(xpSelectScreen!!.XPCard4)
        xpCardImages.add(xpSelectScreen!!.XPCard5)
        xpCardImages.add(xpSelectScreen!!.XPCard6)
        xpCardImages.add(xpSelectScreen!!.XPCard7)
        xpCardImages.add(xpSelectScreen!!.XPCard8)
        xpCardImages.add(xpSelectScreen!!.XPCard9)



        for(i in 0.. character.xpCardImages.size-1){
            xpCardImages[i].setImageBitmap(character.xpCardImages[i])
            xpCardImages[i].setOnLongClickListener {
                onShowXPCard(xpCardImages[i])
                true
            }
            if(character.xpCardsEquipped[i]){
                xpCardImages[i].alpha = 1f
            }
            else{
                xpCardImages[i].alpha = 0.5f
            }
            xpCardImages[i].setTag(i)
        }
        var xpLeft = character.totalXP-character.xpSpent
        xpSelectScreen!!.xp_text.setText("XP: " + xpLeft)
    }

    fun onShowXPCard(view: ImageView){
        var image = ((view).drawable as BitmapDrawable).bitmap
        showCardDialog!!.card_image.setImageBitmap(image)
        showCardDialog!!.show()
    }
    fun onXPCard(view: View) {
        var xpLeft = character.totalXP-character.xpSpent;
        var cardNo = view.tag as Int
        if(character.xpCardsEquipped[cardNo]){
            character.xpCardsEquipped[cardNo] = false
            xpCardImages[cardNo].animate().alpha(0.5f).duration = 50
            character.xpSpent -= character.xpScores[cardNo]
        }
        else if( character.xpScores[cardNo] <= xpLeft) {
            character.xpCardsEquipped[cardNo] = true
            xpCardImages[cardNo].animate().alpha(1f).duration = 50
            character.xpSpent+=character.xpScores[cardNo]
        }
        xpLeft = character.totalXP-character.xpSpent
        xpSelectScreen!!.xp_text.setText("XP: " + xpLeft)
        character.rewardObtained = character.xpCardsEquipped[8]

        if(character.currentImage!=null) {
            character.currentImage!!.recycle()
        }
        updateImages()
        updateStats()

    }

    fun addXP(view: View){
        character.totalXP++
        var xpLeft = character.totalXP-character.xpSpent;
        xpSelectScreen!!.xp_text.setText("XP: " + xpLeft)
    }
    fun minusXP(view: View){
        var xpLeft = character.totalXP-character.xpSpent;
        if(xpLeft>0){
            character.totalXP--
        }
        xpLeft = character.totalXP-character.xpSpent;
        xpSelectScreen!!.xp_text.setText("XP: " + xpLeft)
    }


    //endregion
    //************************************************************************************************************
    //region Saving and Loading
    //************************************************************************************************************

    fun saveCharacter(){

        var saveFile= CharacterData(
            "" + saveDialog!!.save_name.text.toString(),
            System.currentTimeMillis(),
            character.name_short,
            character.damage,
            character.strain,
            character.token,
            character.wounded,
            character.conditionsActive[0],
            character.conditionsActive[1],
            character.conditionsActive[2],
            character.conditionsActive[3],
            character.conditionsActive[4],
            character.totalXP,
            character.xpSpent,
            character.xpCardsEquipped[0],
            character.xpCardsEquipped[1],
            character.xpCardsEquipped[2],
            character.xpCardsEquipped[3],
            character.xpCardsEquipped[4],
            character.xpCardsEquipped[5],
            character.xpCardsEquipped[6],
            character.xpCardsEquipped[7],
            character.xpCardsEquipped[8],
            character.weapon1,
            character.weapon2,
            character.accessory1,
            character.accessory2,
            character.accessory3,
            character.helmet,
            character.armour,
            character.background,
            conditionsActive[0],
            conditionsActive[1],
            conditionsActive[2],
            conditionsActive[3],
            conditionsActive[4],
            character.killCount[0],
            character.killCount[1],
            character.killCount[2],
            character.killCount[3],
            character.killCount[4],
            character.killCount[5],
            character.killCount[6],
            character.killCount[7],
            character.assistCount[0],
            character.assistCount[1],
            character.assistCount[2],
            character.assistCount[3],
            character.assistCount[4],
            character.assistCount[5],
            character.assistCount[6],
            character.assistCount[7],
            character.movesTaken,
            character.attacksMade,
            character.interactsUsed,
            character.timesWounded,
            character.timesRested,
            character.timesWithdrawn,
            character.activated,
            character.damageTaken,
            character.strainTaken,
            character.specialActions,
            character.timesFocused,
            character.timesHidden,
            character.timesStunned,
            character.timesBleeding,
            character.timesWeakened,
            character.cratesPickedUp,
            character.rewardObtained,
            character.withdrawn
        )

        val database = AppDatabase.getInstance(this)
        GlobalScope.launch {
            database!!.getCharacterDAO().insertAll(saveFile)
        }
    }


    //endregion
}



