//package com.example.imperialassault
//
//import android.animation.ObjectAnimator
//import android.app.Activity
//import android.app.Dialog
//import android.content.Context
//import android.content.Intent
//import android.content.res.ColorStateList
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Color
//import android.graphics.Color.TRANSPARENT
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.ColorDrawable
//import android.graphics.drawable.Drawable
//import android.os.Bundle
//import android.renderscript.Allocation
//import android.renderscript.Element
//import android.renderscript.RenderScript
//import android.renderscript.ScriptIntrinsicBlur
//import android.text.method.LinkMovementMethod
//import android.util.DisplayMetrics
//import android.view.*
//import android.view.animation.DecelerateInterpolator
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.widget.ImageViewCompat
//import androidx.lifecycle.Observer
//import androidx.work.*
//import kotlinx.android.synthetic.main.activity_character_screen.*
//import kotlinx.android.synthetic.main.credits_to_us.*
//import kotlinx.android.synthetic.main.dialog_assist.*
//import kotlinx.android.synthetic.main.dialog_background.*
//import kotlinx.android.synthetic.main.dialog_bio.*
//import kotlinx.android.synthetic.main.dialog_conditions.*
//import kotlinx.android.synthetic.main.dialog_options.*
//import kotlinx.android.synthetic.main.dialog_quick_view.*
//import kotlinx.android.synthetic.main.dialog_quick_view_button.*
//import kotlinx.android.synthetic.main.dialog_rest.*
//import kotlinx.android.synthetic.main.dialog_save.*
//import kotlinx.android.synthetic.main.dialog_show_card.*
//import kotlinx.android.synthetic.main.dialog_show_card.view.*
//import kotlinx.android.synthetic.main.grid_item.view.*
//import kotlinx.android.synthetic.main.screen_settings.*
//import kotlinx.android.synthetic.main.screen_stats.*
//import kotlinx.android.synthetic.main.screen_xp_select.*
//import kotlinx.android.synthetic.main.toast_no_actions_left.view.*
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.InputStream
//import kotlin.random.Random
//
//
//
//class CharacterScreen : AppCompatActivity() {
//    var character: Character = Character();
//    var animateConditions = true
//    var animateDamage = true
//
//    var actionUsage = true
//    var strengthGlow:GreenHighlight? = null
//    var techGlow:GreenHighlight?= null
//    var insightGlow:GreenHighlight?= null
//
//    var height = 0f
//    var width = 0f
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_character_screen)
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );
//
//
//
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        height = displayMetrics.heightPixels.toFloat()
//        width = displayMetrics.widthPixels.toFloat()
//
//        initDialogs()
//        initScreen()
//        initAnimations()
//        initKillTracker()
//
//    }
//
//
//
//    var loadAnimated = false
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        println(" CHARACTER" + MainActivity.selectedCharacter)
//
//            updateStats()
//            updateImages()
//            quickSave()
//
//
//        if (hasFocus && !loadAnimated) {
//
//
//            top_panel.animate().alpha(1f)
//            val animTop = ObjectAnimator.ofFloat(
//                top_panel, "translationY", -top_panel.height
//                    .toFloat(), 0f
//            )
//
//            animTop.duration = (500).toLong()
//            animTop.start()
//            bottom_panel.animate().alpha(1f)
//            val animBottom = ObjectAnimator.ofFloat(
//                bottom_panel, "translationY", bottom_panel.height
//                    .toFloat(), 0f
//            )
//            //animBottom.interpolator = DecelerateInterpolator()
//            animBottom.duration = (500).toLong()
//            animBottom.start()
//
//            left_buttons.animate().alpha(1f)
//            val animButtons = ObjectAnimator.ofFloat(
//                left_buttons, "translationX", -left_buttons.width
//                    .toFloat(), 0f
//            )
//            //animBottom.interpolator = DecelerateInterpolator()
//            animButtons.duration = (500).toLong()
//            animButtons.start()
//
//            right_buttons.animate().alpha(1f)
//            val animRightButtons = ObjectAnimator.ofFloat(
//                right_buttons, "translationX", right_buttons
//                    .width
//                    .toFloat(), 0f
//            )
//            //animBottom.interpolator = DecelerateInterpolator()
//            animRightButtons.duration = (500).toLong()
//            animRightButtons.start()
//
//            character_images.animate().alpha(1f)
//            val animChar = ObjectAnimator.ofFloat(
//                character_images, "translationX", -character_images.width
//                    .toFloat(), -character_images.width.toFloat(), 0f
//            )
//            animChar.interpolator = DecelerateInterpolator()
//            animChar.duration = (800).toLong()
//            animChar.start()
//
//            companion_image.animate().alpha(1f)
//            val animcompanion = ObjectAnimator.ofFloat(
//                companion_image, "translationX",
//                -character_images.width * 1.2f
//                    .toFloat(), -character_images.width.toFloat() * 1.2f, 0f
//            )
//            animcompanion.interpolator = DecelerateInterpolator()
//            animcompanion.duration = (800 * 1.2f).toLong()
//            animcompanion.start()
//
//            updateSideBarState()
//            kill_tracker_bar.visibility = View.VISIBLE
//
//            loadAnimated = true
//        }
//    }
//    fun resetUI(){
//        top_panel.animate().alpha(0f)
//        bottom_panel.animate().alpha(0f)
//        left_buttons.animate().alpha(0f)
//        right_buttons.animate().alpha(0f)
//        character_images.animate().alpha(0f)
//        companion_image.animate().alpha(0f)
//        loadAnimated = false
//    }
//
//    //************************************************************************************************************
//    //region Main Screen
//    //************************************************************************************************************
//    var isWounded = false
//
//    private fun initScreen() {
//        var load = intent.getBooleanExtra("Load", false)
//        var characterName: String = intent.getStringExtra("CharacterName").toString()
//
//
//        if (!load) {
//            when (characterName) {
//                "biv" -> {
//                    character = Character_biv(this)
//                }
//                "davith" -> {
//                    character = Character_davith(this)
//                }
//                "diala" -> {
//                    character = Character_diala(this)
//                }
//                "drokdatta" -> {
//                    character = Character_drokkatta(this)
//                }
//                "fenn" -> {
//                    character = Character_fenn(this)
//                }
//                "gaarkhan" -> {
//                    character = Character_gaarkhan(this)
//                }
//                "gideon" -> {
//                    character = Character_gideon(this)
//                }
//                "jarrod" -> {
//                    character = Character_jarrod(this)
//                }
//                "jyn" -> {
//                    character = Character_jyn(this)
//                }
//                "loku" -> {
//                    character = Character_loku(this)
//                }
//                "kotun" -> {
//                    character = Character_kotun(this)
//                }
//                "mak" -> {
//                    character = Character_mak(this)
//                }
//                "mhd19" -> {
//                    character = Character_mhd19(this)
//                }
//                "murne" -> {
//                    character = Character_murne(this)
//                }
//                "onar" -> {
//                    character = Character_onar(this)
//                }
//                "saska" -> {
//                    character = Character_saska(this)
//                }
//                "shyla" -> {
//                    character = Character_shyla(this)
//                }
//                "verena" -> {
//                    character = Character_verena(this)
//                }
//                "vinto" -> {
//                    character = Character_vinto(this)
//                }
//                "drokkatta" -> {
//                    character = Character_drokkatta(this)
//                }
//                "ct1701" -> {
//                    character = Character_ct1701(this)
//                }
//                "tress" -> {
//                    character = Character_tress(this)
//                }
//            }
//            MainActivity.selectedCharacter = character
//
//            if (character.startingRangedWeapon != null) {
//                character.weapons.add(Items.rangedArray!![3].index)
//            }
//            if (character.startingMeleeWeapon != null) {
//                character.weapons.add(Items.meleeArray!![3].index)
//            }
//        } else {
//            character = MainActivity.selectedCharacter!!
//        }
//        if (character.portraitImage == null) {
//            character.loadPortraitImage(this)
//        }
//        name.setText("" + character.name)
//
//        animateConditions = character.conditionAnimSetting
//        animateDamage =character.damageAnimSetting
//        actionUsage = character.actionUsageSetting
//        settingsScreen!!.soundEffectsVolume.progress = (character.soundEffectsSetting*100).toInt()
//
//        when (character.defence_dice) {
//            "white" -> defence.setImageDrawable(resources.getDrawable(R.drawable.dice))
//            "black" -> defence.setImageDrawable(resources.getDrawable(R.drawable.dice_black))
//            "" -> defence.visibility = View.INVISIBLE
//
//        }
//
//
//        updateImages()
//        updateStats()
//        initDamageAndStrain()
//        updateStats()
//        initConditions()
//        updateConditionIcons()
//
//
//
//        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_interior.png"))
//
//        if (character.name_short == "jarrod") {
//            companion_button.visibility = View.VISIBLE
//
//            companion_button.isClickable = true
//        } else {
//            companion_button.visibility = View.INVISIBLE
//
//            companion_button.isClickable = false
//        }
//        background_image.setImageBitmap(character.getBackgroundImage(this))
//    }
//
//    private fun setDiceColor(dice: ImageView, color: Char) {
//        dice.visibility = ImageView.VISIBLE
//        when (color) {
//            'B' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_blue))
//            'G' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_green))
//            'Y' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_yellow))
//            'R' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_red))
//            ' ' -> ImageViewCompat.setImageTintList(
//                dice, ColorStateList.valueOf(
//                    Color.argb(
//                        0,
//                        0,
//                        0,
//                        0
//                    )
//                )
//            )
//        }
//    }
//
//    open fun getBitmap(context: Context, path: String): Bitmap? {
//        val assetManager = context.assets
//        var inputStream: InputStream? = null
//        var bitmap: Bitmap? = null
//        val options = BitmapFactory.Options()
//        for (i in 1..32) {
//            try {
//                inputStream = assetManager.open(path)
//                options.inSampleSize = i
//
//                bitmap = BitmapFactory.decodeStream(inputStream, null, options)
//                break
//            } catch (outOfMemoryError: OutOfMemoryError) {
//
//                //println("next size" + i)
//            } catch (e: Exception) {
//                //e.printStackTrace()
//                break
//            }
//        }
//
//        try {
//            inputStream?.close()
//        } catch (e: Exception) {
//            //e.printStackTrace()
//        }
//        return bitmap
//    }
//
//    fun updateImages() {
//
//
//        character.updateCharacterImages(this)
//        if (animateConditions) {
//            if (character.currentImage != null) {
//                character.glowImage = Bitmap.createBitmap(character.currentImage!!)
//                val input = Bitmap.createBitmap(character.currentImage!!)
//                val output = Bitmap.createBitmap(character.currentImage!!)
//
//                val rs = RenderScript.create(this)
//                val blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
//                blur.setRadius(25f)
//                val tempIn = Allocation.createFromBitmap(rs, input)
//                val tempOut = Allocation.createFromBitmap(rs, output)
//                blur.setInput(tempIn)
//                blur.forEach(tempOut)
//
//                tempOut.copyTo(character.glowImage)
//
//            }
//            character_image.setGlowBitmap(character.glowImage)
//            character.updateCharacterImages(this)
//        }
//        character_image.setImageBitmap(character.currentImage)
//        character_image.setLayer1Bitmap(character.layer1)
//        character_image.setLayer2Bitmap(character.layer2)
//
//        if(!character.name_short.equals("jarrod")){
//            if(character.astromech) {
//                character.companionImage = (resources.getDrawable(R.drawable.r5_astromech1) as BitmapDrawable).bitmap
//            }
//            else{
//                character.companionImage = null
//            }
//        }
//        println(character.companionImage)
//        if (character.companionImage != null) {
//            if (conditionsActive[hidden] && animateConditions) {
//                companion_image.visibility = View.INVISIBLE
//            } else {
//                companion_image.setImageBitmap(character.companionImage)
//                companion_image.visibility = View.VISIBLE
//            }
//        } else {
//            companion_image.visibility = View.GONE
//        }
//
//
//        //quickSave()
//
//    }
//
//    fun onShowCompanionCard(view: View) {
//        if (character.companionCard != null) {
//            showCardDialog!!.card_image.setImageBitmap(character.companionCard)
//            showCardDialog!!.show()
//        }
//    }
//
//    fun updateStats() {
//        character.health = character.health_default
//        character.endurance = character.endurance_default
//        character.speed = character.speed_default
//
//        for (i in 0..character.xpCardsEquipped.size - 1) {
//            if (character.xpCardsEquipped[i]) {
//                if (character.xpHealths[i] != 0) {
//                    character.health += character.xpHealths[i]
//                }
//                if (character.xpEndurances[i] != 0) {
//                    character.endurance += character.xpEndurances[i]
//                }
//                if (character.xpSpeeds[i] != 0) {
//                    character.speed += character.xpSpeeds[i]
//                }
//            }
//        }
//
//        for (i in 0..character.accessories.size - 1) {
//            character.health += Items.itemsArray!![character.accessories[i]].health
//            character.endurance += Items.itemsArray!![character.accessories[i]].endurance
//        }
//        for (i in 0..character.armor.size - 1) {
//            character.health += Items.itemsArray!![character.armor[i]].health
//            //character.endurance += Items.armorArray!![character.armor[i]].endurance
//        }
//        for (i in 0..character.rewards.size - 1) {
//            character.health += Items.rewardsArray!![character.rewards[i]].health
//            //character.endurance += Items.armorArray!![character.armor[i]].endurance
//        }
//
//        if (isWounded) {
//            character.endurance--
//            character.speed--
//        }
//
//        setStatColor(health, character.health, character.health_default)
//        setStatColor(endurance, character.endurance, character.endurance_default)
//        setStatColor(speed, character.speed, character.speed_default)
//
//        health.setText("" + character.health);
//        endurance.setText("" + character.endurance);
//        speed.setText("" + character.speed);
//
//        if (!isWounded) {
//
//            setDiceColor(strength1, character.strength[0]);
//            setDiceColor(strength2, character.strength[1]);
//            setDiceColor(strength3, character.strength[2]);
//
//            setDiceColor(insight1, character.insight[0]);
//            setDiceColor(insight2, character.insight[1]);
//            setDiceColor(insight3, character.insight[2]);
//
//            setDiceColor(tech1, character.tech[0]);
//            setDiceColor(tech2, character.tech[1]);
//            setDiceColor(tech3, character.tech[2]);
//        } else {
//            setDiceColor(strength1, character.strengthWounded[0]);
//            setDiceColor(strength2, character.strengthWounded[1]);
//            setDiceColor(strength3, character.strengthWounded[2]);
//
//            setDiceColor(insight1, character.insightWounded[0]);
//            setDiceColor(insight2, character.insightWounded[1]);
//            setDiceColor(insight3, character.insightWounded[2]);
//
//            setDiceColor(tech1, character.techWounded[0]);
//            setDiceColor(tech2, character.techWounded[1]);
//            setDiceColor(tech3, character.techWounded[2]);
//        }
//
//
//    }
//
//    private fun setStatColor(stat: TextView, current: Int, default: Int) {
//        if (current > default) {
//            stat.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.dice_green))
//        } else if (current < default) {
//            stat.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.stat_orange))
//        } else {
//            stat.setShadowLayer(5f, 0f, 0f, Color.BLACK)
//        }
//    }
//
//    fun onHide(view: View) {
//        view.visibility = View.INVISIBLE
//    }
//
//    //endregion
//    //************************************************************************************************************
//    //region Damage and Strain
//    //************************************************************************************************************
//
//    private fun getNumber(number: Int): Drawable {
//        var numberImage = R.drawable.number_0
//        when (number) {
//            1 -> numberImage = R.drawable.number_1
//            2 -> numberImage = R.drawable.number_2
//            3 -> numberImage = R.drawable.number_3
//            4 -> numberImage = R.drawable.number_4
//            5 -> numberImage = R.drawable.number_5
//            6 -> numberImage = R.drawable.number_6
//            7 -> numberImage = R.drawable.number_7
//            8 -> numberImage = R.drawable.number_8
//            9 -> numberImage = R.drawable.number_9
//            10 -> numberImage = R.drawable.number_10
//            11 -> numberImage = R.drawable.number_11
//            12 -> numberImage = R.drawable.number_12
//            13 -> numberImage = R.drawable.number_13
//            14 -> numberImage = R.drawable.number_14
//            15 -> numberImage = R.drawable.number_15
//            16 -> numberImage = R.drawable.number_16
//            17 -> numberImage = R.drawable.number_17
//            18 -> numberImage = R.drawable.number_18
//            19 -> numberImage = R.drawable.number_19
//            20 -> numberImage = R.drawable.number_20
//        }
//        return resources.getDrawable(numberImage)
//    }
//
//    fun onAddStrain(view: View) {
//        if (character.strain < character.endurance) {
//            Sounds.strainSound()
//            character.strain++
//            character.strainTaken++
//
//            if (minus_strain.alpha == 0f) {
//                minus_strain.animate().alpha(1f)
//            }
//
//            //add_strain.setText("" + character.strain)
//            add_strain.setImageDrawable(getNumber(character.strain))
//        } else {
//            Sounds.damagedSound(this,Sounds.impact)
//            addDamage()
//            if(animateDamage) {
//                hitAnim()
//            }
//        }
//
//        playRestAnim()
//
//    }
//
//    fun onMinusStrain(view: View) {
//        if (character.strain > 0) {
//            Sounds.selectSound()
//            character.strain--
//            //add_strain.setText("" + character.strain)
//            add_strain.setImageDrawable(getNumber(character.strain))
//        }
//        if (character.strain == 0) {
//            if (minus_strain.alpha > 0f) {
//                minus_strain.animate().alpha(0f)
//            }
//        }
//
//    }
//
//    fun onAddDamage(view: View) {
//        if (addDamage()) {
//            playDamageAnim()
//
//        }
//    }
//
//    private fun addDamage(): Boolean {
//        if (character.damage < character.health * 2) {
//            character.damage++
//            character.damageTaken++
//
//            if (minus_damage.alpha == 0f) {
//                minus_damage.animate().alpha(1f)
//            }
//
//            if (character.damage < character.health) {
//
//                //add_damage.setText("" + character.damage)
//                add_damage.setImageDrawable(getNumber(character.damage))
//            } else if (character.damage < character.health * 2) {
//
//                character.wounded = character.damage - character.health
//                //add_damage.setText("" + character.wounded)
//                add_damage.setImageDrawable(getNumber(character.wounded))
//                if (!isWounded) {
//                    wounded.animate().alpha(1f)
//
//
//                    character.timesWounded++
//                    isWounded = true
//                    updateStats()
//                }
//
//            } else {
//                character.withdrawn = true
//                character.timesWithdrawn++
//                //add_damage.setText("" + character.health)
//                add_damage.setImageDrawable(getNumber(character.health))
//                val slide = ObjectAnimator.ofFloat(
//                    character_images, "translationY", 0f, 0f,
//                    character_image.height.toFloat()
//                )
//                slide.setDuration(1500)
//                slide.start()
//                quickSave()
//            }
//
//
//
//            return true
//        }
//        return false
//    }
//
//    fun onMinusDamage(view: View) {
//        if (character.damage > 0) {
//            Sounds.selectSound()
//            character.damage--
//            character.withdrawn = false
//            if (character.damage < character.health) {
//                //add_damage.setText("" + character.damage)
//                add_damage.setImageDrawable(getNumber(character.damage))
//                if (isWounded) {
//                    character.wounded = 0
//                    wounded.animate().alpha(0f)
//                    isWounded = false
//                    updateStats()
//                }
//            } else if (character.damage < character.health * 2) {
//                character.wounded = character.damage - character.health
//                //add_damage.setText("" + character.wounded)
//                add_damage.setImageDrawable(getNumber(character.wounded))
//                val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
//                slide.setDuration(500)
//                slide.start()
//                quickSave()
//            }
//
//        }
//        if (character.damage == 0) {
//            if (minus_damage.alpha > 0f) {
//                minus_damage.animate().alpha(0f)
//            }
//        }
//    }
//
//    fun onUnwound(view: View) {
//        playRestAnim()
//        character.damage = 0
//        character.wounded = 0
//        //add_damage.setText("" + character.damage)
//        add_damage.setImageDrawable(getNumber(character.damage))
//        wounded.animate().alpha(0f)
//        isWounded = false
//        unwoundDialog!!.dismiss()
//        character.withdrawn = false
//        val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
//        slide.setDuration(500)
//        slide.start()
//        updateStats()
//        quickSave()
//
//    }
//
//    private fun initDamageAndStrain() {
//        if (character.damage > 0) {
//            if (minus_damage.alpha == 0f) {
//                minus_damage.animate().alpha(1f)
//            }
//
//            if (character.damage < character.health) {
//                //add_damage.setText("" + character.damage)
//                add_damage.setImageDrawable(getNumber(character.damage))
//
//            } else {
//                character.wounded = character.damage - character.health
//                //add_damage.setText("" + character.wounded)
//                add_damage.setImageDrawable(getNumber(character.wounded))
//                wounded.animate().alpha(1f)
//
//                isWounded = true
//                updateStats()
//                //println()
//                //println("withdrawn" + character.withdrawn)
//                //println()
//                if (character.withdrawn) {
//                    //println("SLIDE DOWN")
//                    wounded.animate().translationY(character_image.height.toFloat())
//                }
//
//            }
//        }
//
//        if (character.strain > 0) {
//            if (minus_strain.alpha == 0f) {
//                minus_strain.animate().alpha(1f)
//            }
//
//            //add_strain.setText("" + character.strain)
//            add_strain.setImageDrawable(getNumber(character.strain))
//        }
//
//        add_damage.setOnLongClickListener {
//            if (character.damage >= character.health) {
//                unwoundDialog!!.show()
//            }
//            true
//        }
//        add_strain.setOnLongClickListener {
//            if (actionsLeft > 0 || !actionUsage) {
//                restDialog!!.show()
//            } else {
//                showNoActionsLeftToast()
//            }
//            true
//        }
//    }
//
//    //endregion
//    //************************************************************************************************************
//    //region Turn Actions
//    //************************************************************************************************************
//    var actionsLeft = 0;
//    var activated = false
//
//    fun onActivate(view: View) {
//        Sounds.selectSound()
//        if (!activated) {
//            /*
//            val flipUnactive = ObjectAnimator.ofFloat(unactive, "scaleX", 1f, 0f, 0f, 0f)
//            flipUnactive.setDuration(300)
//            flipUnactive.start()
//
//            val flipActive = ObjectAnimator.ofFloat(active, "scaleX", 0f, 0f, 0f, 1f)
//            flipActive.setDuration(300)
//            flipActive.start()
//
//             */
//            unactive.animate().alpha(0f).duration = 100
//
//            if (actionUsage) {
//                turnOnActionButtons()
//                actionsLeft = 2;
//            }
//            activated = true
//
//
//        } else {
//            onEndActivation(view)
//        }
//    }
//
//    fun turnOnActionButtons() {
//
//        action1.visibility = View.VISIBLE
//        action_button1.visibility = View.VISIBLE
//        action_button1.animate().alpha(1f)
//        action2.visibility = View.VISIBLE
//        action_button2.visibility = View.VISIBLE
//        action_button2.animate().alpha(1f)
//
//        /*
//        action_button1.visibility = View.VISIBLE
//        val flipAction1 = ObjectAnimator.ofFloat(action_button1, "scaleY", 0f, 1f)
//        flipAction1.setDuration(300)
//        flipAction1.start()
//
//        action_button2.visibility = View.VISIBLE
//        val flipAction2 = ObjectAnimator.ofFloat(action_button2, "scaleY", 0f, 1f)
//        flipAction2.setDuration(300)
//        flipAction2.start()
//        */
//
//    }
//
//    fun onEndActivation(view: View) {
//
//        endActivationDialog!!.dismiss()
//        removeCondition(weakened)
//        /*
//        val flipUnactive = ObjectAnimator.ofFloat(unactive, "scaleX", 0f, 0f, 0f, 1f)
//        flipUnactive.setDuration(300)
//        flipUnactive.start()
//
//        val flipActive = ObjectAnimator.ofFloat(active, "scaleX", 1f, 0f, 0f, 0f)
//        flipActive.setDuration(300)
//        flipActive.start()
//        */
//        unactive.animate().alpha(1f).duration = 100
//
//        if (actionUsage) {
//            turnOffActionButtons()
//            if(actionsLeft <= 1){
//                character.activated++
//            }
//        }
//        else{
//            character.activated++
//        }
//        activated = false
//
//    }
//
//    fun turnOffActionButtons() {
//
//        action_button1.animate().alpha(0f)
//        action_button1.visibility = View.GONE
//
//        action_button2.animate().alpha(0f)
//        action_button2.visibility = View.GONE
//
///*
//        val flipAction1 = ObjectAnimator.ofFloat(action_button1, "scaleY", 1f, 0f)
//        flipAction1.setDuration(300)
//        flipAction1.start()
//
//
//        val flipAction2 = ObjectAnimator.ofFloat(action_button2, "scaleY", 1f, 0f)
//        flipAction2.setDuration(300)
//        flipAction2.start()
//        */
//
//    }
//
//    fun onEndActivationNo(view: View) {
//        endActivationDialog!!.dismiss()
//    }
//
//    fun onAction(view: View) {
//        Sounds.selectSound()
//        action_menu.visibility = View.INVISIBLE
//        action_menu.alpha = 0f
//
//        if (actionsLeft > 0) {
//            //todo add focus symbol to attack
//            if (conditionsActive[focused]) {
//                attack_focused.visibility = View.VISIBLE
//            } else {
//                attack_focused.visibility = View.GONE
//            }
//
//            if (conditionsActive[hidden]) {
//                attack_hidden.visibility = View.VISIBLE
//            } else {
//                attack_hidden.visibility = View.GONE
//            }
//
//            //todo add stun symbol and deactivate to move, special and attack
//            if (conditionsActive[stunned]) {
//                action_stunned_attack.visibility = View.VISIBLE
//                action_stunned_move.visibility = View.VISIBLE
//                action_stunned_special.visibility = View.VISIBLE
//                action_remove_stun.visibility = View.VISIBLE
//            } else {
//                action_stunned_attack.visibility = View.INVISIBLE
//                action_stunned_move.visibility = View.INVISIBLE
//                action_stunned_special.visibility = View.INVISIBLE
//                action_remove_stun.visibility = View.GONE
//            }
//
//            if (conditionsActive[bleeding]) {
//                action_remove_bleeding.visibility = View.VISIBLE
//            } else {
//                action_remove_bleeding.visibility = View.GONE
//            }
//
//            action_menu.visibility = View.VISIBLE
//            action_menu.animate().alpha(1f)
//        }
//
//    }
//
//    fun actionCompleted() {
//        if (actionUsage) {
//            if (actionsLeft > 0) {
//                actionsLeft--;
//                if (actionsLeft == 1) {
//                    action1.visibility = View.INVISIBLE
//                } else if (actionsLeft == 0) {
//                    action2.visibility = View.INVISIBLE
//                }
//                if (conditionsActive[bleeding]) {
//                    onAddStrain(add_strain)
//                }
//                action_menu.visibility = View.INVISIBLE
//            }
//            if (actionsLeft <= 0) {
//
//                action_menu.visibility = View.INVISIBLE
//                if (activated) {
//                    endActivationDialog!!.show()
//                }
//            }
//        }
//    }
//
//    fun onAttack(view: View) {
//        if (actionsLeft > 0) {
//
//
//            if (!conditionsActive[stunned]) {
//                removeCondition(focused)
//                removeCondition(hidden)
//                actionCompleted()
//                Sounds.attackSound()
//                character.attacksMade++
//
//            }
//            else{
//                Sounds.negativeSound()
//            }
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun onMove(view: View) {
//        if (actionsLeft > 0) {
//            Sounds.movingSound()
//            if (!conditionsActive[stunned]) {
//                actionCompleted()
//
//                character.movesTaken++
//            }
//            else{
//                Sounds.negativeSound()
//            }
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun onSpecial(view: View) {
//
//        if (actionsLeft > 0) {
//            if (!conditionsActive[stunned]) {
//                actionCompleted()
//                Sounds.selectSound()
//                character.specialActions++
//            }
//            else{
//                Sounds.negativeSound()
//            }
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun onInteract(view: View) {
//        if (actionsLeft > 0) {
//            actionCompleted()
//            Sounds.selectSound()
//            character.interactsUsed++
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun onRest(view: View) {
//        if (actionsLeft > 0 || !actionUsage) {
//            character.strain -= character.endurance
//
//            if (character.strain < 0) {
//                for (i in 1..-character.strain) {
//                    onMinusDamage(view)
//                }
//                character.strain = 0;
//            }
//            //add_strain.setText("" + character.strain)
//            add_strain.setImageDrawable(getNumber(character.strain))
//            playRestAnim()
//            character.timesRested++
//            restDialog!!.dismiss()
//
//            actionCompleted()
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun onRemoveStun(view: View) {
//        if (actionsLeft > 0 || !actionUsage) {
//            removeCondition(stunned)
//            Sounds.selectSound()
//            actionCompleted()
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun onRemoveBleeding(view: View) {
//        if (actionsLeft > 0 || !actionUsage) {
//            removeCondition(bleeding)
//            Sounds.selectSound()
//            actionCompleted()
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    private fun showNoActionsLeftToast() {
//        Sounds.negativeSound()
//        val noActionsLeftToast = Toast(this)
//        noActionsLeftToast!!.duration = Toast.LENGTH_SHORT
//        noActionsLeftToast!!.view = layoutInflater.inflate(
//            R.layout.toast_no_actions_left,
//            character_view_group,
//            false
//        )
//        noActionsLeftToast!!.setGravity(Gravity.CENTER, 0, 0)
//        noActionsLeftToast!!.show()
//    }
//
//    //endregion
//    //************************************************************************************************************
//    //region Show Cards
//    //************************************************************************************************************
//
//    fun onShowFocusedCard(view: View) {
//        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_focused))
//        showCardDialog!!.show()
//    }
//
//    fun onShowStunnedCard(view: View) {
//        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_stunned))
//        showCardDialog!!.show()
//    }
//
//    fun onShowWeakenedCard(view: View) {
//        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_weakened))
//        showCardDialog!!.show()
//    }
//
//    fun onShowBleedingCard(view: View) {
//        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_bleeding))
//        showCardDialog!!.show()
//    }
//
//    fun onShowHiddenCard(view: View) {
//        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_hidden))
//        showCardDialog!!.show()
//    }
//
//    fun onShowCard(view: View) {
//        when (view.getTag()) {
//            hidden -> onShowHiddenCard(view)
//            focused -> onShowFocusedCard(view)
//            stunned -> onShowStunnedCard(view)
//            bleeding -> onShowBleedingCard(view)
//            weakened -> onShowWeakenedCard(view)
//        }
//    }
//
//    var sideMenuState = 0
//
//
//
//    //endregion
//    //************************************************************************************************************
//    //region Side Navigation
//    //************************************************************************************************************
//    fun updateSideBarState(){
//        when (sideMenuState) {
//            -1 -> {
//                extend_down_button.animate().alpha(0f);
//                extend_up_button.animate().alpha(1f)
//                kill_tracker_bar.animate().translationY(0f)
//                menu_bar.animate().translationY(height)
//            }
//            0 -> {
//                extend_down_button.animate().alpha(1f)
//                extend_up_button.animate().alpha(1f)
//                kill_tracker_bar.animate().translationY(-height)
//                menu_bar.animate().translationY(0f)
//            }
//            1 -> {
//                extend_down_button.animate().alpha(1f)
//                extend_up_button.animate().alpha(0f)
//                kill_tracker_bar.animate().translationY(-2*height)
//                menu_bar.animate().translationY(-height)
//            }
//        }
//
//    }
//    fun onExtendDown(view: View) {
//
//        if (sideMenuState > -1) {
//            Sounds.selectSound()
//            sideMenuState--
//            updateSideBarState()
//        }
//
//
//    }
//
//    fun onExtendUp(view: View) {
//        if (sideMenuState < 1) {
//            Sounds.selectSound()
//            sideMenuState++
//            updateSideBarState()
//        }
//    }
//
//    fun onReward(view: View) {
//        resetUI()
//        Sounds.selectSound()
//        val intent = Intent(this, RewardsScreen::class.java)
//        //intent.putExtra("Load",false)
//        startActivity(intent);
//
//    }
//
//    fun onAccessory(view: View) {
//        //val intent = Intent(this, ItemSelectScreen::class.java)
//        //intent.putExtra("tab", "accessory")
//        //intent.putExtra("Load",false)
//        resetUI()
//        Sounds.selectSound()
//        val intent = Intent(this, AccScreen::class.java)
//        startActivity(intent);
//
//    }
//
//    fun onArmour(view: View) {
//        //val intent = Intent(this, ItemSelectScreen::class.java)
//        //intent.putExtra("tab", "armour")
//        //intent.putExtra("Load",false)
//        resetUI()
//        Sounds.selectSound()
//        val intent = Intent(this, ArmorScreen::class.java)
//        startActivity(intent);
//
//    }
//
//    fun onMelee(view: View) {
//        //val intent = Intent(this, ItemSelectScreen::class.java)
//        //intent.putExtra("tab", "melee")
//        resetUI()
//        Sounds.selectSound()
//        val intent = Intent(this, MeleeScreen::class.java)
//        //intent.putExtra("Load",false)
//        startActivity(intent);
//
//    }
//
//    fun onRange(view: View) {
//        //val intent = Intent(this, ItemSelectScreen::class.java)
//        //intent.putExtra("tab", "range")
//        resetUI()
//        Sounds.selectSound()
//        val intent = Intent(this, RangedScreen::class.java)
//        //intent.putExtra("Load",false)
//        startActivity(intent);
//
//    }
//
//    fun onXPScreen(view: View) {
//        resetUI()
//        Sounds.selectSound()
//        initXPScreen()
//        xpSelectScreen!!.show()
//
//    }
//
//    //endregion
//    //************************************************************************************************************
//    //region Kill Tracker
//    //************************************************************************************************************
//
//    //TODO add to stats
//    var killCounts = ArrayList<TextView>()
//    val villain = 0
//    val leader = 1
//    val vehicle = 2
//    val creature = 3
//    val guard = 4
//    val droid = 5
//    val scum = 6
//    val trooper = 7
//
//    fun villainKillDown(view: View) {
//        killCountDown(villain)
//    }
//
//    fun villainKillUp(view: View) {
//        killCountUp(villain)
//    }
//
//    fun leaderKillDown(view: View) {
//        killCountDown(leader)
//    }
//
//    fun leaderKillUp(view: View) {
//        killCountUp(leader)
//    }
//
//    fun vehicleKillDown(view: View) {
//        killCountDown(vehicle)
//    }
//
//    fun vehicleKillUp(view: View) {
//        killCountUp(vehicle)
//    }
//
//    fun creatureKillDown(view: View) {
//        killCountDown(creature)
//    }
//
//    fun creatureKillUp(view: View) {
//        killCountUp(creature)
//    }
//
//    fun guardKillDown(view: View) {
//        killCountDown(guard)
//    }
//
//    fun guardKillUp(view: View) {
//        killCountUp(guard)
//    }
//
//    fun droidKillDown(view: View) {
//        killCountDown(droid)
//    }
//
//    fun droidKillUp(view: View) {
//        killCountUp(droid)
//    }
//
//    fun scumKillDown(view: View) {
//        killCountDown(scum)
//    }
//
//    fun scumKillUp(view: View) {
//        killCountUp(scum)
//    }
//
//    fun trooperKillDown(view: View) {
//        killCountDown(trooper)
//    }
//
//    fun trooperKillUp(view: View) {
//        killCountUp(trooper)
//    }
//
//    fun killCountUp(type: Int) {
//        Sounds.selectSound()
//        var killCount = Integer.parseInt(killCounts[type].text.toString())
//        killCount++
//        character.killCount[type] = killCount
//        killCounts[type].setText("" + killCount)
//    }
//
//    fun killCountDown(type: Int) {
//        var killCount = Integer.parseInt(killCounts[type].text.toString())
//        if (killCount > 0) {
//            Sounds.selectSound()
//            killCount--
//            character.killCount[type] = killCount
//            killCounts[type].setText("" + killCount)
//        }
//    }
//
//    fun onAssist(view: View) {
//        Sounds.selectSound()
//        when (view.tag) {
//            villain -> {
//                character.assistCount[villain]++
//            }
//            leader -> {
//                character.assistCount[leader]++
//            }
//            vehicle -> {
//                character.assistCount[vehicle]++
//            }
//            creature -> {
//                character.assistCount[creature]++
//            }
//            guard -> {
//                character.assistCount[guard]++
//            }
//            droid -> {
//                character.assistCount[droid]++
//            }
//            scum -> {
//                character.assistCount[scum]++
//            }
//            trooper -> {
//                character.assistCount[trooper]++
//            }
//        }
//        assistDialog!!.dismiss()
//    }
//
//    private fun initKillTracker() {
//
//        killCounts.add(villain_count)
//        villain_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_villian))
//            assistDialog!!.unwound_button.setTag(villain)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(leader_count)
//        leader_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_leader))
//            assistDialog!!.unwound_button.setTag(leader)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(vehicle_count)
//        vehicle_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(
//                resources.getDrawable(
//                    R.drawable
//                        .icon_vehicle
//                )
//            )
//            assistDialog!!.unwound_button.setTag(vehicle)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(creature_count)
//        creature_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_creature))
//            assistDialog!!.unwound_button.setTag(creature)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(guard_count)
//        guard_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_gaurd))
//            assistDialog!!.unwound_button.setTag(guard)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(droid_count)
//        droid_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_droid))
//            assistDialog!!.unwound_button.setTag(droid)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(scum_count)
//        scum_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(resources.getDrawable(R.drawable.icon_scum))
//            assistDialog!!.unwound_button.setTag(scum)
//            assistDialog!!.show()
//            true
//        }
//        killCounts.add(trooper_count)
//        trooper_button.setOnLongClickListener {
//            assistDialog!!.assist_icon.setImageDrawable(
//                resources.getDrawable(
//                    R.drawable
//                        .icon_trooper
//                )
//            )
//            assistDialog!!.unwound_button.setTag(trooper)
//            assistDialog!!.show()
//            true
//        }
//
//        for (i in 0..killCounts.size - 1) {
//            killCounts[i].text = "" + character.killCount[i]
//        }
//    }
//
//    //endregion
//    //************************************************************************************************************
//    //region Conditions
//    //************************************************************************************************************
//    var hidden = 0
//    var focused = 1
//    var weakened = 2
//    var bleeding = 3
//    var stunned = 4
//    var conditionViews = ArrayList<ImageView>()
//    var conditionsActive = booleanArrayOf(false, false, false, false, false)
//    var conditionDrawable = intArrayOf(
//        R.drawable.condition_hidden,
//        R.drawable.condition_focused,
//        R.drawable.condition_weakened,
//        R.drawable.condition_bleeding,
//        R.drawable.condition_stunned
//    )
//
//    private fun initConditions() {
//        strengthGlow= GreenHighlight(strength_icon,this,this.resources)
//        techGlow =GreenHighlight(tech_icon,this,this.resources)
//        insightGlow =GreenHighlight(insight_icon,this,this.resources)
//        conditionsActive = character.conditionsActive
//        conditionViews.add(condition1)
//        conditionViews.add(condition2)
//        conditionViews.add(condition3)
//        conditionViews.add(condition4)
//        conditionViews.add(condition5)
//        for (i in 0..conditionViews.size - 1) {
//            conditionViews[i].setOnLongClickListener(object : View.OnLongClickListener {
//                override fun onLongClick(v: View): Boolean {
//                    removeCondition(v.getTag() as Int)
//                    actionCompleted()
//                    return true
//                }
//            }
//            )
//        }
//        hidden_all.setOnLongClickListener {
//            removeCondition(hidden)
//            true
//        }
//        focused_all.setOnLongClickListener {
//            removeCondition(focused)
//
//            true
//        }
//        weakened_all.setOnLongClickListener {
//            removeCondition(weakened)
//
//            true
//        }
//        bleeding_all.setOnLongClickListener {
//            removeCondition(bleeding)
//            actionCompleted()
//            true
//        }
//        stunned_all.setOnLongClickListener {
//            removeCondition(stunned)
//            actionCompleted()
//            true
//        }
//    }
//
//    fun onAddCondition(view: View) {
//        Sounds.selectSound()
//        conditionsDialog!!.show()
//    }
//
//    fun onWeakened(view: View) {
//
//        conditionsActive[weakened] = !conditionsActive[weakened]
//        if (conditionsActive[weakened]) {
//            character.timesWeakened++
//            Sounds.conditionSound(weakened)
//        } else {
//            //character.timesWeakened--
//            Sounds.selectSound()
//        }
//        updateConditionIcons()
//    }
//
//    fun onBleeding(view: View) {
//
//        conditionsActive[bleeding] = !conditionsActive[bleeding]
//        if (conditionsActive[bleeding]) {
//            character.timesBleeding++
//            Sounds.conditionSound(bleeding)
//        } else {
//            //character.timesBleeding--
//            Sounds.selectSound()
//        }
//        updateConditionIcons()
//    }
//
//    fun onStunned(view: View) {
//
//        conditionsActive[stunned] = !conditionsActive[stunned]
//        if (conditionsActive[stunned]) {
//            character.timesStunned++
//            Sounds.conditionSound(stunned)
//        } else {
//            //character.timesStunned--
//            Sounds.selectSound()
//        }
//        updateConditionIcons()
//    }
//
//    fun onHidden(view: View) {
//
//        conditionsActive[hidden] = !conditionsActive[hidden]
//        if (conditionsActive[hidden]) {
//            character.timesHidden++
//            Sounds.conditionSound(hidden)
//
//        } else {
//            //character.timesHidden--
//            Sounds.selectSound()
//
//        }
//        updateConditionIcons()
//
//
//    }
//
//
//    fun onFocused(view: View) {
//        conditionsActive[focused] = !conditionsActive[focused]
//        if (conditionsActive[focused]) {
//            character.timesFocused++
//            Sounds.conditionSound(focused)
//        } else {
//            //character.timesFocused--
//            Sounds.selectSound()
//        }
//        updateConditionIcons()
//    }
//
//    private fun removeCondition(conditionType: Int) {
//
//        if (actionsLeft > 0 || conditionType == hidden || conditionType == focused || conditionType == weakened || !actionUsage) {
//            conditionsActive[conditionType] = false
//            updateConditionIcons()
//        } else {
//            showNoActionsLeftToast()
//        }
//    }
//
//    fun updateConditionIcons() {
//        //TODO condition save
//
//        character.conditionsActive = conditionsActive
//        character_image.animateConditions = animateConditions
//        //quickSave()
//
//        for (i in 0..conditionViews.size - 1) {
//            conditionViews[i].visibility = View.GONE
//        }
//        show_all_conditions.visibility = View.GONE
//
//
//        if (!animateConditions) {
//            var active = 0;
//            for (i in 0..conditionsActive.size - 1) {
//                if (conditionsActive[i]) {
//                    active++;
//                }
//            }
//            conditions_row2.visibility = View.GONE
//
//            if (active < 5) {
//                show_conditions.visibility = View.VISIBLE
//                show_all_conditions.visibility = View.INVISIBLE
//
//                var conditionType = 0;
//                for (i in 0..active - 1) {
//                    conditionViews[i].visibility = View.VISIBLE
//                    for (j in conditionType..conditionDrawable.size - 1) {
//                        if (conditionsActive[j]) {
//                            conditionViews[i].setImageDrawable(
//                                resources.getDrawable(
//                                    conditionDrawable[conditionType]
//                                )
//                            )
//                            conditionViews[i].setTag(conditionType)
//                            conditionType = j + 1;
//                            break
//                        }
//                        conditionType = j + 1;
//                    }
//                }
//            } else {
//                show_conditions.visibility = View.GONE
//                show_all_conditions.visibility = View.VISIBLE
//            }
//            if (active > 2) {
//                conditions_row2.visibility = View.VISIBLE
//            } else {
//                conditions_row2.visibility = View.GONE
//            }
//        }
//
//
//
//        if (!conditionsActive[hidden]) {
//            conditionsDialog!!.hidden_select.alpha = 1f
//            camouflage.visibility = View.GONE
//        } else {
//            conditionsDialog!!.hidden_select.alpha = 0.5f
//            if (animateConditions) {
//                camouflage.visibility = View.VISIBLE
//            } else {
//                camouflage.visibility = View.GONE
//            }
//        }
//
//
//        if (!conditionsActive[focused]) {
//            conditionsDialog!!.focused_select.alpha = 1f
//            character_image.focused = false
//            strengthGlow!!.disabled()
//            techGlow!!.disabled()
//            insightGlow!!.disabled()
//        } else {
//            conditionsDialog!!.focused_select.alpha = 0.5f
//            character_image.focused = true
//            strengthGlow!!.active()
//            techGlow!!.active()
//            insightGlow!!.active()
//        }
//
//        if (!conditionsActive[stunned]) {
//            conditionsDialog!!.stunned_select.alpha = 1f
//            character_image.stunned = false
//        } else {
//            conditionsDialog!!.stunned_select.alpha = 0.5f
//            character_image.stunned = true
//        }
//        if (!conditionsActive[bleeding]) {
//            conditionsDialog!!.bleeding_select.alpha = 1f
//            character_image.bleeding = false
//            bleeding_add_strain.visibility = View.INVISIBLE
//        } else {
//            conditionsDialog!!.bleeding_select.alpha = 0.5f
//            character_image.bleeding = true
//            bleeding_add_strain.visibility = View.VISIBLE
//        }
//        if (!conditionsActive[weakened]) {
//            conditionsDialog!!.weakened_select.alpha = 1f
//            character_image.weakened = false
//        } else {
//            conditionsDialog!!.weakened_select.alpha = 0.5f
//            character_image.weakened = true
//        }
//
//        updateImages()
//
//    }
//
//
//    //************************************************************************************************************
//    //region Dialogs and Screens
//    //************************************************************************************************************
//
//    var restDialog: Dialog? = null
//    var unwoundDialog: Dialog? = null
//    var conditionsDialog: Dialog? = null
//    var showCardDialog: Dialog? = null
//    var endActivationDialog: Dialog? = null
//    var assistDialog: Dialog? = null
//    var saveDialog: Dialog? = null
//    var settingsScreen: Dialog? = null
//    var statsScreen: Dialog? = null
//    var xpSelectScreen: Dialog? = null
//    var quickViewDialog: Dialog? = null
//    var quickViewButtonDialog: Dialog? = null
//
//    private fun initDialogs() {
//        initRestDialog()
//        initUnwoundDialog()
//        initConditionsDialog()
//
//
//        initShowCardDialog()
//        initEndActivationDialog()
//        initAssistDialog()
//
//        initOptionsDialog()
//
//
//
//
//
//        initXpSelectScreenDialog()
//        initQuickViewDialog()
//
//    }
//
//    private fun initRestDialog() {
//        restDialog = Dialog(this)
//        restDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        restDialog!!.setCancelable(false)
//        restDialog!!.setContentView(R.layout.dialog_rest)
//        restDialog!!.setCanceledOnTouchOutside(true)
//        restDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
//        restDialog!!.rest_button.setOnClickListener {
//            onRest(restDialog!!.rest_button)
//            //TODO
//            //quickSave()
//            true
//        }
//    }
//
//    private fun initUnwoundDialog() {
//        unwoundDialog = Dialog(this)
//        unwoundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        unwoundDialog!!.setCancelable(false)
//        unwoundDialog!!.setContentView(R.layout.dialog_unwound)
//        unwoundDialog!!.setCanceledOnTouchOutside(true)
//        unwoundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
//        unwoundDialog!!.unwound_button.setOnClickListener {
//            onUnwound(unwoundDialog!!.unwound_button)
//            //TODO
//            //quickSave()
//            true
//        }
//    }
//
//    private fun initConditionsDialog() {
//        conditionsDialog = Dialog(this)
//        conditionsDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        conditionsDialog!!.setCancelable(false)
//        conditionsDialog!!.setContentView(R.layout.dialog_conditions)
//        conditionsDialog!!.setCanceledOnTouchOutside(true)
//        conditionsDialog!!.stunned_select.setOnLongClickListener {
//            onShowStunnedCard(conditionsDialog!!.stunned_select)
//            true
//        }
//        conditionsDialog!!.bleeding_select.setOnLongClickListener {
//            onShowBleedingCard(conditionsDialog!!.bleeding_select)
//            true
//        }
//        conditionsDialog!!.weakened_select.setOnLongClickListener {
//            onShowWeakenedCard(conditionsDialog!!.weakened_select)
//            true
//        }
//        conditionsDialog!!.focused_select.setOnLongClickListener {
//            onShowFocusedCard(conditionsDialog!!.focused_select)
//            true
//        }
//        conditionsDialog!!.hidden_select.setOnLongClickListener {
//            onShowHiddenCard(conditionsDialog!!.hidden_select)
//            true
//        }
//        conditionsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
//    }
//
//
//
//
//    private fun initShowCardDialog() {
//        showCardDialog = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
//        showCardDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//        showCardDialog!!.setContentView(R.layout.dialog_show_card)
//        showCardDialog!!.setCancelable(false)
//        showCardDialog!!.setCanceledOnTouchOutside(true)
//        showCardDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
//        showCardDialog!!.show_card_dialog.setOnClickListener {
//            Sounds.selectSound()
//            showCardDialog!!.dismiss()
//            true
//        }
//
//    }
//
//    private fun initEndActivationDialog() {
//        endActivationDialog = Dialog(this)
//        endActivationDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        endActivationDialog!!.setCancelable(false)
//        endActivationDialog!!.setContentView(R.layout.dialog_end_activation)
//        endActivationDialog!!.setCanceledOnTouchOutside(true)
//        endActivationDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
//    }
//
//    private fun initAssistDialog() {
//        assistDialog = Dialog(this)
//        assistDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        assistDialog!!.setCancelable(false)
//        assistDialog!!.setContentView(R.layout.dialog_assist)
//        assistDialog!!.setCanceledOnTouchOutside(true)
//        assistDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
//        assistDialog!!.unwound_button.setOnClickListener {
//            onAssist(assistDialog!!.unwound_button)
//            //TODO
//            quickSave()
//            true
//        }
//    }
//    fun onShowItemCard(view: ImageView) {
//        var image = ((view).drawable as BitmapDrawable).bitmap
//        showCardDialog!!.card_image.setImageBitmap(image)
//        showCardDialog!!.show()
//
//    }
//    override fun onBackPressed() {
//        quickSave()
//        val intent = Intent(this, MainActivity::class.java)
//
//
//        startActivity(intent)
//        finishAffinity()
//    }
//
//    override fun onStop() {
//        quickSave()
//        println("on stop save")
//        super.onStop()
//    }
//}
//
//
//class ModListAdapter internal constructor(
//    val mContext: Activity, var modIndices:
//    ArrayList<Int>
//) :
//    BaseAdapter() {
//
//    var items = arrayListOf<View>()
//    var showCardDialog: Dialog? = null
//    init {
//        showCardDialog = Dialog(mContext)
//        showCardDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//        showCardDialog!!.setContentView(R.layout.dialog_show_card)
//        showCardDialog!!.setCancelable(false)
//        showCardDialog!!.setCanceledOnTouchOutside(true)
//        showCardDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        showCardDialog!!.show_card_dialog.setOnClickListener {
//            showCardDialog!!.cancel()
//            true
//        }
//
//        for (i in 0..modIndices.size - 1) {
//            var item = mContext.layoutInflater.inflate(R.layout.grid_item, null, true)
//            var modIndex = modIndices[i]
//            item.grid_image.setImageResource(Items.itemsArray!![modIndex].resourceId)
//
//            item.setOnClickListener {
//                onShowCard(item.grid_image)
//            }
//            items.add(item)
//        }
//    }
//
//    override fun getCount(): Int {
//        return items.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return position
//    }
//
//    fun onShowCard(view: ImageView) {
//        var image = ((view).drawable as BitmapDrawable).bitmap
//        println(image)
//        showCardDialog!!.card_image.setImageBitmap(image)
//        showCardDialog!!.show()
//    }
//
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    // Create a new ImageView for each item referenced by the Adapter
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        return items.get(position)
//    }
//}
//
//
