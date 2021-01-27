package com.example.imperialassault

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_item__select__screen.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.rewards_fragment.*
import kotlinx.android.synthetic.main.rewards_fragment2.*

class Item_Select_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item__select__screen)

        val adapter = MyAdapter(this, supportFragmentManager,tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        when(intent.getStringExtra("tab")){
            "range" -> {pager.setCurrentItem(4)}
            "melee" -> {pager.setCurrentItem(3)}
            "accessory" -> {pager.setCurrentItem(2)}
            "armour" -> {pager.setCurrentItem(1)}
            "reward" -> {pager.setCurrentItem(0)}
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
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
            }1 -> {
                Armor()
            }2 -> {
                Drones()
            }3 -> {
                Melee()
            }4 -> {
                Ranged()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}

class Rewards constructor() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity)
        return rewardsView
    }
}

class Drones : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity)
        return rewardsView
    }
}

class Armor : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity)
        return rewardsView
    }
}

class Melee : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity)
        return rewardsView
    }
}

class Ranged : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity)
        return rewardsView
    }
}

class ImageAdapter internal constructor(val mContext: Activity) : BaseAdapter() {

    // References to our images
    val mThumbIds = arrayOf(
        R.drawable.reward1,
        R.drawable.reward2,
        R.drawable.reward3,
        R.drawable.reward4,
        R.drawable.reward5,
        R.drawable.reward6,
        R.drawable.reward7,
        R.drawable.reward8,
        R.drawable.reward9,
        R.drawable.reward10,
        R.drawable.reward11,
        R.drawable.reward12,
        R.drawable.reward13,
        R.drawable.reward14,
        R.drawable.reward15,
        R.drawable.reward16,
        R.drawable.reward17,
        R.drawable.reward18,
        R.drawable.reward19,
        R.drawable.reward20,
        R.drawable.reward21,
        R.drawable.reward22,
        R.drawable.reward23,
        R.drawable.reward24,
        R.drawable.reward25,
    )
    override fun getCount(): Int {
        return mThumbIds.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    // Create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var imageView: ImageView
        var gridItem: View

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            val inflater = mContext.layoutInflater
            gridItem = inflater.inflate(R.layout.list_item, null, true)
            gridItem.reward1.setImageResource(mThumbIds[position])
        } else {
            gridItem = (convertView as View?)!!
        }
        return gridItem
    }
}



