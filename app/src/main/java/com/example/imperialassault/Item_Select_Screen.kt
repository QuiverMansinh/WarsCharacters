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
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_item__select__screen.*
import kotlinx.android.synthetic.main.dialog_show_card.*
import kotlinx.android.synthetic.main.list_item.view.*

class Item_Select_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item__select__screen)

        val adapter = MyAdapter(this, supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        when (intent.getStringExtra("tab")) {
            "range" -> {
                pager.setCurrentItem(4)
            }
            "melee" -> {
                pager.setCurrentItem(3)
            }
            "accessory" -> {
                pager.setCurrentItem(2)
            }
            "armour" -> {
                pager.setCurrentItem(1)
            }
            "reward" -> {
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
        super.onBackPressed()
        finish()
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
                Rewards()
            }
            1 -> {
                Armor()
            }
            2 -> {
                Accessories()
            }
            3 -> {
                Melee()
            }
            4 -> {
                Ranged()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}

class Rewards : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.item_fragment, container, false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, items.RewardsArray)
        return rewardsView
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
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, items.accArray)
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
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, items.armorArray)
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
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, items.meleeArray)
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
        rewardsgrid.adapter = ImageAdapter(this.context as Activity, items.rangedArray)
        return rewardsView
    }
}

class ImageAdapter internal constructor(
    val mContext: Activity, var itemArray:
    ArrayList<ArrayList<Int>>
) :
    BaseAdapter
        () {

    // References to our images

    override fun getCount(): Int {
        return itemArray[0].size
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

        if (itemArray[0][position] > 0) {
            gridItem = mContext.layoutInflater.inflate(R.layout.list_item, null, true)
            gridItem.item.setImageResource(itemArray[0][position])
            if (itemArray[1][position] == 1) {
                gridItem.item.alpha = 1f
            } else if (itemArray[1][position] == 0) {
                gridItem.item.alpha = 0.5f
            }
            gridItem.setOnLongClickListener {
                onShowCard(gridItem.item)
                true
            }
            gridItem.setOnClickListener {
                if (itemArray[1][position] == 0) {
                    System.out.println("SIZE SIZE SIZE " + itemArray[1][position])
                    if(itemArray[2][0] != 0){
                        gridItem.item.animate().alpha(1f).duration = 50
                        itemArray[1][position] = 1
                        itemArray[2][0]--
                    }
                } else if (itemArray[1][position] == 1) {
                    System.out.println("It Works " + itemArray[1][position])
                    gridItem.item.animate().alpha(0.5f).duration = 50
                    itemArray[1][position] = 0
                    itemArray[2][0]++
                }
            }
        } else if (itemArray[0][position] == -1) {
            gridItem = mContext.layoutInflater.inflate(R.layout.tier1_title, null, true)
        } else if (itemArray[0][position] == -2) {
            gridItem = mContext.layoutInflater.inflate(R.layout.tier2_title, null, true)
        } else if (itemArray[0][position] == -3) {
            gridItem = mContext.layoutInflater.inflate(R.layout.tier3_title, null, true)
        } else if (itemArray[0][position] == -4) {
            gridItem = mContext.layoutInflater.inflate(R.layout.mods_divider, null, true)
        } else if (itemArray[0][position] == -5) {
            gridItem = mContext.layoutInflater.inflate(R.layout.empty_item, null, true)
        } else {
            gridItem = mContext.layoutInflater.inflate(R.layout.empty_space, null, true)
        }
        return gridItem
    }
}



var showCardDialog: Dialog? = null

fun onShowCard(view: ImageView) {
    var image = ((view).drawable as BitmapDrawable).bitmap
    showCardDialog!!.card_image.setImageBitmap(image)
    showCardDialog!!.show()
}