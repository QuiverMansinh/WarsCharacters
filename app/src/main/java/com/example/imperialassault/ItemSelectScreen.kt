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
import kotlinx.android.synthetic.main.activity_item__select__screen.*
import kotlinx.android.synthetic.main.dialog_show_card.*
import kotlinx.android.synthetic.main.grid_item.view.*

class ItemSelectScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item__select__screen)

        val adapter = MyAdapter(this, supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        //defining Which tab is active

        when (intent.getStringExtra("tab")) {
            "range" -> {
                pager.setCurrentItem(3)
            }
            "melee" -> {
                pager.setCurrentItem(2)
            }
            "armour" -> {
                pager.setCurrentItem(1)
            }
            "accessory" -> {
                pager.setCurrentItem(0)
            }
        }

        //Dialog that shows up card while long-Pressed

        showCardDialog = Dialog(this)
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

//creating CUSTOM Adapter for cards view grid

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
                Accessories()
            }
            1 -> {
                Armor()
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

//Code bellow creates fragments for each section:Accessories,Armor,Melee,Ranged.

class Accessories : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.item_fragment, container, false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, Items.accArray!!)
        rewardsgrid.setFriction(ViewConfiguration.getScrollFriction() / 10)
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
        rewardsgrid.setFriction(ViewConfiguration.getScrollFriction() / 10)
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
        rewardsgrid.setFriction(ViewConfiguration.getScrollFriction() / 10)
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
        rewardsgrid.setFriction(ViewConfiguration.getScrollFriction() / 10)
        return rewardsView
    }
}

//Code bellow creates image adapter for inflating images and layout into gridview.

class ImageAdapter internal constructor(
    val context: Activity, var itemArray:
    Array<Item>
) :
    BaseAdapter() {

    var gridItems = arrayListOf<View>()

    init {

        for (i in 0..itemArray.size - 1) {
            var gridItem: View
            var currentItem = itemArray.get(i)

            if (currentItem.type >= 0) {
                gridItem = context.layoutInflater.inflate(R.layout.grid_item, null, true)
                gridItem.item.alpha = 0.5f
                var character = MainActivity.selectedCharacter!!

                if (i == 3 && currentItem.type == Items.melee && character.startingMeleeWeapon !=
                    null
                ) {
                    gridItem.item.setImageBitmap(character.startingMeleeWeapon)
                } else if (i == 3 && currentItem.type == Items.ranged && character
                        .startingRangedWeapon != null
                ) {
                    gridItem.item.setImageBitmap(character.startingRangedWeapon)
                } else {
                    gridItem.item.setImageResource(currentItem.resourceId)

                }

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
                        if (character.weapons.contains(currentItem.index) || character.mods.contains(
                                currentItem.index
                            )
                        ) {
                            gridItem.item.alpha = 1f
                        }
                    }
                    Items.ranged -> {
                        if (character.weapons.contains(currentItem.index) || character.mods.contains(
                                currentItem.index
                            )
                        ) {
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
                            println(currentItem.index)
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
                    if (gridItem.item.alpha == 0.5f) {
                        //TODO Rejection sounds
                    } else {
                        Sounds().weaponSound(context, currentItem.weaponType)
                    }
                }

            } else {
                gridItem = context.layoutInflater.inflate(currentItem.resourceId, null, true)
            }
            gridItems.add(gridItem)
        }
    }
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
        return gridItems.get(position)
    }


    fun equipReward(item: Item): Float {
        var character = MainActivity.selectedCharacter!!

        //remove if already equipped
        if (character.rewards.remove(item.index)) {
            when (item.index) {
                Items.adrenalImplantsIndex -> character.adenalImplants = false
                Items.bardottanShardIndex -> character.bardottanShard = false
                Items.quickDrawHolsterIndex -> character.quickDrawHolster = false
            }
            return 0.5f
        }

        //equip if not equipped

        if (item.index == Items.adrenalImplantsIndex) {
            if (character.accessories.size < getMaxAcc()) {
                character.rewards.add(item.index)
                character.adenalImplants = true
                return 1f
            } else {
                return 0.5f
            }
        }
        if (item.index == Items.bardottanShardIndex) {
            if (character.accessories.size < getMaxAcc()) {
                character.rewards.add(item.index)
                character.bardottanShard = true
                return 1f
            } else {
                return 0.5f
            }
        }

        if (item.index == Items.quickDrawHolsterIndex) {
            if (character.accessories.size < getMaxAcc()) {
                character.rewards.add(item.index)
                character.quickDrawHolster = true
                return 1f
            } else {
                return 0.5f
            }
        }

        character.rewards.add(item.index)
        return 1f
    }


    // 3 rewards count towards accessory limit (AdrenalImplants, QuickDrawHolster,
    // BardottanShard)
    fun getMaxAcc(): Int {
        var character = MainActivity.selectedCharacter!!
        var maxAcc = 3
        if (character.adenalImplants) {
            maxAcc--
        }
        if (character.bardottanShard) {
            maxAcc--
        }
        if (character.quickDrawHolster) {
            maxAcc--
        }
        return maxAcc
    }

    fun equipAcc(item: Item): Float {
        var character = MainActivity.selectedCharacter!!

        if (character.accessories.remove(item.index)) {
            if (item.subType == Items.helmet) {
                character.helmet = false
            }

            return 0.5f
        }
        //equip if slot available
        if (character.accessories.size < getMaxAcc()) {
            if (item.subType == Items.helmet) {
                if (!character.helmet) {
                    character.helmet = true
                    character.accessories.add(item.index)

                    return 1f
                }
            } else {
                character.accessories.add(item.index)
                return 1f
            }
        }

        //not equipped
        return 0.5f
    }


    fun equipArmor(item: Item): Float {
        var character = MainActivity.selectedCharacter!!

        //remove if already equipped
        if (character.armor.remove(item.index)) {
            return 0.5f
        }

        //equip if slot available
        if (character.armor.size < 1) {
            character.armor.add(item.index)
            return 1f
        }

        //not equipped
        return 0.5f
    }

    fun equipWeapon(item: Item): Float {
        var character = MainActivity.selectedCharacter!!

        if (item.subType == Items.mod) {
            //remove if already equipped
            if (character.mods.remove(item.index)) {
                return 0.5f
            }
            //equip if not equipped
            character.mods.add(item.index)
            return 1f
        } else {
            //remove if already equipped
            if (character.weapons.remove(item.index)) {
                return 0.5f
            }
            //equip if slot available
            if (character.weapons.size < 2) {
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
    println(image)
    showCardDialog!!.card_image.setImageBitmap(image)
    showCardDialog!!.show()
}

