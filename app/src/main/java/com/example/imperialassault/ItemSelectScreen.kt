package com.example.imperialassault

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_character_screen.*

import kotlinx.android.synthetic.main.dialog_show_card.*
import kotlinx.android.synthetic.main.grid_item.view.*
import kotlinx.android.synthetic.main.toast_no_actions_left.view.*

class ItemSelectScreen {
}

//creating CUSTOM Adapter for cards view grid


//Code bellow creates image adapter for inflating images and layout into gridview.

class ImageAdapter internal constructor(
    val context: Activity, val itemArray:
    Array<Item>
) :
    BaseAdapter() {

    val gridItems = arrayListOf<View>()

    init {

        for (i in 0..itemArray.size - 1) {
            var gridItem: View
            var currentItem = itemArray.get(i)

            if (currentItem.type >= 0) {
                gridItem = context.layoutInflater.inflate(R.layout.grid_item, null, false)
                gridItem.grid_image.alpha = 0.5f
                var character = MainActivity.selectedCharacter!!

                if (currentItem.index == 27 ){
                    if(character.startingMeleeWeapon != null) {
                        gridItem.grid_image.setImageBitmap(character.startingMeleeWeapon)
                    }
                    else{
                        gridItem.grid_image.setImageResource(currentItem.resourceId)
                    }
                    setClickables(gridItem, currentItem)
                }
                else if (currentItem.index == 52) {
                    if (character.startingRangedWeapon != null) {
                        gridItem.grid_image.setImageBitmap(character.startingRangedWeapon)
                    }
                    else{
                        gridItem.grid_image.setImageResource(currentItem.resourceId)
                    }
                    setClickables(gridItem, currentItem)
                }
                else {
                    gridItem.grid_image.setImageResource(currentItem.resourceId)
                    setClickables(gridItem, currentItem)
                }

                when (currentItem.type) {
                    Items.reward -> {
                        if (character.rewards.contains(currentItem.index)) {
                            gridItem.grid_image.alpha = 1f
                        }
                    }
                    Items.acc -> {
                        if (character.accessories.contains(currentItem.index)) {
                            gridItem.grid_image.alpha = 1f
                        }
                    }
                    Items.armor -> {
                        if (character.armor.contains(currentItem.index)) {
                            gridItem.grid_image.alpha = 1f
                        }
                    }
                    Items.melee -> {
                        if (character.weapons.contains(currentItem.index)) {
                            gridItem.grid_image.alpha = 1f
                        }
                    }
                    Items.ranged -> {
                        if (character.weapons.contains(currentItem.index)) {
                            gridItem.grid_image.alpha = 1f
                        }
                    }
                    Items.mod -> {
                        if (character.mods.contains(currentItem.index)) {
                            gridItem.grid_image.alpha = 1f
                        }
                    }
                }
            } else {
                gridItem = context.layoutInflater.inflate(currentItem.resourceId, null, false)
            }
            gridItems.add(gridItem)
        }
    }
    // References to our images

    fun setClickables(gridItem:View, currentItem:Item) {
        gridItem.setOnLongClickListener {
            onShowCard(gridItem.grid_image)
            true
        }

        gridItem.setOnClickListener {
            if(gridItem.grid_image!=null) {
            when (currentItem.type) {
                Items.reward -> {
                    gridItem.grid_image.alpha = equipReward(currentItem)
                }
                Items.acc -> {
                    gridItem.grid_image.alpha = equipAcc(currentItem)
                }
                Items.armor -> {
                    gridItem.grid_image.alpha = equipArmor(currentItem)
                }
                Items.melee -> {
                    gridItem.grid_image.alpha = equipWeapon(currentItem)
                }
                Items.ranged -> {
                    gridItem.grid_image.alpha = equipWeapon(currentItem)
                }
                Items.mod -> {
                    gridItem.grid_image.alpha = equipMod(currentItem)

                }

            }

                if (gridItem.grid_image.alpha == 1f) {
                    Sounds.equipSound(context, currentItem.soundType)
                }
            }

        }
    }
    override fun getCount(): Int {
        return itemArray.size
    }

    override fun getItem(position: Int): Any? {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun isEnabled(position: Int): Boolean {
        return false
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }


    // Create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        setClickables(gridItems.get(position),itemArray.get(position))
        return gridItems.get(position)
    }


    fun equipReward(item: Item): Float {
        var character = MainActivity.selectedCharacter!!

        //remove if already equipped
        if (character.rewards.remove(item.index)) {
            Sounds.selectSound()
            return 0.5f
        }

        //equip if not equipped
        character.rewards.add(item.index)
        return 1f
    }

    fun equipAcc(item: Item): Float {
        var character = MainActivity.selectedCharacter!!
        println("")
        println("accessory equip " + character.accessories.size)
        println("")
        if (character.accessories.remove(item.index)) {
            if (item.index == Items.mandoHelmetIndex || item.index == Items.reinforcedHelmetIndex){
                character.helmet = false
            }
            Sounds.selectSound()

            //turn off
            when(item.index){

            }



            return 0.5f
        }
        //equip if slot available
        if (character.accessories.size < 3) {

            if (item.index == Items.mandoHelmetIndex || item.index == Items.reinforcedHelmetIndex) {
                if (!character.helmet) {
                    character.helmet = true
                    character.accessories.add(item.index)
                    if(character.name_short.equals("biv")) {
                        character.changeRandom = true
                    }
                    return 1f
                }
                else{
                    showItemLimitReached(-1)
                    return 0.5f
                }
            } else {
                character.accessories.add(item.index)
                when(item.index){

                }

                return 1f
            }
        }
        //TODO toast 3 accessory limit reached
        showItemLimitReached(item.type)

        //not equipped
        return 0.5f
    }


    fun equipArmor(item: Item): Float {
        var character = MainActivity.selectedCharacter!!

        //remove if already equipped
        if (character.armor.remove(item.index)) {
            Sounds.selectSound()
            return 0.5f
        }

        //equip if slot available
        if (character.armor.size < 1) {
            character.armor.add(item.index)
            return 1f
        }
        //TODO toast 1 armor limit reached
        showItemLimitReached(item.type)

        //not equipped
        return 0.5f
    }

    fun equipWeapon(item: Item): Float {
        var character = MainActivity.selectedCharacter!!
        //remove if already equipped
        if (character.weapons.remove(item.index)) {
            Sounds.selectSound()
            return 0.5f
        }
        //equip if slot available
        if (character.weapons.size < 2) {
            character.weapons.add(item.index)
            return 1f
        }

        //TODO toast 2 weapon limit reached
        showItemLimitReached(item.type)

        //not equipped
        return 0.5f
    }

    fun equipMod(item: Item): Float {
        var character = MainActivity.selectedCharacter!!
        //remove if already equipped
        if (character.mods.remove(item.index)) {
            Sounds.selectSound()
            return 0.5f
        }
        //equip if not equipped
        character.mods.add(item.index)
        return 1f
    }


    private fun showItemLimitReached(itemType : Int) {
        Sounds.negativeSound()
        val toast = Toast(context)
        toast!!.duration = Toast.LENGTH_SHORT
         val view = context.layoutInflater.inflate(
            R.layout.toast_no_actions_left,
            null,
            false
        )
        when(itemType){
            Items.melee -> view.toast_text.setText("2 weapon limit reached")
            Items.ranged -> view.toast_text.setText("2 weapon limit reached")
            Items.armor -> view.toast_text.setText("1 armor limit reached")
            Items.acc-> view.toast_text.setText("3 accessory limit reached")
            Items.reward-> view.toast_text.setText("3 accessory limit reached")
            -1 -> view.toast_text.setText("1 helmet limit reached")
        }

        toast!!.view = view
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        toast!!.show()
    }
}

var showCardDialog: Dialog? = null
fun onShowCard(view: ImageView) {
    var image = ((view).drawable as BitmapDrawable).bitmap
    println(image)
    showCardDialog!!.card_image.setImageBitmap(image)
    showCardDialog!!.show()
}

