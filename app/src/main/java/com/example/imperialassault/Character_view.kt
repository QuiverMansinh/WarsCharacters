package com.example.imperialassault

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat

import kotlinx.android.synthetic.main.activity_character__view.*
import kotlinx.android.synthetic.main.dialog_action_menu.*
import kotlinx.android.synthetic.main.dialog_conditions.*
import kotlinx.android.synthetic.main.dialog_show_card.*
import kotlinx.android.synthetic.main.screen_item_select.*
import java.io.InputStream


class Character_view : AppCompatActivity() {
    var character:Character = Character();
    var blastAnim:AnimationDrawable = AnimationDrawable()
    var impactAnim :AnimationDrawable= AnimationDrawable()
    var restAnim:AnimationDrawable= AnimationDrawable()
    var sliceAnim:AnimationDrawable= AnimationDrawable()

    var hidden = 0
    var focused = 1
    var weakened = 2
    var bleeding = 3
    var stunned = 4


    var conditionViews = ArrayList<ImageView>()
    var conditionsActive = booleanArrayOf(false,false,false,false,false)
    var conditionDrawable = intArrayOf(R.drawable.condition_hidden, R.drawable.condition_focused, R.drawable.condition_weakened, R.drawable.condition_bleeding, R.drawable.condition_stunned)

    var restDialog:Dialog? = null
    var unwoundDialog:Dialog? = null
    var conditionsDialog:Dialog? = null
    var settingsDialog:Dialog? = null
    var actionDialog:Dialog? = null
    var showCardDialog:Dialog? = null
    var endActivationDialog:Dialog? = null
    var itemSelectScreen:Dialog?=null

