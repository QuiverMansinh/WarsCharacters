package com.glasswellapps.iact.character
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.text.method.LinkMovementMethod
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Observer
import androidx.work.*
import com.glasswellapps.iact.*
import com.glasswellapps.iact.R
import com.glasswellapps.iact.bluetooth.BluetoothManager
import com.glasswellapps.iact.inventory.Items
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.characters.*
import com.glasswellapps.iact.inventory.*
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlinx.android.synthetic.main.credits_to_us.*
import kotlinx.android.synthetic.main.dialog_assist.*
import kotlinx.android.synthetic.main.dialog_background.*
import kotlinx.android.synthetic.main.dialog_bio.*
import kotlinx.android.synthetic.main.dialog_options.*
import kotlinx.android.synthetic.main.dialog_rest.*
import kotlinx.android.synthetic.main.dialog_save.*
import kotlinx.android.synthetic.main.dialog_show_card.*

import java.io.InputStream

var height = 0f
var width = 0f

class CharacterScreen : AppCompatActivity() {
    var character: Character = Character()

    var bluetoothManager: BluetoothManager? = null
    lateinit var conditions: ConditionsController
    lateinit var characterAnimation:CharacterScreenAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Sounds.reset(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()

        initScreen()
        characterAnimation = CharacterScreenAnimation(this)
    }

