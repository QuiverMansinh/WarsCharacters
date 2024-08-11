package com.glasswellapps.iact.character_screen
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.glasswellapps.iact.CardDisplay
import com.glasswellapps.iact.MainActivity
import com.glasswellapps.iact.R
import com.glasswellapps.iact.character_screen.controllers.*
import com.glasswellapps.iact.character_screen.views.StatView
import com.glasswellapps.iact.characters.Character
import com.glasswellapps.iact.effects.LightSaberMotionController
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.loading.CharacterHolder
import com.glasswellapps.iact.multiplayer.BluetoothController
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlinx.android.synthetic.main.dialog_assist.*
import kotlinx.android.synthetic.main.dialog_rest.*


class CharacterScreen : AppCompatActivity() {
    var character: Character = Character()
    var height = 0f
    var width = 0f
    var isTablet = false;
    val REQUEST_COARSE_LOCATION = 3
    val REQUEST_FINE_LOCATION = 4
    lateinit var cardDisplay: CardDisplay
    private lateinit var conditionsController: ConditionsController
    private lateinit var damageStrainController: DamageStrainController
    private lateinit var killTrackerController: KillTrackerController
    private lateinit var quickViewController: QuickViewController
    private lateinit var navigationController: NavigationController
    private lateinit var actionController: ActionController
    private lateinit var gameOverController: GameOverController
    private lateinit var bluetoothController: BluetoothController
    private lateinit var savingController: SavingController
    private lateinit var lightSaberSoundController: LightSaberMotionController
    private lateinit var restDialog: Dialog
    private lateinit var unwoundDialog: Dialog
    private lateinit var screenSaverController: ScreenSaverController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_screen)
        Sounds.reset(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()
        var from: String = intent.getStringExtra("from").toString()
        detectDeviceType(displayMetrics)
        initCharacter()
        initControllers()
        updateImages()
        updateStats()
        when(from){
            "party"->initPartyButton()
        }
        // View (Sub-Class) where onTouchEvent is implemented

        savingController.quickSave()
    }
    private fun detectDeviceType(displayMetrics:DisplayMetrics){
        var hi = displayMetrics.heightPixels/displayMetrics.ydpi;
        var wi = displayMetrics.widthPixels/displayMetrics.xdpi;
        val diagonal = Math.sqrt((wi*wi + hi*hi).toDouble())
        if(diagonal > 7) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            System.out.println("Tablet")
            isTablet = true;
        }
        else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            System.out.println("Phone")
            isTablet = false;
        }

    }
    private fun initPartyButton(){
        if(CharacterHolder.getParty() == null) return
        party_button.setOnClickListener{ onPartyMode() }
        party_button.visibility = View.VISIBLE

        val partyButtons = arrayListOf<ImageView>()
        partyButtons.add(party_button1)
        partyButtons.add(party_button2)
        partyButtons.add(party_button3)
        partyButtons.add(party_button4)
        for(i in 0..partyButtons.size-1){
            partyButtons[i].visibility = View.GONE
        }
        for(i in 0..CharacterHolder.getParty().size-1){
            val partyCharacter = CharacterHolder.getParty()[i]
            if(partyCharacter.name_short!=character.name_short) {
                partyButtons[i].visibility = View.VISIBLE
                //partyCharacter.loadPortraitImage(this)
                partyButtons[i].setImageDrawable(partyCharacter.portraitImage)
                partyButtons[i].tag = i
                partyButtons[i].setOnClickListener(View.OnClickListener {
                    toCharacter(partyButtons[i].tag as Int)
                })
            }
        }
    }
    fun onPartyMode() {
        Sounds.selectSound()
        super.onBackPressed()
        character_image.onStop()
        navigationController.onStop()
        finish()
    }
    fun toCharacter(playerIndex:Int){
        Sounds.selectSound()
        val character = CharacterHolder.getParty()[playerIndex]
        CharacterHolder.setActiveCharacter(character)
        savingController.quickSave()
        val intent = Intent(this, CharacterScreen::class.java)
        intent.putExtra("from", "party")
        startActivity(intent)
        finish()
    }
    private fun initCharacter() {
        if(CharacterHolder.getActiveCharacter() == null) {
            finish()
            return
        }
        character = CharacterHolder.getActiveCharacter()

        character.loadCardImages(this)
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
        CharacterHolder.setIsInteractable(true)
    }
    private fun initControllers() {
        initShowCardDialog()
        initRestDialog()
        initUnwoundDialog()

        killTrackerController = KillTrackerController(this)
        navigationController = NavigationController(this)
        actionController = ActionController(this)
        gameOverController = GameOverController(this, actionController)
        damageStrainController = DamageStrainController(this)
        conditionsController = ConditionsController(this)
        quickViewController = QuickViewController(this)
        bluetoothController =
            BluetoothController(this)
        savingController = SavingController(this, saving_icon)
        savingController.addCharacterToSave(character)
        StatView.setDice(character, defence, resources)

        lightSaberSoundController = LightSaberMotionController(this)
        screenSaverController = ScreenSaverController(this)
    }

    fun actionCompleted(){ actionController.actionCompleted()}
    fun onXPScreen() { navigationController.onXPScreen() }
    fun onReward() { navigationController.onReward() }
    fun onHide(view: View) { view.visibility = View.INVISIBLE }


    //************************************************************************************************************
    //region Bluetooth
    //************************************************************************************************************

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        bluetoothController.onResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        bluetoothController.onResult(requestCode,permissions,grantResults)
    }

    //endregion

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

    fun initUnwoundDialog(){
        unwoundDialog = Dialog(this)
        unwoundDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        unwoundDialog.setCancelable(false)
        unwoundDialog.setContentView(R.layout.dialog_unwound)
        unwoundDialog.setCanceledOnTouchOutside(true)
        unwoundDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        unwoundDialog.remove_condition_button.setOnClickListener { onUnwound() }
    }

    fun initRestDialog() {
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
    fun onAddStrain() { damageStrainController.addStrain() }
    fun onUnwound() {
        damageStrainController.unwound()
        Sounds.strainSound()
        savingController.quickSave()
        unwoundDialog.dismiss()
    }
    fun onRest() {
        damageStrainController.onRest()
        //savingController.quickSave()
        actionCompleted()
        restDialog.dismiss()
    }
    fun onNextMission(){
        damageStrainController.unwound()
        damageStrainController.resetStrain()
        character.strain = 0
        character.conditionsActive = BooleanArray(5)
        Sounds.strainSound()
        updateConditions()
        updateStats()
        quickSave()
    }
    fun onShowUnwoundDialog(){
        unwoundDialog.show()
    }
    fun onShowRestDialog(){
        restDialog.show()
    }

    fun onGameOver(){
        gameOverController.onGameOver()
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
        bluetoothController.onDisconnected()
        //val intent = Intent(this, MainActivity::class.java)
        //startActivity(intent)
        character_image.onStop()
        navigationController.onStop()
        //CharacterHolder.clearActiveCharacter()
        //CharacterHolder.clearParty()
        //CharacterHolder.clearAllImages()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onStop() {
        savingController.quickSave()
        screenSaverController.stopTimer()
        println("on stop save")
        lightSaberTurnOn = true
        CharacterImageController.turnOffLightSaber(character_image)
        lightSaberSoundController.stopSound()

        super.onStop()
    }

    //endregion
    //************************************************************************************************************
    //region Updating
    //************************************************************************************************************

    fun updateNetwork(updateImages: Boolean){ bluetoothController.sendCharacter(updateImages); }
    fun updateStats(){ StatController.Companion.update(character,health,endurance,speed, arrayOf
        (strength1,
        strength2,strength3), arrayOf(insight1,insight2,insight3), arrayOf(tech1,tech2,tech3),
        resources)
        updateNetwork(false)}
    fun updateImages() { CharacterImageController.update(character,character_image,
        companion_image,this)
        updateNetwork(true) }
    fun updateConditions(){ conditionsController.update(); updateNetwork(false)}

    private var loadAnimated = false
    private var lightSaberTurnOn = true

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus && !loadAnimated) {
            startAnimation()
        }
        else{
            savingController.quickSave()
        }


        updateStats()
        updateConditions()
        updateActionUsage()
        updateImages()

        if(character.layerLightSaber!=null){
            lightSaberSoundController.startSound()

        }
        CharacterImageController.turnOnLightSaber(character_image, 300)


    }
    private fun updateActionUsage(){
        if (character.actionUsageSetting) {
            if (character.isActivated && character.actionsLeft > 0) {
                action1.alpha = 0f
            }
            else{
                action1.alpha = 1f
            }
            if (character.isActivated && character.actionsLeft > 1) {
                action2.alpha = 0f
            }
            else{
                action2.alpha = 1f
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
        lightSaberTurnOn = true
        if(character.isActivated){actionController.onActivate()}
        CharacterImageController.turnOffLightSaber(character_image)
        CharacterImageController.turnOnLightSaber(character_image, 500)

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

        party_buttons.animate().alpha(1f)
        val partyAnimButtons = ObjectAnimator.ofFloat(
            party_buttons, "translationX", -left_buttons.width
                .toFloat(), 0f
        )
        partyAnimButtons.duration = (500).toLong()
        partyAnimButtons.start()
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
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        System.out.println("Touch")
        screenSaverController.onTouchEvent()
        return super.dispatchTouchEvent(ev)
    }

    fun setScreenSaverTime(minutes: Int) {
        var time = minutes*60
        if(minutes>20){
            time = -1
        }

        screenSaverController.turnOnTime = time
    }
    fun getScreenSaverTime():Int{
        return  screenSaverController.turnOnTime /60
    }

}