    var actionsLeft = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character__view)

        var load = false //intent.getBooleanExtra("Load",false)
        var characterName: String = intent.getStringExtra("CharacterName").toString()

        if (!load) {
            when (characterName) {
                "mak" -> {
                    character = Character_mak(this)
                }

            }


        } else {

        }
        name.setText("" + character.name);
        health.setText("" + character.health);
        endurance.setText("" + character.endurance);
        speed.setText("" + character.speed);

        when (character.defence_dice) {
            "white" -> ImageViewCompat.setImageTintList(defence, ColorStateList.valueOf(Color.WHITE))
            "black" -> ImageViewCompat.setImageTintList(defence, ColorStateList.valueOf(Color.BLACK))
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

        character_image.setImageBitmap(character.getCharacterImage())
        background_image.setImageBitmap(character.getBackgroundImage(this))

        blastAnim = createAnimation("blast")
        impactAnim = createAnimation("impact")
        sliceAnim = createAnimation("slice")
        restAnim = createAnimation("rest")

        blast_animation.setBackgroundDrawable(blastAnim)
        blast_animation.visibility = FrameLayout.INVISIBLE
        slice_animation.setBackgroundDrawable(sliceAnim)
        slice_animation.visibility = FrameLayout.INVISIBLE
        impact_animation.setBackgroundDrawable(impactAnim)
        impact_animation.visibility = FrameLayout.INVISIBLE
        rest_animation.setBackgroundDrawable(restAnim)
        rest_animation.visibility = FrameLayout.INVISIBLE



        add_damage.setOnLongClickListener {
            if (character.damage >= character.health) {
                unwoundDialog!!.show()
            }
            true
        }
        add_strain.setOnLongClickListener {
            if(actionsLeft>0) {
                restDialog!!.show()
            }
            else{
                showNoActionsLeftToast()
            }
            true
        }




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
            actionCompleted()
            true
        }
        focused_all.setOnLongClickListener {
            removeCondition(focused)
            actionCompleted()
            true
        }
        weakened_all.setOnLongClickListener {
            removeCondition(weakened)
            actionCompleted()
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


        restDialog = Dialog(this)
        restDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        restDialog!!.setCancelable(false)
        restDialog!!.setContentView(R.layout.dialog_rest)
        restDialog!!.setCanceledOnTouchOutside(true)
        restDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        unwoundDialog = Dialog(this)
        unwoundDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        unwoundDialog!!.setCancelable(false)
        unwoundDialog!!.setContentView(R.layout.dialog_unwound)
        unwoundDialog!!.setCanceledOnTouchOutside(true)
        unwoundDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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
        conditionsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        settingsDialog = Dialog(this)
        settingsDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        settingsDialog!!.setCancelable(false)
        settingsDialog!!.setContentView(R.layout.dialog_settings_menu)
        settingsDialog!!.setCanceledOnTouchOutside(true)
        settingsDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        actionDialog = Dialog(this)
        actionDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        actionDialog!!.setCancelable(false)
        actionDialog!!.setContentView(R.layout.dialog_action_menu)
        actionDialog!!.setCanceledOnTouchOutside(true)
        actionDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        showCardDialog = Dialog(this)
        showCardDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        showCardDialog!!.setCancelable(false)
        showCardDialog!!.setContentView(R.layout.dialog_show_card)
        showCardDialog!!.setCanceledOnTouchOutside(true)
        showCardDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        endActivationDialog = Dialog(this)
        endActivationDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        endActivationDialog!!.setCancelable(false)
        endActivationDialog!!.setContentView(R.layout.dialog_end_activation)
        endActivationDialog!!.setCanceledOnTouchOutside(true)
        endActivationDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        itemSelectScreen = Dialog(this)
        itemSelectScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        itemSelectScreen!!.setCancelable(false)
        itemSelectScreen!!.setContentView(R.layout.screen_item_select)
        itemSelectScreen!!.setCanceledOnTouchOutside(true)


    }


    fun setDiceColor(dice: ImageView, color: Char) {
        when(color){
            'B' -> ImageViewCompat.setImageTintList(dice, ColorStateList.valueOf(getResources().getColor(R.color.dice_blue, null)))
            'G' -> ImageViewCompat.setImageTintList(dice, ColorStateList.valueOf(getResources().getColor(R.color.dice_green, null)))
            'Y' -> ImageViewCompat.setImageTintList(dice, ColorStateList.valueOf(getResources().getColor(R.color.dice_yellow, null)))
            ' ' -> dice.visibility = ImageView.GONE
        }
    }

    fun playDamageAnim(){
        val animType = Math.random();
        if(animType<0.3){
            blast_animation.visibility = FrameLayout.VISIBLE
            blastAnim.setVisible(true, true)
            blastAnim.start()
        }
        else if(animType<0.6){
            slice_animation.visibility = FrameLayout.VISIBLE
            sliceAnim.setVisible(true, true)
            sliceAnim.start()
        }
        else{
            impact_animation.visibility = FrameLayout.VISIBLE
            impactAnim.setVisible(true, true)
            impactAnim.start()
        }
    }
    fun playRestAnim(){
        rest_animation.visibility = FrameLayout.VISIBLE
        restAnim.setVisible(true, true)
        restAnim.start()
    }


    fun createAnimation(type: String): AnimationDrawable{

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels.toFloat()
        val width = displayMetrics.widthPixels.toFloat()

        val animation = AnimationDrawable()


        for(i in 0..9){
            var bitmap = getBitmap(this, "animations/" + type + "/" + type + "_" + +i + ".png")


            if (bitmap != null) {
                val bitmapWidth = width/(height-128)*bitmap.width
                val bitmapOffset =((bitmap.width-bitmapWidth)/2).toInt()
                println(bitmapWidth)
                bitmap = Bitmap.createBitmap(bitmap, bitmapOffset, 0, bitmapWidth.toInt(), bitmap.height)
                val frame = BitmapDrawable(resources, bitmap);
                animation.addFrame(frame, 60)
            }
        }
        animation.setOneShot(true);


        return animation;
    }

    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open(path)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun onAddStrain(view: View) {
        if(character.strain < character.endurance) {
            character.strain++
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

    }
    fun onAddDamage(view: View) {
        if(addDamage()) {
            playDamageAnim()
        }
    }

    fun addDamage():Boolean{
        if(character.damage < character.health*2) {
            character.damage++
            if(character.damage < character.health) {

                add_damage.setText("" + character.damage)
            }
            else if(character.damage < character.health*2){

                character.wounded = character.damage-character.health
                add_damage.setText("" + character.wounded)
                wounded.visibility = ImageView.VISIBLE
            }
            else{
                add_damage.setText("" + character.health)
                val slide = ObjectAnimator.ofFloat(character_image, "translationY",0f,0f,character_image.height.toFloat())
                slide.setDuration(1500)
                slide.start()
            }
            return true
        }
        return false
    }

    fun onMinusDamage(view: View) {
        if(character.damage >0) {
            character.damage--
            if(character.damage < character.health) {
                add_damage.setText("" + character.damage)
                wounded.visibility = ImageView.INVISIBLE
                character.wounded = 0;
            }
            else if(character.damage < character.health*2){
                character.wounded = character.damage-character.health
                add_damage.setText("" + character.wounded)
                val slide = ObjectAnimator.ofFloat(character_image, "translationY", 0f)
                slide.setDuration(500)
                slide.start()
            }

        }



    }

    fun onUnwound(view: View) {
            playRestAnim()
            character.damage = 0
            character.wounded = 0
            add_damage.setText("" + character.damage)
            wounded.visibility = ImageView.INVISIBLE
            unwoundDialog!!.cancel()
    }


    fun onAddCondition(view: View) {
        conditionsDialog!!.show()

    }

    fun onHide(view: View) {
        view.visibility = View.INVISIBLE
    }



    fun onWeakened(view: View) {
        conditionsActive[weakened] =!conditionsActive[weakened]
        updateConditionIcons()
    }
    fun onBleeding(view: View) {
        conditionsActive[bleeding] =!conditionsActive[bleeding]
        updateConditionIcons()
    }
    fun onStunned(view: View) {
        conditionsActive[stunned] =!conditionsActive[stunned]
        updateConditionIcons()
    }
    fun onHidden(view: View) {
        conditionsActive[hidden] =!conditionsActive[hidden]
        updateConditionIcons()
    }
    fun onFocused(view: View) {
        conditionsActive[focused] =!conditionsActive[focused]
        updateConditionIcons()
    }


    fun removeCondition(conditionType : Int){

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
            show_conditions.visibility = View.INVISIBLE
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

    fun onActivate(view: View) {
        if(unactive.visibility == View.VISIBLE){
            unactive.visibility = View.INVISIBLE

            action1.visibility = View.VISIBLE
            action_button1.visibility = View.VISIBLE
            action2.visibility = View.VISIBLE
            action_button2.visibility = View.VISIBLE
            actionsLeft = 2;
        }
        else{
            endActivationDialog!!.show()
        }
    }

    fun onAction(view: View) {

        actionDialog!!.cancel()

        if(actionsLeft>0) {
            //todo add focus symbol to attack
            if (conditionsActive[focused]) {
                actionDialog!!.attack_focused.visibility = View.VISIBLE
            } else {
                actionDialog!!.attack_focused.visibility = View.GONE
            }

            if (conditionsActive[hidden]) {
                actionDialog!!.attack_hidden.visibility = View.VISIBLE
            } else {
                actionDialog!!.attack_hidden.visibility = View.GONE
            }

            //todo add stun symbol and deactivate to move, special and attack
            if (conditionsActive[stunned]) {
                actionDialog!!.action_stunned_attack.visibility = View.VISIBLE
                actionDialog!!.action_stunned_move.visibility = View.VISIBLE
                actionDialog!!.action_stunned_special.visibility = View.VISIBLE
                actionDialog!!.action_remove_stun.visibility = View.VISIBLE
            } else {
                actionDialog!!.action_stunned_attack.visibility = View.INVISIBLE
                actionDialog!!.action_stunned_move.visibility = View.INVISIBLE
                actionDialog!!.action_stunned_special.visibility = View.INVISIBLE
                actionDialog!!.action_remove_stun.visibility = View.GONE
            }

            if (conditionsActive[bleeding]) {
                actionDialog!!.action_remove_bleeding.visibility = View.VISIBLE
            } else {
                actionDialog!!.action_remove_bleeding.visibility = View.GONE
            }

            actionDialog!!.show()
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

            actionDialog!!.cancel()
            endActivationDialog!!.show()
        }
    }

    fun onAttack(view: View) {
        if(actionsLeft>0 ) {
            if(!conditionsActive[stunned]) {
                removeCondition(focused)
                removeCondition(hidden)
                actionCompleted()
                onAction(action1)
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
                onAction(action1)

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
                onAction(action1)

            }
        }
        else{
            showNoActionsLeftToast()
        }
    }
    fun onInteract(view: View) {
        if(actionsLeft>0) {
            actionCompleted()
            onAction(action1)
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
            onAction(action1)
            actionCompleted()
        }
        else{
            showNoActionsLeftToast()
        }
    }

    fun onRemoveBleeding(view: View) {
        if(actionsLeft>0) {
            removeCondition(bleeding)
            onAction(action1)
            actionCompleted()
        }
        else{
            showNoActionsLeftToast()
        }
    }


    fun onEndActivation(view:View){
        endActivationDialog!!.cancel()
        removeCondition(weakened)
        unactive.visibility = View.VISIBLE
        action_button1.visibility = View.INVISIBLE
        action_button2.visibility = View.INVISIBLE
    }

    fun onEndActivationNo(view:View){
        endActivationDialog!!.cancel()
    }

    fun onShowFocusedCard(view:View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_focused))
        showCardDialog!!.show()
    }
    fun onShowStunnedCard(view:View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_stunned))
        showCardDialog!!.show()
    }
    fun onShowWeakenedCard(view:View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_weakened))
        showCardDialog!!.show()
    }
    fun onShowBleedingCard(view:View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_bleeding))
        showCardDialog!!.show()
    }
    fun onShowHiddenCard(view:View){
        showCardDialog!!.card_image.setImageDrawable(resources.getDrawable(R.drawable.card_hidden))
        showCardDialog!!.show()
    }

    fun onShowCard(view:View){
        when(view.getTag()){
            hidden->onShowHiddenCard(view)
            focused->onShowFocusedCard(view)
            stunned->onShowStunnedCard(view)
            bleeding->onShowHiddenCard(view)
            weakened->onShowWeakenedCard(view)
        }
    }

    fun showNoActionsLeftToast(){
        val noActionsLeftToast=Toast(this)
        noActionsLeftToast!!.duration = Toast.LENGTH_SHORT
        noActionsLeftToast!!.view = layoutInflater.inflate(R.layout.toast_no_actions_left,character_view_group, false)
        noActionsLeftToast!!.setGravity(Gravity.CENTER,0,0)
        noActionsLeftToast!!. show()
    }

    var sideMenuState = 0
    fun onExtendDown(view: View) {

        if(sideMenuState>-1){
            sideMenuState--
            kill_tracker_bar.animate().translationYBy(menu_bar.height.toFloat())
            menu_bar.animate().translationYBy(menu_bar.height.toFloat())
        }
        when(sideMenuState){
            -1->{extend_down_button.animate().alpha(0f);
                extend_up_button.animate().alpha(1f)}
            0->{extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(1f)}
            1->{extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(0f)}
        }


    }
    fun onExtendUp(view: View) {

        if(sideMenuState<1){
            sideMenuState++
            kill_tracker_bar.animate().translationYBy(-menu_bar.height.toFloat())
            menu_bar.animate().translationYBy(-menu_bar.height.toFloat())
        }
        when(sideMenuState){
            -1->{extend_down_button.animate().alpha(0f);
                extend_up_button.animate().alpha(1f)}
            0->{extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(1f)}
            1->{extend_down_button.animate().alpha(1f)
                extend_up_button.animate().alpha(0f)}
        }
    }

    fun onRewards(view: View) {
        itemSelectScreen!!.show()
    }
}