    fun onBluetoothButton(view:View){
        if(bluetoothManager == null) {
            bluetoothManager = BluetoothManager(this, "IATracker", "dd8c0994-aa25-4698-8e30-56f286a98375")
        }
        else{
            bluetoothManager!!.discoverDevices()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothManager.REQUEST_ENABLE_DISCOVERY) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(applicationContext, "Discovery not enabled", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Discovery enabled", Toast.LENGTH_SHORT).show()

            }
        }
        if (requestCode == BluetoothManager.REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(applicationContext, "Bluetooth not enabled", Toast.LENGTH_SHORT)
                    .show()
                bluetoothManager = null
            } else {
                Toast.makeText(applicationContext, "Bluetooth enabled", Toast.LENGTH_SHORT).show()
                bluetoothManager!!.discoverDevices()
            }
        }
    }

    var loadAnimated = false
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        updateStats()
        if(character.damage>0) {
            if (character.damage < character.health) {
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.damage))
                if(character.withdrawn) {
                    character.withdrawn = false
                    val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
                    slide.duration = 500
                    slide.start()
                }
                if (character.isWounded) {
                    character.wounded = 0
                    wounded.animate().alpha(0f)
                    character.isWounded = false
                    updateStats()
                }
            } else if (character.damage < character.health * 2) {
                character.wounded = character.damage - character.health
                //add_damage.setText("" + character.wounded)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.wounded))
                if (!character.isWounded) {
                    wounded.animate().alpha(1f)
                    character.timesWounded++
                    character.isWounded = true
                    updateStats()
                }
                if(character.withdrawn) {
                    character.withdrawn = false
                    val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
                    slide.duration = 500
                    slide.start()
                }
            } else {
                if(!character.withdrawn) {
                    character.withdrawn = true
                    character.timesWithdrawn++
                    val slide = ObjectAnimator.ofFloat(
                        character_images, "translationY", character_images.y, character_images.y,
                        character_image.height.toFloat()
                    )
                    slide.duration = 1500
                    slide.start()
                }
                //add_damage.setText("" + character.health)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.health))
            }
        }
        updateImages()

        if (hasFocus && !loadAnimated) {
            top_panel.animate().alpha(1f)
            bottom_panel.animate().alpha(1f)
            left_buttons.animate().alpha(1f)
            val animButtons = ObjectAnimator.ofFloat(
                left_buttons, "translationX", -left_buttons.width
                    .toFloat(), 0f
            )
            //animBottom.interpolator = DecelerateInterpolator()
            animButtons.duration = (500).toLong()
            animButtons.start()

            right_buttons.animate().alpha(1f)
            val animRightButtons = ObjectAnimator.ofFloat(
                right_buttons, "translationX", right_buttons
                    .width
                    .toFloat(), 0f
            )
            //animBottom.interpolator = DecelerateInterpolator()
            animRightButtons.duration = (500).toLong()
            animRightButtons.start()

            character_images.animate().alpha(1f)
            if(character.damage < character.health*2) {
                val animChar = ObjectAnimator.ofFloat(
                    character_images, "translationY", character_images.height
                        .toFloat(), character_images.height.toFloat(), 0f
                )
                animChar.interpolator = DecelerateInterpolator()
                animChar.duration = (800).toLong()
                animChar.start()
            }
            else{
                val slide = ObjectAnimator.ofFloat(
                    character_images, "translationY", character_images.y, character_images.y,
                    character_image.height.toFloat()
                )
                slide.duration = 1500
                slide.start()
            }
            companion_image.animate().alpha(1f)
            val animcompanion = ObjectAnimator.ofFloat(
                companion_image, "translationX",
                -character_images.width * 1.2f
                    .toFloat(), -character_images.width.toFloat() * 1.2f, 0f
            )
            animcompanion.interpolator = DecelerateInterpolator()
            animcompanion.duration = (800 * 1.2f).toLong()
            animcompanion.start()

            updateSideBarState()
            kill_tracker_bar.visibility = View.VISIBLE
            background_image.alpha=0f
            background_image.animate().alpha(1f)
            camouflage.animate().alpha(1f)
            loadAnimated = true
        }
        quickSave()
    }
    fun resetUI(){
        top_panel.animate().alpha(0f)
        bottom_panel.animate().alpha(0f)
        left_buttons.animate().alpha(0f)
        right_buttons.animate().alpha(0f)
        character_images.animate().alpha(0f)
        companion_image.animate().alpha(0f)
        background_image.alpha = 0f
        loadAnimated = false
    }

    //************************************************************************************************************
    //region Main Screen
    //************************************************************************************************************


    private fun initScreen() {
        var load = intent.getBooleanExtra("Load", false)
        var characterName: String = intent.getStringExtra("CharacterName").toString()


        if (!load) {
            character = CharacterBuilder.create(characterName,this)
            LoadedCharacter.setCharacter(character)

            if (character.startingRangedWeapon != null) {
                character.weapons.add(Items.rangedArray!![0].index)
            }
            if (character.startingMeleeWeapon != null) {
                character.weapons.add(Items.meleeArray!![0].index)
            }
        } else {
            if(LoadedCharacter.getCharacter()!=null) {
                character = LoadedCharacter.getCharacter()
            }
            else{
                finish()
            }
        }
        initDialogs()
        character.loadImages(this)
        if (character.portraitImage == null) {
            character.loadPortraitImage(this)
        }
        fileName.text = "" + character.name


        initDialogs()


        when (character.defence_dice) {
            "white" -> defence.setImageDrawable(resources.getDrawable(R.drawable.dice))
            "black" -> defence.setImageDrawable(resources.getDrawable(R.drawable.dice_black))
            "" -> defence.visibility = View.INVISIBLE
        }

        updateImages()
        updateStats()
        initDamageAndStrain()
        updateStats()
        conditions = ConditionsController(this)



        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_interior.png"))

        if (character.name_short == "jarrod") {
            companion_button.visibility = View.VISIBLE
            companion_button.isClickable = true
        } else {
            companion_button.visibility = View.INVISIBLE
            companion_button.isClickable = false
        }
        background_image.setImageBitmap(character.getBackgroundImage(this))
        camouflage.setImageBitmap(character.getCamoImage(this))
    }


    private fun setDiceColor(dice: ImageView, color: Char) {
        dice.visibility = ImageView.VISIBLE
        when (color) {
            'B' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_blue))
            'G' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_green))
            'Y' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_yellow))
            'R' -> dice.setImageDrawable(resources.getDrawable(R.drawable.dice_red))
            ' ' -> ImageViewCompat.setImageTintList(
                dice, ColorStateList.valueOf(
                    Color.argb(
                        0,
                        0,
                        0,
                        0
                    )
                )
            )
        }
    }

    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        for (i in 1..32) {
            try {
                inputStream = assetManager.open(path)
                options.inSampleSize = i

                bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                break
            } catch (outOfMemoryError: OutOfMemoryError) {

                //println("next size" + i)
            } catch (e: Exception) {
                //e.printStackTrace()
                break
            }
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            //e.printStackTrace()
        }
        return bitmap
    }

    fun updateImages() {


        character.updateCharacterImages(this)
        if (character.conditionAnimSetting) {
            if (character.currentImage != null) {
                character.glowImage = Bitmap.createBitmap(character.currentImage!!)
                val input = Bitmap.createBitmap(character.currentImage!!)
                val output = Bitmap.createBitmap(character.currentImage!!)

                val rs = RenderScript.create(this)
                val blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
                blur.setRadius(25f)
                val tempIn = Allocation.createFromBitmap(rs, input)
                val tempOut = Allocation.createFromBitmap(rs, output)
                blur.setInput(tempIn)
                blur.forEach(tempOut)

                tempOut.copyTo(character.glowImage)

            }
            character_image.setGlowBitmap(character.glowImage)
            character.updateCharacterImages(this)
        }
        character_image.setImageBitmap(character.currentImage)
        character_image.setLayer1Bitmap(character.layer1)
        character_image.setLayer2Bitmap(character.layer2)

        if(!character.name_short.equals("jarrod")){
            if(character.astromech) {
                character.companionImage = (resources.getDrawable(R.drawable.r5_astromech1) as BitmapDrawable).bitmap
            }
            else{
                character.companionImage = null
            }
        }
        println( "ASTROMECH" +character.companionImage + " " + character.astromech )
        if (character.companionImage != null) {
            if (character.conditionsActive[ConditionTypes.HIDDEN] && character
                    .conditionAnimSetting) {
                companion_image.visibility = View.INVISIBLE
            } else {
                companion_image.setImageBitmap(character.companionImage)
                companion_image.visibility = View.VISIBLE
            }
        } else {
            companion_image.visibility = View.GONE
        }
    }

    fun onShowCompanionCard(view: View) {
        if (character.companionCard != null) {
            showCardDialog!!.card_image.setImageBitmap(character.companionCard)
            showCardDialog!!.show()
        }
    }

    fun updateStats() {
        character.health = character.health_default
        character.endurance = character.endurance_default
        character.speed = character.speed_default

        for (i in 0..character.xpCardsEquipped.size - 1) {
            if (character.xpCardsEquipped[i]) {
                if (character.xpHealths[i] != 0) {
                    character.health += character.xpHealths[i]
                }
                if (character.xpEndurances[i] != 0) {
                    character.endurance += character.xpEndurances[i]
                }
                if (character.xpSpeeds[i] != 0) {
                    character.speed += character.xpSpeeds[i]
                }
            }
        }

        for (i in 0..character.accessories.size - 1) {
            character.health += Items.itemsArray!![character.accessories[i]].health
            character.endurance += Items.itemsArray!![character.accessories[i]].endurance
        }
        for (i in 0..character.armor.size - 1) {
            character.health += Items.itemsArray!![character.armor[i]].health
            //character.endurance += Items.armorArray!![character.armor[i]].endurance
        }
        for (i in 0..character.rewards.size - 1) {
            character.health += Items.rewardsArray!![character.rewards[i]].health
            //character.endurance += Items.armorArray!![character.armor[i]].endurance
        }

        if (character.isWounded) {
            character.endurance--
            character.speed--
        }

        setStatColor(health, character.health, character.health_default)
        setStatColor(endurance, character.endurance, character.endurance_default)
        setStatColor(speed, character.speed, character.speed_default)

        health.text = "" + character.health
        endurance.text = "" + character.endurance
        speed.text = "" + character.speed

        if (!character.isWounded) {

            setDiceColor(strength1, character.strength[0])
            setDiceColor(strength2, character.strength[1])
            setDiceColor(strength3, character.strength[2])

            setDiceColor(insight1, character.insight[0])
            setDiceColor(insight2, character.insight[1])
            setDiceColor(insight3, character.insight[2])

            setDiceColor(tech1, character.tech[0])
            setDiceColor(tech2, character.tech[1])
            setDiceColor(tech3, character.tech[2])
        } else {
            setDiceColor(strength1, character.strengthWounded[0])
            setDiceColor(strength2, character.strengthWounded[1])
            setDiceColor(strength3, character.strengthWounded[2])

            setDiceColor(insight1, character.insightWounded[0])
            setDiceColor(insight2, character.insightWounded[1])
            setDiceColor(insight3, character.insightWounded[2])

            setDiceColor(tech1, character.techWounded[0])
            setDiceColor(tech2, character.techWounded[1])
            setDiceColor(tech3, character.techWounded[2])
        }
    }

    private fun setStatColor(stat: TextView, current: Int, default: Int) {
        if (current > default) {
            stat.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.dice_green))
        } else if (current < default) {
            stat.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.stat_orange))
        } else {
            stat.setShadowLayer(5f, 0f, 0f, Color.BLACK)
        }
    }

    fun onHide(view: View) {
        view.visibility = View.INVISIBLE
    }
    //endregion
    //************************************************************************************************************
    //region Damage and Strain
    //************************************************************************************************************

    fun onAddStrain(view: View) {
        if (character.strain < character.endurance) {
            Sounds.strainSound()
            character.strain++
            character.strainTaken++

            if (minus_strain.alpha == 0f) {
                minus_strain.animate().alpha(1f)
            }

            //add_strain.setText("" + character.strain)
            add_strain.setImageDrawable(IANumbers.getNumber(resources, character.strain))
            characterAnimation.playRestAnim()
        } else {
            if(addDamage()) {
                Sounds.damagedSound(this, Sounds.impact)
                characterAnimation.hitAnim()
                character.damageTaken++
                characterAnimation.playRestAnim()
            }
            else{
                Sounds.negativeSound()
            }
        }
    }

    fun onMinusStrain(view: View) {
        if (character.strain > 0) {
            Sounds.selectSound()
            character.strain--
            //add_strain.setText("" + character.strain)
            add_strain.setImageDrawable(IANumbers.getNumber(resources, character.strain))
        }
        if (character.strain == 0) {
            if (minus_strain.alpha > 0f) {
                minus_strain.animate().alpha(0f)
            }
        }
    }

    fun onAddDamage(view: View) {
        if (addDamage()) {
            characterAnimation.playDamageAnim()
            character.damageTaken++
        }
        else{
            Sounds.negativeSound()
        }
    }

    private fun addDamage(): Boolean {
        if (character.damage < character.health * 2) {
            character.damage++


            if (minus_damage.alpha == 0f) {
                minus_damage.animate().alpha(1f)
            }

            if (character.damage < character.health) {

                //add_damage.setText("" + character.damage)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.damage))
            } else if (character.damage < character.health * 2) {

                character.wounded = character.damage - character.health
                //add_damage.setText("" + character.wounded)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.wounded))
                if (!character.isWounded) {
                    wounded.animate().alpha(1f)


                    character.timesWounded++
                    character.isWounded = true
                    quickSave()
                    updateStats()
                }

            } else {
                character.withdrawn = true
                character.timesWithdrawn++
                //add_damage.setText("" + character.health)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.health))
                val slide = ObjectAnimator.ofFloat(
                    character_images, "translationY", character_images.y, character_images.y,
                    character_image.height.toFloat()
                )
                slide.duration = 1500
                slide.start()
                quickSave()
            }
            return true
        }
        return false
    }

    fun onMinusDamage(view: View) {
        Sounds.selectSound()
        minusDamage()
    }

    fun minusDamage(){
        if (character.damage > 0) {

            character.damage--

            if (character.damage < character.health) {
                //add_damage.setText("" + character.damage)
                character.withdrawn = false
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.damage))
                if (character.isWounded) {
                    character.wounded = 0
                    wounded.animate().alpha(0f)
                    character.isWounded = false
                    quickSave()
                    updateStats()
                }
            } else if (character.damage < character.health * 2) {
                character.withdrawn = false
                character.wounded = character.damage - character.health
                //add_damage.setText("" + character.wounded)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.wounded))
                val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
                slide.duration = 500
                slide.start()
                quickSave()
            }
            else{
                character.withdrawn = true
                character.damage = character.health*2
                character.wounded = character.damage - character.health
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.wounded))

            }

        }
        if (character.damage == 0) {
            if (minus_damage.alpha > 0f) {
                minus_damage.animate().alpha(0f)
            }
        }
    }

    fun onUnwound(view: View) {
        Sounds.strainSound()
        characterAnimation.playRestAnim()
        character.damage = 0
        character.wounded = 0
        //add_damage.setText("" + character.damage)
        add_damage.setImageDrawable(IANumbers.getNumber(resources, character.damage))
        wounded.animate().alpha(0f)
        character.isWounded = false
        unwoundDialog!!.dismiss()
        character.withdrawn = false
        val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
        slide.duration = 500
        //slide.start()
        character_images.animate().translationY(0f)
        updateStats()
        quickSave()

    }

    private fun initDamageAndStrain() {
        if (character.damage > 0) {
            if (minus_damage.alpha == 0f) {
                minus_damage.animate().alpha(1f)
            }

            if (character.damage < character.health) {
                //add_damage.setText("" + character.damage)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.damage))

            } else {
                character.wounded = character.damage - character.health
                //add_damage.setText("" + character.wounded)
                add_damage.setImageDrawable(IANumbers.getNumber(resources, character.wounded))
                wounded.animate().alpha(1f)

                character.isWounded = true

                //println()
                //println("withdrawn" + character.withdrawn)
                //println()
                if (character.withdrawn) {
                    //println("SLIDE DOWN")
                    character_images.animate().translationY(character_image.height.toFloat())
                }

            }
        }

        if (character.strain > 0) {
            if (minus_strain.alpha == 0f) {
                minus_strain.animate().alpha(1f)
            }

            //add_strain.setText("" + character.strain)
            add_strain.setImageDrawable(IANumbers.getNumber(resources, character.strain))
        }

        add_damage.setOnLongClickListener {
            if (character.damage >= character.health) {
                unwoundDialog!!.show()
            }
            true
        }
        add_strain.setOnLongClickListener {
            restDialog!!.show()

            true
        }
    }

    //endregion
    //************************************************************************************************************
    //region Turn Actions
    //************************************************************************************************************


    fun onActivate(view: View) {

        if (!character.isActivated) {
            Sounds.selectSound()
            val flipUnactive = ObjectAnimator.ofFloat(unactive, "scaleX", 1f, 0f, 0f, 0f)
            flipUnactive.duration = 200
            flipUnactive.start()

            val flipActive = ObjectAnimator.ofFloat(active, "scaleX", 0f, 0f, 0f, 1f)
            flipActive.duration = 200
            flipActive.start()


            unactive.animate().alpha(0f).duration = 100

            if (character.actionUsageSetting) {
                turnOnActionButtons()
                character.actionsLeft = 2
            }
            character.isActivated = true


        } else {
            onEndActivation(view)
        }
    }

    fun turnOnActionButtons() {

        action1.visibility = View.VISIBLE
        action_button1.visibility = View.VISIBLE
        action_button1.animate().alpha(1f)
        action2.visibility = View.VISIBLE
        action_button2.visibility = View.VISIBLE
        action_button2.animate().alpha(1f)
    }

    fun onEndActivation(view: View) {
        Sounds.selectSound()
        deactivationAnim()
        endActivationDialog!!.dismiss()
        conditions.removeWeakened()

        if (character.actionUsageSetting) {
            turnOffActionButtons()
            if(character.actionsLeft <= 1){
                character.activated++
            }
        }
        else{
            character.activated++
        }
        character.isActivated = false
    }

    fun deactivationAnim(){
        val flipUnactive = ObjectAnimator.ofFloat(unactive, "scaleX", 0f, 0f, 0f, 1f)
        flipUnactive.duration = 200
        flipUnactive.start()

        val flipActive = ObjectAnimator.ofFloat(active, "scaleX", 1f, 0f, 0f, 0f)
        flipActive.duration = 200
        flipActive.start()

        unactive.animate().alpha(1f).duration = 100
    }

    fun turnOffActionButtons() {

        action_button1.animate().alpha(0f)
        //action_button1.visibility = View.GONE

        action_button2.animate().alpha(0f)
        //action_button2.visibility = View.GONE
    }

    fun onEndActivationNo(view: View) {
        Sounds.selectSound()
        endActivationDialog!!.dismiss()
    }

    fun onAction(view: View) {
        if(!character.isActivated){
            return
        }
        Sounds.selectSound()
        action_menu.visibility = View.INVISIBLE
        action_menu.alpha = 0f

        if (character.actionsLeft > 0) {
            //todo add focus symbol to attack
            if (character.conditionsActive[ConditionTypes.FOCUSED]) {
                attack_focused.visibility = View.VISIBLE
            } else {
                attack_focused.visibility = View.GONE
            }

            if (character.conditionsActive[ConditionTypes.HIDDEN]) {
                attack_hidden.visibility = View.VISIBLE
            } else {
                attack_hidden.visibility = View.GONE
            }

            //todo add stun symbol and deactivate to move, special and attack
            if (character.conditionsActive[ConditionTypes.STUNNED]) {
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

            if (character.conditionsActive[ConditionTypes.BLEEDING]) {
                action_remove_bleeding.visibility = View.VISIBLE
            } else {
                action_remove_bleeding.visibility = View.GONE
            }

            action_menu.visibility = View.VISIBLE
            action_menu.animate().alpha(1f)
        }

    }

    fun actionCompleted() {
        if (character.actionUsageSetting) {
            if (character.actionsLeft > 0) {
                character.actionsLeft--
                if (character.actionsLeft == 1) {
                    action1.visibility = View.INVISIBLE
                } else if (character.actionsLeft == 0) {
                    action2.visibility = View.INVISIBLE
                }
                if (character.conditionsActive[ConditionTypes.BLEEDING]) {
                    onAddStrain(add_strain)
                }
                action_menu.visibility = View.INVISIBLE
            }
            if (character.actionsLeft <= 0) {

                action_menu.visibility = View.INVISIBLE
                if (character.isActivated) {
                    endActivationDialog!!.show()
                }
            }
        }
    }

    fun onAttack(view: View) {
        if (character.actionsLeft > 0) {
            if (!character.conditionsActive[ConditionTypes.STUNNED]) {
                conditions.removeFocused()
                conditions.removeHidden()
                actionCompleted()
                Sounds.attackSound()
                character.attacksMade++

            }
            else{
                Sounds.negativeSound()
            }
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onMove(view: View) {
        if (character.actionsLeft > 0) {
            Sounds.movingSound()
            if (!character.conditionsActive[ConditionTypes.STUNNED]) {
                actionCompleted()

                character.movesTaken++
            }
            else{
                Sounds.negativeSound()
            }
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onSpecial(view: View) {

        if (character.actionsLeft > 0) {
            if (!character.conditionsActive[ConditionTypes.STUNNED]) {
                actionCompleted()
                Sounds.specialSound()
                character.specialActions++
            }
            else{
                Sounds.negativeSound()
            }
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onInteract(view: View) {
        if (character.actionsLeft > 0) {
            actionCompleted()
            Sounds.interactSound()
            character.interactsUsed++
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onPickUpCrate(view: View) {
        if (character.actionsLeft > 0) {
            actionCompleted()
            Sounds.crateSound()
            character.cratesPickedUp++
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onRest(view: View) {
        rest(view)
        if (character.actionsLeft > 0 && character.actionUsageSetting) {
            actionCompleted()
        }
    }

    fun rest(view:View){
        character.strain -= character.endurance
        if (character.strain < 0) {
            var healAmount = -character.strain
            if(character.isWounded){
                healAmount = Math.min(character.wounded,healAmount)
            }
            for (i in 1..healAmount) {
                minusDamage()
            }
            character.strain = 0
        }
        //add_strain.setText("" + character.strain)
        add_strain.setImageDrawable(IANumbers.getNumber(resources, character.strain))
        Sounds.strainSound()
        characterAnimation.playRestAnim()
        character.timesRested++
        quickSave()
        restDialog!!.dismiss()
    }

    fun onRemoveStun(view: View) {
        if (character.actionsLeft > 0 || !character.actionUsageSetting) {
            conditions.removeStunned()
            Sounds.selectSound()
            actionCompleted()
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onRemoveBleeding(view: View) {
        if (character.actionsLeft > 0 || !character.actionUsageSetting) {
            conditions.removeBleeding()
            Sounds.selectSound()
            actionCompleted()
        } else {
            showNoActionsLeftToast()
        }
    }

    fun onShowCard(view:View){
        conditions.onShowCard(view);
    }

    private fun showNoActionsLeftToast() {
        Sounds.negativeSound()
        val noActionsLeftToast = Toast(this)
        noActionsLeftToast.duration = Toast.LENGTH_SHORT
        noActionsLeftToast.view = layoutInflater.inflate(
            R.layout.toast_no_actions_left,
            character_view_group,
            false
        )
        noActionsLeftToast.setGravity(Gravity.CENTER, 0, 0)
        noActionsLeftToast.show()
    }

    fun onNextMission(view: View) {
        Sounds.selectSound()
        nextMissionDialog!!.dismiss()
        if (character.damage > 0) {
            if (character.withdrawn) {
                val slide = ObjectAnimator.ofFloat(character_images, "translationY", 0f)
                slide.duration = 500
                slide.start()
                character.withdrawn = false
            }
            if(character.isWounded){
                character.wounded = 0
                wounded.animate().alpha(0f)
                character.isWounded = false
            }

            character.damage = 0
            add_damage.setImageDrawable(IANumbers.getNumber(resources, character.damage))
            minus_damage.animate().alpha(0f)
        }
        if (character.strain > 0) {
            character.strain = 0
            add_strain.setImageDrawable(IANumbers.getNumber(resources, character.strain))
            minus_strain.animate().alpha(0f)
        }
        character.conditionsActive = BooleanArray(5)
        if(character.isActivated) {
            turnOffActionButtons()
            deactivationAnim()
            character.isActivated = false
        }
        character.actionsLeft = 0
        conditions.updateConditionIcons()
        updateStats()
        quickSave()
    }

    fun onNextMissionNo(view: View) {
        Sounds.selectSound()
        nextMissionDialog!!.dismiss()
    }

    //endregion
    //************************************************************************************************************
    //region Show Condition Cards
    //************************************************************************************************************




    var sideMenuState = 0

//endregion
    //************************************************************************************************************
    //region Options Menu
    //************************************************************************************************************

    fun onShowOptions(view: View) {
        Sounds.selectSound()
        optionsDialog!!.show()
    }

    fun onBiography(view: View) {
        Sounds.selectSound()
        optionsDialog!!.dismiss()
        bioDialog!!.bio_title.text = character.bio_title
        bioDialog!!.bio_quote.text = character.bio_quote
        bioDialog!!.bio_text.text = character.bio_text
        bioDialog!!.show()
    }

    fun onPower(view: View) {
        Sounds.selectSound()
        optionsDialog!!.dismiss()

        if (!character.isWounded) {
            showCardDialog!!.card_image.setImageBitmap(character.power)
        } else {
            showCardDialog!!.card_image.setImageBitmap(character.power_wounded)
        }

        showCardDialog!!.show()
    }

    fun onSave(view: View) {
        Sounds.selectSound()
        if (character.file_name.equals("autosave")) {
            saveDialog!!.show()
        } else {

            quickSave()
        }
        optionsDialog!!.dismiss()

    }

    fun onBackground(view: View) {
        Sounds.selectSound()
        optionsDialog!!.dismiss()
        backgroundDialog!!.show()
    }

    fun onSettings(view: View) {
        Sounds.selectSound()
        optionsDialog!!.dismiss()
        settingsScreen.showDialog()
    }


    fun onStatistics(view: View) {
        Sounds.selectSound()
        optionsDialog!!.dismiss()
        statsScreen.showDialog()
    }

    fun onCredits(view: View) {
        Sounds.selectSound()
        optionsDialog!!.dismiss()
        developersCreditsScreen!!.show()
    }


    //Backgrounds
    fun onBackgroundSnow(view: View) {
        Sounds.selectSound()
        character.background = "snow"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_snow.jpg"))
        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_snow.png"))

    }

    fun onBackgroundJungle(view: View) {
        Sounds.selectSound()
        character.background = "jungle"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_jungle.jpg"))
        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_jungle.png"))

    }

    fun onBackgroundDesert(view: View) {
        Sounds.selectSound()
        character.background = "desert"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_desert.jpg"))
        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_desert.png"))

    }

    fun onBackgroundBespin(view: View) {
        Sounds.selectSound()
        character.background = "bespin"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_bespin.jpg"))
        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_bespin.png"))

    }

    fun onBackgroundCity(view: View) {
        Sounds.selectSound()
        character.background = "city"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_city.jpg"))
        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_city.png"))

    }

    fun onBackgroundInterior(view: View) {
        Sounds.selectSound()
        character.background = "interior"
        background_image.setImageBitmap(getBitmap(this, "backgrounds/background_interior.jpg"))
        camouflage.setImageBitmap(getBitmap(this, "backgrounds/camo_interior.png"))


    }

    //endregion
    //************************************************************************************************************
    //region Side Navigation
    //************************************************************************************************************
    fun updateSideBarState(){
        when (sideMenuState) {
            -1 -> {
                extend_down_button.animate().alpha(0f)
                extend_up_button.animate().alpha(1f)
                kill_tracker_bar.animate().translationY(0f)
                menu_bar.animate().translationY(height)
            }
            0 -> {
                extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(1f)
                kill_tracker_bar.animate().translationY(-height)
                menu_bar.animate().translationY(0f)
            }
            1 -> {
                extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(0f)
                kill_tracker_bar.animate().translationY(-2* height)
                menu_bar.animate().translationY(-height)
            }
        }
    }

    fun onExtendDown(view: View) {
        if (sideMenuState > -1) {
            Sounds.selectSound()
            sideMenuState--
            updateSideBarState()
        }
    }

    fun onExtendUp(view: View) {
        if (sideMenuState < 1) {
            Sounds.selectSound()
            sideMenuState++
            updateSideBarState()
        }
    }

    fun onReward(view: View) {
        //resetUI()
        Sounds.selectSound()
        val intent = Intent(this, RewardsScreen::class.java)
        //intent.putExtra("Load",false)
        startActivity(intent)

    }

    fun onAccessory(view: View) {
        Sounds.selectSound()
        val intent = Intent(this, AccScreen::class.java)
        startActivity(intent)

    }

    fun onArmour(view: View) {
        Sounds.selectSound()
        val intent = Intent(this, ArmorScreen::class.java)
        startActivity(intent)

    }

    fun onMelee(view: View) {
        Sounds.selectSound()
        val intent = Intent(this, MeleeScreen::class.java)
        startActivity(intent)

    }

    fun onRange(view: View) {
        Sounds.selectSound()
        val intent = Intent(this, RangedScreen::class.java)
        startActivity(intent)
    }

    fun onXPScreen(view: View) {
        //resetUI()
        Sounds.selectSound()
        xpScreen.showDialog()
    }

    //endregion
    //************************************************************************************************************
    //region Dialogs and Screens
    //************************************************************************************************************
    lateinit var statsScreen:StatsScreen
    lateinit var xpScreen:XPScreen
    lateinit var killTracker:KillTracker
    lateinit var quickView:QuickView
    lateinit var settingsScreen:SettingsScreen


    var restDialog: Dialog? = null
    var unwoundDialog: Dialog? = null

    var showCardDialog: Dialog? = null

    var endActivationDialog: Dialog? = null
    var optionsDialog: Dialog? = null
    var bioDialog: Dialog? = null
    var saveDialog: Dialog? = null
    var backgroundDialog: Dialog? = null
    var developersCreditsScreen: Dialog? = null
    var nextMissionDialog: Dialog? = null

    private fun initDialogs() {

        initRestDialog()
        initUnwoundDialog()
        initOptionsDialog()
        initSaveDialog()
        initShowCardDialog()
        initEndActivationDialog()
        initBioDialog()
        initBackgroundDialog()
        initCreditsScreenDialog()
        initNextMission()

        settingsScreen = SettingsScreen(this)
        quickView = QuickView(this)
        statsScreen = StatsScreen(this)
        xpScreen = XPScreen(this)
        killTracker = KillTracker(this)
    }

    private fun initRestDialog() {
        restDialog = Dialog(this)
        restDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        restDialog!!.setCancelable(false)
        restDialog!!.setContentView(R.layout.dialog_rest)
        restDialog!!.setCanceledOnTouchOutside(true)
        restDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        restDialog!!.rest_button.setOnClickListener {
            onRest(restDialog!!.rest_button)
            //TODO
            //quickSave()
            true
        }
    }

    private fun initUnwoundDialog() {
        unwoundDialog = Dialog(this)
        unwoundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        unwoundDialog!!.setCancelable(false)
        unwoundDialog!!.setContentView(R.layout.dialog_unwound)
        unwoundDialog!!.setCanceledOnTouchOutside(true)
        unwoundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        unwoundDialog!!.remove_condition_button.setOnClickListener {
            onUnwound(unwoundDialog!!.remove_condition_button)
            //TODO

            true
        }
    }



    private fun initOptionsDialog() {
        optionsDialog = Dialog(this)
        optionsDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        optionsDialog!!.setCancelable(false)
        optionsDialog!!.setContentView(R.layout.dialog_options)
        optionsDialog!!.setCanceledOnTouchOutside(true)
        optionsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        optionsDialog!!.bioOption.setOnClickListener {
            onBiography(optionsDialog!!.bioOption)
        }
        optionsDialog!!.powerOption.setOnClickListener {
            onPower(optionsDialog!!.powerOption)
        }
        optionsDialog!!.settingsOption.setOnClickListener {
            onSettings(optionsDialog!!.settingsOption)
        }
        optionsDialog!!.saveOption.setOnClickListener {
            onSave(optionsDialog!!.saveOption)
        }
        optionsDialog!!.statsOption.setOnClickListener {
            onStatistics(optionsDialog!!.statsOption)
        }
        optionsDialog!!.backgroundOption.setOnClickListener {
            onBackground(optionsDialog!!.backgroundOption)
        }
        optionsDialog!!.creditsOption.setOnClickListener {
            onCredits(optionsDialog!!.backgroundOption)
        }
    }

    private fun initSaveDialog() {
        saveDialog = Dialog(this)
        saveDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        saveDialog!!.setCancelable(false)
        saveDialog!!.setContentView(R.layout.dialog_save)
        saveDialog!!.setCanceledOnTouchOutside(true)
        saveDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        saveDialog!!.save_button.setOnClickListener {
            if (character.file_name.equals("autosave")) {

                firstManualSave()
            } else {
                quickSave()
            }
            Sounds.selectSound()
            saveDialog!!.dismiss()
            true
        }
        saveDialog!!.cancel_button.setOnClickListener {
            Sounds.selectSound()
            saveDialog!!.dismiss()
            true
        }
    }

    private fun initShowCardDialog() {
        showCardDialog = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        showCardDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        showCardDialog!!.setContentView(R.layout.dialog_show_card)
        showCardDialog!!.setCancelable(true)
        showCardDialog!!.setCanceledOnTouchOutside(true)
        showCardDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        showCardDialog!!.show_card_dialog.setOnClickListener {
            Sounds.selectSound()
            showCardDialog!!.dismiss()
            true
        }

    }




    private fun initEndActivationDialog() {
        endActivationDialog = Dialog(this)
        endActivationDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        endActivationDialog!!.setCancelable(false)
        endActivationDialog!!.setContentView(R.layout.dialog_end_activation)
        endActivationDialog!!.setCanceledOnTouchOutside(true)
        endActivationDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
    }



    private fun initBioDialog() {
        bioDialog = Dialog(this)
        bioDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bioDialog!!.setCancelable(true)
        bioDialog!!.setContentView(R.layout.dialog_bio)
        bioDialog!!.setCanceledOnTouchOutside(true)
        bioDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        bioDialog!!.bio_layout.setOnClickListener {
            Sounds.selectSound()
            bioDialog!!.dismiss()
            true
        }
    }

    private fun initCreditsScreenDialog() {
        developersCreditsScreen =
            Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        developersCreditsScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        developersCreditsScreen!!.setCancelable(false)
        developersCreditsScreen!!.setContentView(R.layout.credits_to_us)
        developersCreditsScreen!!.setCanceledOnTouchOutside(true)
        developersCreditsScreen!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        developersCreditsScreen!!.textView41.movementMethod = LinkMovementMethod.getInstance()
        developersCreditsScreen!!.textView43.movementMethod = LinkMovementMethod.getInstance()
        developersCreditsScreen!!.textView44.movementMethod = LinkMovementMethod.getInstance()
        developersCreditsScreen!!.mannyPortfolio.movementMethod = LinkMovementMethod.getInstance()
        developersCreditsScreen!!.davidPortfolio.movementMethod = LinkMovementMethod.getInstance()
        developersCreditsScreen!!.mikeImageLink.setOnClickListener{
            var linkIntent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse("http://www.mikeglasswell.com/"))
            startActivity(linkIntent)
        }
        developersCreditsScreen!!.mannyImageLink.setOnClickListener{
            var linkIntent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse("https://mansinh-d25ff.web.app"))
            startActivity(linkIntent)
        }
        developersCreditsScreen!!.HeroXImageLink.setOnClickListener{
            var linkIntent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse("https://twitter.com/Matthew_Hero_X"))
            startActivity(linkIntent)
        }
    }

   
    private fun initNextMission(){
        nextMissionDialog = Dialog(this)
        nextMissionDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        nextMissionDialog!!.setCancelable(false)
        nextMissionDialog!!.setContentView(R.layout.dialog_next_mission)
        nextMissionDialog!!.setCanceledOnTouchOutside(true)
        nextMissionDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))

        activationButton.setOnLongClickListener{
            nextMissionDialog!!.show()
            true
        }

    }


    private fun initBackgroundDialog() {
        backgroundDialog = Dialog(this)
        backgroundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        backgroundDialog!!.setCancelable(false)
        backgroundDialog!!.setContentView(R.layout.dialog_background)
        backgroundDialog!!.setCanceledOnTouchOutside(true)
        backgroundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        backgroundDialog!!.desert_background.setOnClickListener {
            onBackgroundDesert(backgroundDialog!!.desert_background)
            //quickSave()
        }
        backgroundDialog!!.snow_background.setOnClickListener {
            onBackgroundSnow(backgroundDialog!!.snow_background)
            //quickSave()
        }
        backgroundDialog!!.jungle_background.setOnClickListener {
            onBackgroundJungle(backgroundDialog!!.jungle_background)
            // quickSave()
        }
        backgroundDialog!!.interior_background.setOnClickListener {
            onBackgroundInterior(backgroundDialog!!.interior_background)
            //  quickSave()
        }
        backgroundDialog!!.city_background.setOnClickListener {
            onBackgroundCity(backgroundDialog!!.city_background)
            //  quickSave()
        }
        backgroundDialog!!.bespin_background.setOnClickListener {
            onBackgroundBespin(backgroundDialog!!.bespin_background)
            //  quickSave()
        }
    }



    fun onShowItemCard(view: ImageView) {
        var image = ((view).drawable as BitmapDrawable).bitmap
        showCardDialog!!.card_image.setImageBitmap(image)
        showCardDialog!!.show()

    }

    //************************************************************************************************************
    //region Saving
    //************************************************************************************************************

    fun quickSave() {
        //if(secondsSinceLastSave > 3) {
        //val character = MainActivity.selectedCharacter
        startSavingAnimation()
        val saveWorkRequestBuilder = OneTimeWorkRequest.Builder(SaveWorker::class.java)
        val data = Data.Builder()

        saveWorkRequestBuilder.setInputData(data.build())
        saveWorkRequestBuilder.addTag("save")

        val saveWorkRequest =saveWorkRequestBuilder.build()

        WorkManager.getInstance(this).enqueue(saveWorkRequest)
        WorkManager.getInstance(this)
            .getWorkInfosByTagLiveData("save")
            .observe(this, Observer<List<WorkInfo>> {
                    workStatusList ->
                val currentWorkStatus = workStatusList ?.getOrNull(0)
                if (currentWorkStatus ?.state ?.isFinished == true)
                {
                    WorkManager.getInstance(this)
                        .getWorkInfosByTagLiveData("save").removeObservers(this)
                    stopSavingAnimation()
                }
            })

    }
    lateinit var savingAnimation:ObjectAnimator
    fun startSavingAnimation(){
        saving_icon.alpha=0f

        saving_icon.animate().alpha(1f)

        savingAnimation = ObjectAnimator.ofFloat(saving_icon, "scaleX", 0f,0.8f, 1f,1f,1f,0.8f,
            0f)

        savingAnimation.repeatCount = -1
        savingAnimation.duration = 500
        savingAnimation.start()
    }

    fun stopSavingAnimation(){
        val handler = Handler()
        handler.postDelayed(Runnable {
            saving_icon.animate().alpha(0f)
            handler.postDelayed(Runnable {
                savingAnimation.cancel()
            }, 1000)

        }, 1000)
    }

    fun firstManualSave() {

        println("FIRST MANUAL SAVE character " + character)
        character.file_name = "" + saveDialog!!.save_name.text.toString()
        quickSave()
    }

    override fun onBackPressed() {
        quickSave()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun onStop() {
        quickSave()
        println("on stop save")
        super.onStop()
    }
//endregion
}





