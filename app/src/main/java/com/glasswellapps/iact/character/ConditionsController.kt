package com.glasswellapps.iact.character
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.core.view.isVisible
import com.glasswellapps.iact.R
import com.glasswellapps.iact.effects.GreenHighlight
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlinx.android.synthetic.main.dialog_conditions.*
import kotlinx.android.synthetic.main.dialog_show_condition_card.*

class ConditionsController (val characterScreen: CharacterScreen){
    var character = characterScreen.character
    var resources = characterScreen.resources

    var conditionViews = ArrayList<ImageView>()
    var conditionDrawable = intArrayOf(
        R.drawable.condition_hidden,
        R.drawable.condition_focused,
        R.drawable.condition_weakened,
        R.drawable.condition_bleeding,
        R.drawable.condition_stunned
    )
    var strengthGlow: GreenHighlight
    var techGlow: GreenHighlight
    var insightGlow: GreenHighlight
    lateinit var conditionsDialog: Dialog
    lateinit var showConditionCardDialog: Dialog

    init {
        strengthGlow = GreenHighlight(characterScreen.strength_icon, characterScreen, resources)
        techGlow = GreenHighlight(characterScreen.tech_icon, characterScreen, resources)
        insightGlow = GreenHighlight(characterScreen.insight_icon, characterScreen, resources)
        conditionViews.add(characterScreen.condition1)
        conditionViews.add(characterScreen.condition2)
        conditionViews.add(characterScreen.condition3)
        conditionViews.add(characterScreen.condition4)
        conditionViews.add(characterScreen.condition5)
        for (i in 0..conditionViews.size - 1) {
            conditionViews[i].setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View): Boolean {
                    removeCondition(v.tag as Int)
                    characterScreen.actionCompleted()
                    return true
                }
            }
            )
        }
        characterScreen.hidden_all.setOnLongClickListener {
            removeCondition(ConditionTypes.HIDDEN)
            true
        }
        characterScreen.focused_all.setOnLongClickListener {
            removeCondition(ConditionTypes.FOCUSED)

            true
        }
        characterScreen.weakened_all.setOnLongClickListener {
            removeCondition(ConditionTypes.WEAKENED)

            true
        }
        characterScreen.bleeding_all.setOnLongClickListener {
            removeCondition(ConditionTypes.BLEEDING)
            characterScreen.actionCompleted()
            true
        }
        characterScreen.stunned_all.setOnLongClickListener {
            removeCondition(ConditionTypes.STUNNED)
            characterScreen.actionCompleted()
            true
        }
        initConditionsDialog()
        initShowConditionCardDialog()

        characterScreen.add_condition.setOnClickListener { onAddCondition(characterScreen.add_condition) }
    }

    private fun initConditionsDialog(){
        conditionsDialog = Dialog(characterScreen)
        conditionsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        conditionsDialog.setCancelable(false)
        conditionsDialog.setContentView(R.layout.dialog_conditions)
        conditionsDialog.setCanceledOnTouchOutside(true)

        conditionsDialog.stunned_select.setOnClickListener{
            onStunned( conditionsDialog.stunned_select)
        }
        conditionsDialog.stunned_select.setOnLongClickListener {
            onShowCard(conditionsDialog.stunned_select)
            true
        }

        conditionsDialog.bleeding_select.setOnClickListener {
            onBleeding(conditionsDialog.bleeding_select)
        }
        conditionsDialog.bleeding_select.setOnLongClickListener {
            onShowCard(conditionsDialog.bleeding_select)
            true
        }

        conditionsDialog.weakened_select.setOnClickListener {
            onWeakened(conditionsDialog.weakened_select)
        }
        conditionsDialog.weakened_select.setOnLongClickListener {
            onShowCard(conditionsDialog.weakened_select)
            true
        }

        conditionsDialog.focused_select.setOnClickListener {
            onFocused(conditionsDialog.focused_select)
        }
        conditionsDialog.focused_select.setOnLongClickListener {
            onShowCard(conditionsDialog.focused_select)
            true
        }

        conditionsDialog.hidden_select.setOnClickListener {
            onHidden(conditionsDialog.hidden_select)
        }
        conditionsDialog.hidden_select.setOnLongClickListener {
            onShowCard(conditionsDialog.hidden_select)
            true
        }
        conditionsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initShowConditionCardDialog() {
        showConditionCardDialog = Dialog(characterScreen, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        showConditionCardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        showConditionCardDialog.setContentView(R.layout.dialog_show_condition_card)
        showConditionCardDialog.setCancelable(true)
        showConditionCardDialog.setCanceledOnTouchOutside(true)
        showConditionCardDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        showConditionCardDialog.show_condition_card_dialog.setOnClickListener {
            Sounds.selectSound()
            showConditionCardDialog.dismiss()
        }

        showConditionCardDialog.remove_button.setOnClickListener {
            var currentCondition = Integer.parseInt(showConditionCardDialog.remove_button.tag.toString())
            when (currentCondition){
                ConditionTypes.HIDDEN -> onHidden(showConditionCardDialog.condition_card_image)
                ConditionTypes.FOCUSED -> onFocused(showConditionCardDialog.condition_card_image)
                ConditionTypes.STUNNED -> onStunned(showConditionCardDialog.condition_card_image)
                ConditionTypes.BLEEDING-> onBleeding(showConditionCardDialog.condition_card_image)
                ConditionTypes.WEAKENED -> onWeakened(showConditionCardDialog.condition_card_image)
            }
            showConditionCardDialog.dismiss()
        }
    }

    fun onShowCard(view: View) {
        var currentCondition = Integer.parseInt(view.tag.toString())
        when (currentCondition) {
            ConditionTypes.HIDDEN -> onShowHiddenCard(view)
            ConditionTypes.FOCUSED -> onShowFocusedCard(view)
            ConditionTypes.STUNNED -> onShowStunnedCard(view)
            ConditionTypes.BLEEDING -> onShowBleedingCard(view)
            ConditionTypes.WEAKENED -> onShowWeakenedCard(view)
        }
        showConditionCardDialog!!.remove_button.tag = currentCondition
        if(!characterScreen.action_menu.isVisible) {
            showConditionCardDialog!!.remove_button.visibility = View.VISIBLE
        }
        else{
            showConditionCardDialog!!.remove_button.visibility = View.GONE
        }
        if(character.conditionsActive[currentCondition]) {
            showConditionCardDialog!!.remove_button_text.text = "REMOVE"
        }
        else{
            showConditionCardDialog!!.remove_button_text.text = "APPLY"
        }
    }
    fun onShowFocusedCard(view: View) {
        showConditionCardDialog!!.condition_card_image.setImageDrawable(resources.getDrawable(R.drawable.card_focused))
        showConditionCardDialog!!.remove_button.visibility = View.GONE
        showConditionCardDialog!!.show()
    }

    fun onShowStunnedCard(view: View) {
        showConditionCardDialog!!.condition_card_image.setImageDrawable(resources.getDrawable(R.drawable.card_stunned))
        showConditionCardDialog!!.remove_button.visibility = View.GONE
        showConditionCardDialog!!.show()
    }

    fun onShowWeakenedCard(view: View) {
        showConditionCardDialog!!.condition_card_image.setImageDrawable(resources.getDrawable(R.drawable.card_weakened))
        showConditionCardDialog!!.remove_button.visibility = View.GONE
        showConditionCardDialog!!.show()
    }

    fun onShowBleedingCard(view: View) {
        showConditionCardDialog!!.condition_card_image.setImageDrawable(resources.getDrawable(R.drawable.card_bleeding))
        showConditionCardDialog!!.remove_button.visibility = View.GONE
        showConditionCardDialog!!.show()
    }

    fun onShowHiddenCard(view: View) {
        showConditionCardDialog!!.condition_card_image.setImageDrawable(resources.getDrawable(R.drawable.card_hidden))
        showConditionCardDialog!!.remove_button.visibility = View.GONE
        showConditionCardDialog!!.show()
    }

    fun onAddCondition(view: View) {
        Sounds.selectSound()
        conditionsDialog!!.show()
    }

    fun onWeakened(view: View) {
        character.conditionsActive[ConditionTypes.WEAKENED] = !character
            .conditionsActive[ConditionTypes.WEAKENED]
        if (character.conditionsActive[ConditionTypes.WEAKENED]) {
            character.timesWeakened++
            Sounds.conditionSound(ConditionTypes.WEAKENED)
        } else {
            //character.timesWeakened--
            Sounds.selectSound()
        }
        updateConditionIcons()
    }

    fun onBleeding(view: View) {
        character.conditionsActive[ConditionTypes.BLEEDING] = !character.conditionsActive[ConditionTypes.BLEEDING]
        if (character.conditionsActive[ConditionTypes.BLEEDING]) {
            character.timesBleeding++
            Sounds.conditionSound(ConditionTypes.BLEEDING)
        } else {
            //character.timesBleeding--
            Sounds.selectSound()
        }
        updateConditionIcons()
    }

    fun onStunned(view: View) {

        character.conditionsActive[ConditionTypes.STUNNED] = !character.conditionsActive[ConditionTypes.STUNNED]
        if (character.conditionsActive[ConditionTypes.STUNNED]) {
            character.timesStunned++
            Sounds.conditionSound(ConditionTypes.STUNNED)
        } else {
            //character.timesStunned--
            Sounds.selectSound()
        }
        updateConditionIcons()
    }

    fun onHidden(view: View) {
        character.conditionsActive[ConditionTypes.HIDDEN] = !character.conditionsActive[ConditionTypes.HIDDEN]
        if (character.conditionsActive[ConditionTypes.HIDDEN]) {
            character.timesHidden++
            Sounds.conditionSound(ConditionTypes.HIDDEN)
        } else {
            //character.timesHidden--
            Sounds.selectSound()

        }
        updateConditionIcons()
    }

    fun onFocused(view: View) {
        character.conditionsActive[ConditionTypes.FOCUSED] = !character.conditionsActive[ConditionTypes.FOCUSED]
        if (character.conditionsActive[ConditionTypes.FOCUSED]) {
            character.timesFocused++
            Sounds.conditionSound(ConditionTypes.FOCUSED)
        } else {
            Sounds.selectSound()
        }
        updateConditionIcons()
    }

    fun removeCondition(conditionType: Int) {
        if(character.actionsLeft>0 || conditionType == ConditionTypes.HIDDEN || conditionType ==
            ConditionTypes
                .FOCUSED ||
            conditionType == ConditionTypes.WEAKENED ||
            !character.actionUsageSetting) {
            character.conditionsActive[conditionType] = false
            updateConditionIcons()
        }
    }

    open fun removeStunned(){ removeCondition(ConditionTypes.STUNNED) }
    open fun removeBleeding(){ removeCondition(ConditionTypes.BLEEDING) }
    open fun removeFocused(){ removeCondition(ConditionTypes.FOCUSED) }
    open fun removeHidden(){ removeCondition(ConditionTypes.HIDDEN) }
    open fun removeWeakened(){ removeCondition(ConditionTypes.WEAKENED) }

    open fun updateConditionIcons() {
        //TODO condition save
        characterScreen.character_image.animateConditions = character.conditionAnimSetting
        //quickSave()

        for (i in 0..conditionViews.size - 1) {
            conditionViews[i].visibility = View.GONE
        }
        characterScreen.show_all_conditions.visibility = View.GONE


        if (!character.conditionAnimSetting) {
            var active = 0
            for (i in 0..character.conditionsActive.size - 1) {
                if (character.conditionsActive[i]) {
                    active++
                }
            }
            characterScreen.conditions_row2.visibility = View.GONE

            if (active < 5) {
                characterScreen.show_conditions.visibility = View.VISIBLE
                characterScreen.show_all_conditions.visibility = View.INVISIBLE

                var conditionType = 0
                for (i in 0..active - 1) {
                    conditionViews[i].visibility = View.VISIBLE
                    for (j in conditionType..conditionDrawable.size - 1) {
                        if (character.conditionsActive[j]) {
                            conditionViews[i].setImageDrawable(
                                resources.getDrawable(
                                    conditionDrawable[conditionType]
                                )
                            )
                            conditionViews[i].tag = conditionType
                            conditionType = j + 1
                            break
                        }
                        conditionType = j + 1
                    }
                }
            } else {
                characterScreen.show_conditions.visibility = View.GONE
                characterScreen.show_all_conditions.visibility = View.VISIBLE
            }
            if (active > 2) {
                characterScreen.conditions_row2.visibility = View.VISIBLE
            } else {
                characterScreen.conditions_row2.visibility = View.GONE
            }
        }
        if (!character.conditionsActive[ConditionTypes.HIDDEN]) {
            conditionsDialog!!.hidden_select.alpha = 0.5f
            characterScreen.camouflage.visibility = View.GONE
        } else {
            conditionsDialog!!.hidden_select.alpha = 1f
            if (character.conditionAnimSetting) {
                characterScreen.camouflage.visibility = View.VISIBLE
            } else {
                characterScreen.camouflage.visibility = View.GONE
            }
        }
        if (!character.conditionsActive[ConditionTypes.FOCUSED]) {
            conditionsDialog!!.focused_select.alpha = 0.5f
            characterScreen.character_image.focused= false
            strengthGlow!!.disabled()
            techGlow!!.disabled()
            insightGlow!!.disabled()
        } else {
            conditionsDialog!!.focused_select.alpha = 1f
            characterScreen.character_image.focused = true
            strengthGlow!!.active()
            techGlow!!.active()
            insightGlow!!.active()
        }
        if (!character.conditionsActive[ConditionTypes.STUNNED]) {
            conditionsDialog!!.stunned_select.alpha = 0.5f
            characterScreen.character_image.stunned = false
        } else {
            conditionsDialog!!.stunned_select.alpha = 1f
            characterScreen.character_image.stunned = true
        }
        if (!character.conditionsActive[ConditionTypes.BLEEDING]) {
            conditionsDialog!!.bleeding_select.alpha = 0.5f
            characterScreen.character_image.bleeding = false
            characterScreen.bleeding_add_strain.visibility = View.INVISIBLE
        } else {
            conditionsDialog!!.bleeding_select.alpha = 1f
            characterScreen.character_image.bleeding= true
            characterScreen.bleeding_add_strain.visibility = View.VISIBLE
        }
        if (!character.conditionsActive[ConditionTypes.WEAKENED]) {
            conditionsDialog!!.weakened_select.alpha = 0.5f
            characterScreen.character_image.weakened = false
        } else {
            conditionsDialog!!.weakened_select.alpha = 1f
            characterScreen.character_image.weakened = true
        }
        characterScreen.updateImages()
    }
}