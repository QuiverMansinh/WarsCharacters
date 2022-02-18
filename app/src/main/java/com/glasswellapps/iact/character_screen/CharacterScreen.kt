package com.glasswellapps.iact.character_screen
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.glasswellapps.iact.*
import com.glasswellapps.iact.R
import com.glasswellapps.iact.character_screen.controllers.CharacterImageController
import com.glasswellapps.iact.character_screen.controllers.*
import com.glasswellapps.iact.character_screen.controllers.DamageStrainController
import com.glasswellapps.iact.character_screen.views.StatView
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.characters.*
import com.glasswellapps.iact.loading.LoadedCharacter
import com.glasswellapps.iact.imperial.BluetoothController
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlinx.android.synthetic.main.activity_character_screen.background_image
import kotlinx.android.synthetic.main.activity_character_screen.bottom_panel
import kotlinx.android.synthetic.main.activity_character_screen.camouflage
import kotlinx.android.synthetic.main.activity_character_screen.character_images
import kotlinx.android.synthetic.main.activity_character_screen.companion_image
import kotlinx.android.synthetic.main.activity_character_screen.fileName
import kotlinx.android.synthetic.main.activity_character_screen.top_panel
import kotlinx.android.synthetic.main.dialog_rest.*

class CharacterScreen : AppCompatActivity() {
    var character: Character = Character()
    var height = 0f
    var width = 0f

    lateinit var cardDisplay: CardDisplay

