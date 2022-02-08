package com.glasswellapps.iact.character

import android.R
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.inventory.Items
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlinx.android.synthetic.main.dialog_quick_view.*
import kotlinx.android.synthetic.main.dialog_quick_view_button.*

class QuickView (val characterScreen: CharacterScreen){
    lateinit var quickViewDialog: Dialog
    lateinit var quickViewButtonDialog: Dialog
    private var character = characterScreen.character

    init{
        quickViewDialog = Dialog(characterScreen, R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        quickViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        quickViewDialog.setCancelable(false)
        quickViewDialog.setContentView(com.glasswellapps.iact.R.layout.dialog_quick_view)
        quickViewDialog.setCanceledOnTouchOutside(true)
        quickViewDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        characterScreen.character_images.setOnLongClickListener {
            quickViewButtonDialog.show()
            true
        }
        quickViewDialog.quick_view_back.setOnClickListener {
            quickViewDialog.dismiss()
        }

        quickViewButtonDialog = Dialog(characterScreen)
        quickViewButtonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        quickViewButtonDialog.setCancelable(false)
        quickViewButtonDialog.setContentView(com.glasswellapps.iact.R.layout.dialog_quick_view_button)
        quickViewButtonDialog.setCanceledOnTouchOutside(true)
        quickViewDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        quickViewDialog.quick_view_weapon.setOnClickListener { characterScreen.onShowItemCard(quickViewDialog.quick_view_weapon) }
        quickViewDialog.quick_view_weapon1.setOnClickListener { characterScreen.onShowItemCard(quickViewDialog.quick_view_weapon1) }
        quickViewDialog.quick_view_armor.setOnClickListener { characterScreen.onShowItemCard(quickViewDialog.quick_view_armor) }
        quickViewDialog.quick_view_acc.setOnClickListener { characterScreen.onShowItemCard(quickViewDialog.quick_view_acc) }
        quickViewDialog.quick_view_acc1.setOnClickListener { characterScreen.onShowItemCard(quickViewDialog.quick_view_acc1) }
        quickViewDialog.quick_view_acc2.setOnClickListener { characterScreen.onShowItemCard(quickViewDialog.quick_view_acc2) }


        var mods = quickViewDialog.quickview_mods
        var mods1 = quickViewDialog.quickview_mods1

        mods.setOnClickListener {
            Sounds.selectSound()
            quickViewDialog.show_mods.visibility = View.VISIBLE
        }
        mods1.setOnClickListener {
            Sounds.selectSound()
            quickViewDialog.show_mods.visibility = View.VISIBLE
        }
        quickViewDialog.show_mods.setOnClickListener{
            quickViewDialog.show_mods.visibility = View.INVISIBLE
        }

        var xp = quickViewDialog.quickview_xp
        xp.setOnClickListener {
            characterScreen.onXPScreen(xp)
        }

        val reward = quickViewDialog.quickview_reward
        reward.setOnClickListener {
            characterScreen.onReward(reward)
        }

        quickViewButtonDialog.quick_view_button.setOnClickListener {
            Sounds.selectSound()
            val weaponIndex = character.weapons.getOrElse(0) { -1 }
            val weaponIndex1 = character.weapons.getOrElse(1) { -1 }
            /*
            if (weaponIndex < 0) {
                mods.alpha = 0f

            }*/
            //if (weaponIndex1 < 0) {
            mods1.alpha = 0f
            //}


            var imageId = com.glasswellapps.iact.R.drawable.empty_item_slot
            quickViewDialog.weapon_type.visibility = View.VISIBLE

            if(weaponIndex>0) {
                quickViewDialog.weapon_type.visibility = View.GONE
                if (  weaponIndex == 27) {
                    quickViewDialog.quick_view_weapon.setImageBitmap(character.startingMeleeWeapon)

                }
                else if(weaponIndex == 52){
                    quickViewDialog.quick_view_weapon.setImageBitmap(character.startingRangedWeapon)

                }
                else {
                    imageId = Items.itemsArray!![weaponIndex].resourceId
                    quickViewDialog.quick_view_weapon.setImageResource(imageId)
                }

                if (Items.itemsArray!![weaponIndex].type == Items.ranged) {
                    quickViewDialog.weapon_type.setImageResource(com.glasswellapps.iact.R.drawable.item_ranged)
                } else {
                    quickViewDialog.weapon_type.setImageResource(com.glasswellapps.iact.R.drawable.item_melee)
                }
            }
            else{
                quickViewDialog.quick_view_weapon1.setImageResource(imageId)
            }

            imageId = com.glasswellapps.iact.R.drawable.empty_item_slot
            quickViewDialog.weapon1_type.visibility = View.VISIBLE
            if(weaponIndex1>0){
                quickViewDialog.weapon1_type.visibility = View.GONE
                if (  weaponIndex1 == 27) {
                    quickViewDialog.quick_view_weapon1.setImageBitmap(character.startingMeleeWeapon)

                }
                else if(weaponIndex1 == 52){
                    quickViewDialog.quick_view_weapon1.setImageBitmap(character.startingRangedWeapon)

                }
                else{
                    imageId = Items.itemsArray!![weaponIndex1].resourceId
                    quickViewDialog.quick_view_weapon1.setImageResource(imageId)
                }

                if(Items.itemsArray!![weaponIndex1].type == Items.ranged){
                    quickViewDialog.weapon1_type.setImageResource(com.glasswellapps.iact.R.drawable.item_ranged)
                }
                else {
                    quickViewDialog.weapon1_type.setImageResource(com.glasswellapps.iact.R.drawable.item_melee)
                }
            }
            else{
                quickViewDialog.quick_view_weapon1.setImageResource(imageId)
            }


            imageId = com.glasswellapps.iact.R.drawable.empty_item_slot
            quickViewDialog.armor_type.visibility = View.VISIBLE
            val armorIndex = character.armor.getOrElse(0) { -1 }
            if (armorIndex >= 0) {
                imageId = Items.itemsArray!![armorIndex].resourceId
                quickViewDialog.armor_type.visibility = View.GONE
            }
            quickViewDialog.quick_view_armor.setImageResource(imageId)


            imageId = com.glasswellapps.iact.R.drawable.empty_item_slot
            quickViewDialog.acc_type.visibility = View.VISIBLE
            val accIndex = character.accessories.getOrElse(0) { -1 }
            if (accIndex >= 0) {
                imageId = Items.itemsArray!!.get(accIndex).resourceId
                quickViewDialog.acc_type.visibility = View.GONE
            }
            quickViewDialog.quick_view_acc.setImageResource(imageId)


            imageId = com.glasswellapps.iact.R.drawable.empty_item_slot
            quickViewDialog.acc1_type.visibility = View.VISIBLE
            val accIndex1 = character.accessories.getOrElse(1) { -1 }
            if (accIndex1 >= 0) {
                imageId = Items.itemsArray!![accIndex1].resourceId
                quickViewDialog.quick_view_acc1.setImageResource(imageId)
                quickViewDialog.acc1_type.visibility = View.GONE
            }
            quickViewDialog.quick_view_acc1.setImageResource(imageId)

            imageId = com.glasswellapps.iact.R.drawable.empty_item_slot
            quickViewDialog.acc2_type.visibility = View.VISIBLE
            var accIndex2 = character.accessories.getOrElse(2) { -1 }
            if (accIndex2 >= 0) {
                imageId = Items.itemsArray!![accIndex2].resourceId
                quickViewDialog.acc2_type.visibility = View.GONE
            }
            quickViewDialog.quick_view_acc2.setImageResource(imageId)
            quickViewDialog.mods_grid_view.adapter = ModListAdapter(characterScreen,character.mods)
            quickViewDialog.show()
            quickViewButtonDialog.dismiss()
        }
    }
}