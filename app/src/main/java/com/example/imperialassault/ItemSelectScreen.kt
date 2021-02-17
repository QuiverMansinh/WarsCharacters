package com.example.imperialassault

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_item__select__screen.*
import kotlinx.android.synthetic.main.dialog_show_card.*
import kotlinx.android.synthetic.main.list_item.view.*

class ItemSelectScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item__select__screen)

        val adapter = MyAdapter(this, supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        when (intent.getStringExtra("tab")) {
            "range" -> {
                pager.setCurrentItem(3)
            }
            "melee" -> {
                pager.setCurrentItem(2)
            }
            "accessory" -> {
                pager.setCurrentItem(1)
            }
            "armour" -> {
                pager.setCurrentItem(0)
            }
        }

        showCardDialog = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        showCardDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        showCardDialog!!.setContentView(R.layout.dialog_show_card)
        showCardDialog!!.setCancelable(false)
        showCardDialog!!.setCanceledOnTouchOutside(true)
        showCardDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        showCardDialog!!.show_card_dialog.setOnClickListener {
            showCardDialog!!.cancel()
            true
        }

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    pager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}



@Suppress("DEPRECATION")
internal class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Armor()
            }
            1 -> {
                Accessories()
            }
            2 -> {
                Melee()
            }
            3 -> {
                Ranged()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}

class Accessories : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.item_fragment, container, false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, Items.accArray!!)
        return rewardsView
    }
}

class Armor : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.item_fragment, container, false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, Items.armorArray!!)
        return rewardsView
    }
}

class Melee : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.item_fragment, container, false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, Items.meleeArray!!)
        return rewardsView
    }
}

class Ranged : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.item_fragment, container, false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, Items.rangedArray!!)
        return rewardsView
    }
}

class ImageAdapter internal constructor(
    val mContext: Activity, var itemArray:
    Array<Item>
) :
    BaseAdapter() {

    // References to our images

    override fun getCount(): Int {
        return itemArray.size
    }

    override fun getItem(position: Int): Any? {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var gridItem: View
        var currentItem = itemArray.get(position)

        if (currentItem.type >= 0) {
            gridItem = mContext.layoutInflater.inflate(R.layout.list_item, null, true)
            gridItem.item.setImageResource(currentItem.resourceId)
            gridItem.item.alpha = 0.5f
            var character = MainActivity.selectedCharacter!!
            when (currentItem.type) {
                Items.reward -> {
                    if (character.rewards.contains(currentItem.index)) {
                        gridItem.item.alpha = 1f
                    }
                }
                Items.acc -> {
                    if (character.accessories.contains(currentItem.index)) {
                        gridItem.item.alpha = 1f
                    }
                }
                Items.armor -> {
                    if (character.armor.contains(currentItem.index)) {
                        gridItem.item.alpha = 1f
                    }
                }
                Items.melee -> {
                    if (character.weapons.contains(currentItem.index) || character.mods.contains(currentItem.index)) {
                        gridItem.item.alpha = 1f
                    }
                }
                Items.ranged -> {
                    if (character.weapons.contains(currentItem.index) || character.mods.contains(currentItem.index)) {
                        gridItem.item.alpha = 1f
                    }
                }
            }
            //TODO load eqipped items

            gridItem.setOnLongClickListener {
                onShowCard(gridItem.item)
                true
            }

            gridItem.setOnClickListener {
                when (currentItem.type) {
                    Items.reward -> {
                        gridItem.item.alpha = equipReward(currentItem)
                    }
                    Items.acc -> {
                        gridItem.item.alpha = equipAcc(currentItem)
                    }
                    Items.armor -> {
                        gridItem.item.alpha = equipArmor(currentItem)
                    }
                    Items.melee -> {
                        gridItem.item.alpha = equipWeapon(currentItem)
                    }
                    Items.ranged -> {
                        gridItem.item.alpha = equipWeapon(currentItem)
                    }
                }
            }
        } else {
            gridItem = mContext.layoutInflater.inflate(currentItem.resourceId, null, true)
        }

        return gridItem
    }


    fun equipReward(item:Item):Float{
        var character = MainActivity.selectedCharacter!!

        //remove if already equipped
        if(character.rewards.remove(item.index)){
            return 0.5f
        }

        //equip if not equipped
        character.rewards.add(item.index)
        return 1f
    }

    fun equipAcc(item:Item):Float{
        var character = MainActivity.selectedCharacter!!

        if(character.accessories.remove(item.index)){
            if(item.subType == Items.helmet){
                character.helmet = false
            }
            return 0.5f
        }

        //equip if slot available
        if(character.accessories.size < 3){
            if(item.subType == Items.helmet){
                if(!character.helmet) {
                    character.helmet = true
                    character.accessories.add(item.index)
                    return 1f
                }
            }
            else{
                character.accessories.add(item.index)
                return 1f
            }
        }

        //not equipped
        return 0.5f
    }

    fun equipArmor(item:Item):Float{
        var character = MainActivity.selectedCharacter!!

        //remove if already equipped
        if(character.armor.remove(item.index)){
            return 0.5f
        }

        //equip if slot available
        if(character.armor.size < 1){
            character.armor.add(item.index)
            return 1f
        }

        //not equipped
        return 0.5f
    }

    fun equipWeapon(item:Item):Float{
        var character = MainActivity.selectedCharacter!!

        if(item.subType == Items.mod){
            //remove if already equipped
            if(character.mods.remove(item.index)){
                return 0.5f
            }
            //equip if not equipped
            character.mods.add(item.index)
            return 1f
        }
        else {
            //remove if already equipped
            if(character.weapons.remove(item.index)){
                return 0.5f
            }
            //equip if slot available
            if(character.weapons.size < 2){
                character.weapons.add(item.index)
                return 1f
            }
        }

        //not equipped
        return 0.5f
    }
}