    private lateinit var conditionsController: ConditionsController
    private lateinit var damageStrainController: DamageStrainController
    private lateinit var killTrackerController: KillTrackerController
    private lateinit var quickViewController: QuickViewController
    private lateinit var navigationController: NavigationController
    private lateinit var actionController: ActionController
    private lateinit var bluetoothController: BluetoothController
    private lateinit var savingController: SavingController
    private lateinit var restDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_screen)
        Sounds.reset(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()

        initCharacter()
        initControllers()
        updateImages()
        updateStats()
    }
    private fun initCharacter() {
        if(LoadedCharacter.getActiveCharacter() == null) {
            finish()
        }
        character = LoadedCharacter.getActiveCharacter()

        character.loadImages(this)
        if (character.portraitImage == null) {
            character.loadPortraitImage(this)
        }
        fileName.text = "" + character.name
        if (character.name_short == "jarrod") {
            companion_button.visibility = View.VISIBLE
            companion_button.isClickable = true
        } else {
            companion_button.visibility = View.INVISIBLE
            companion_button.isClickable = false
        }
        LoadedCharacter.setIsInteractable(true)
    }
    private fun initControllers() {
        initShowCardDialog()
        initRestDialog()

        killTrackerController = KillTrackerController(this)
        navigationController = NavigationController(this)
        actionController = ActionController(this)
        damageStrainController = DamageStrainController(this)
        conditionsController = ConditionsController(this)
        quickViewController = QuickViewController(this)
        bluetoothController =
            BluetoothController(this)
        savingController = SavingController(this, saving_icon)
        savingController.addCharacterToSave(character)
        StatView.setDice(character, defence, resources)
    }

    fun actionCompleted(){ actionController.actionCompleted()}
    fun onXPScreen() { navigationController.onXPScreen() }
    fun onReward() { navigationController.onReward() }
    fun onHide(view: View) { view.visibility = View.INVISIBLE }

    //************************************************************************************************************
    //region Conditions
    //************************************************************************************************************

    fun onRemoveFocused() { conditionsController.removeFocused() }
    fun onRemoveHidden() { conditionsController.removeHidden() }
    fun onRemoveStun() { conditionsController.removeStun() }
    fun onRemoveBleeding() { conditionsController.removeBleeding() }
    fun onRemoveWeakened() { conditionsController.removeWeakened() }

    //endregion
    //************************************************************************************************************
    //region Damage and Strain
    //************************************************************************************************************

    fun onAddStrain() { damageStrainController.addStrain() }
    fun onUnwound() { damageStrainController.unwound() }
    private fun initRestDialog(){
        restDialog = Dialog(this)
        restDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        restDialog.setCancelable(false)
        restDialog.setContentView(R.layout.dialog_rest)
        restDialog.setCanceledOnTouchOutside(true)
        restDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        restDialog.rest_button.setOnClickListener {
            onRest()
        }
    }
    fun onRest() {
        damageStrainController.onRest()
        savingController.quickSave()
        restDialog.dismiss()
        if (character.actionsLeft > 0 && character.actionUsageSetting) {
            actionCompleted()
        }
    }

    //endregion
    //************************************************************************************************************
    //region Show Cards
    //************************************************************************************************************


    fun onShowConditionCard(view: View){ conditionsController.onShowCard(view)}
    fun onShowCompanionCard(view: View) {
        if (character.companionCard != null) {
            cardDisplay.onShowCard(character.companionCard!!)
        }
    }
    fun onShowRestDialog(){
        restDialog.show()
    }
    fun onShowCard(image: Bitmap) {
        cardDisplay.onShowCard(image)
    }

    private fun initShowCardDialog() {
        cardDisplay = CardDisplay(this)
    }

    //endregion
    //************************************************************************************************************
    //region Saving
    //************************************************************************************************************

    fun quickSave() {
        savingController.quickSave()
    }
    fun showSaveDialog() {
        savingController.showSaveDialog()
    }
    // show save dialog
    override fun onBackPressed() {
        savingController.quickSave()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
    override fun onStop() {
        savingController.quickSave()
        println("on stop save")
        super.onStop()
    }

    //endregion
    //************************************************************************************************************
    //region Bluetooth
    //************************************************************************************************************

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        bluetoothController.onResult(requestCode, resultCode, data);
    }

    //endregion
    //************************************************************************************************************
    //region Updating
    //************************************************************************************************************

    fun updateNetwork(updateImages: Boolean){ bluetoothController.sendCharacter(updateImages); }
    fun updateStats(){ StatController.Companion.update(character,health,endurance,speed, arrayOf
        (strength1,
        strength2,strength3), arrayOf(insight1,insight2,insight3), arrayOf(tech1,tech2,tech3),
        resources);
        updateNetwork(false)}
    fun updateImages() { CharacterImageController.update(character,character_image,
        companion_image,this);
        updateNetwork(true) }
    fun updateConditions(){ conditionsController.update(); updateNetwork(false)}

    private var loadAnimated = false
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus && !loadAnimated) {
            startAnimation()
        }

        updateStats()
        updateConditions()
        updateActionUsage()
        updateImages()
        savingController.quickSave()
    }

    private fun updateActionUsage(){
        if (character.actionUsageSetting) {
            if (character.isActivated && character.actionsLeft >0) {
                actionController.turnOnActionButtons()
            }
        } else {
            actionController.turnOffActionButtons()
        }
    }
    private fun startAnimation(){
        actionController.turnOffActionButtons()
        top_panel.animate().alpha(1f)
        bottom_panel.animate().alpha(1f)
        slideLeftButtonsIn()
        slideRightButtonsIn()
        character_images.animate().alpha(1f)
        slideCharacterIn()
        slideCompanionIn()
        navigationController.update()
        kill_tracker_bar.visibility = View.VISIBLE
        background_image.alpha=0f
        background_image.animate().alpha(1f)
        camouflage.animate().alpha(1f)
        loadAnimated = true
    }

    private fun slideLeftButtonsIn(){
        left_buttons.animate().alpha(1f)
        val animButtons = ObjectAnimator.ofFloat(
            left_buttons, "translationX", -left_buttons.width
                .toFloat(), 0f
        )
        animButtons.duration = (500).toLong()
        animButtons.start()
    }

    private fun slideRightButtonsIn(){
        right_buttons.animate().alpha(1f)
        val animRightButtons = ObjectAnimator.ofFloat(
            right_buttons, "translationX", right_buttons.width.toFloat(), 0f
        )
        animRightButtons.duration = (500).toLong()
        animRightButtons.start()
    }

    private fun slideCharacterIn(){
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
    }

    private fun slideCompanionIn(){
        companion_image.animate().alpha(1f)
        val animCompanion = ObjectAnimator.ofFloat(
            companion_image, "translationX",
            -character_images.width * 1.2f, -character_images.width.toFloat() * 1.2f, 0f
        )
        animCompanion.interpolator = DecelerateInterpolator()
        animCompanion.duration = (800 * 1.2f).toLong()
        animCompanion.start()
    }


    //endregion
}