var showCardDialog: Dialog? = null
fun onShowCard(view: ImageView) {
    var image = ((view).drawable as BitmapDrawable).bitmap
    showCardDialog!!.card_image.setImageBitmap(image)
    showCardDialog!!.show()
}


/* if (itemArray[1][position] == 0) {
                    if ((itemType == "Ranged" || itemType == "Melee") && items.weaponsEquipped != 0){
                        items.weaponsEquipped--
                        gridItem.item.animate().alpha(1f).duration = 50
                        itemArray[1][position] = 1



                    }else if(itemType == "Accessories"){
                        if ((itemArray[0][position] == R.drawable.acc_t2_mandalorianhelmet &&
                            items.whichHelmet == 0) || (itemArray[0][position] == R.drawable
                                .acc_t3_reinforcedhelmet &&
                            items.whichHelmet == 0) && itemArray[2][0] != 0){
                                if (itemArray[0][position] == R.drawable.acc_t2_mandalorianhelmet){
                                    items.whichHelmet = 1
                                }else{
                                    items.whichHelmet = 2
                                }
                                System.out.println("Yes" + items.whichHelmet)
                            gridItem.item.animate().alpha(1f).duration = 50
                            itemArray[1][position] = 1
                            itemArray[2][0]--
                        }else if ((itemArray[0][position] != R.drawable.acc_t2_mandalorianhelmet)&&
                                (itemArray[0][position] != R.drawable
                                    .acc_t3_reinforcedhelmet)&&itemArray[2][0] != 0){
                                        System.out.println("nooooo")
                            gridItem.item.animate().alpha(1f).duration = 50
                            itemArray[1][position] = 1
                            itemArray[2][0]--
                        }
                    }else if(itemArray[2][0] != 0){
                        gridItem.item.animate().alpha(1f).duration = 50
                        itemArray[1][position] = 1
                        itemArray[2][0]--
                    }
                    Character().health+itemArray[3][position]
                } else if (itemArray[1][position] == 1) {
                    if ((itemType == "Ranged" || itemType == "Melee")){
                        items.weaponsEquipped++
                        itemArray[1][position] = 0
                        gridItem.item.animate().alpha(0.5f).duration = 50
                    }else if ((itemType == "Accessories")){
                        if (itemArray[0][position] == R.drawable.acc_t2_mandalorianhelmet || itemArray[0][position] == R.drawable
                                .acc_t3_reinforcedhelmet){
                            items.whichHelmet = 0
                            itemArray[2][0]++
                            itemArray[1][position] = 0
                            gridItem.item.animate().alpha(0.5f).duration = 50
                        }else{
                            itemArray[1][position] = 0
                            gridItem.item.animate().alpha(0.5f).duration = 50
                            itemArray[2][0]++
                        }
                    }else{
                        gridItem.item.animate().alpha(0.5f).duration = 50
                        itemArray[1][position] = 0
                        itemArray[2][0]++
                        Character().health-itemArray[3][position]
                    }
                }*/