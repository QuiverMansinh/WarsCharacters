package com.example.imperialassault

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_item__select__screen.*
import kotlinx.android.synthetic.main.list_item.view.*

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
        var rewardsPathIds = arrayListOf<Int>(
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
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity,rewardsPathIds)
        return rewardsView
    }
}

class Drones : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rewardsPathIds = arrayListOf<Int>(
            -6,
            -1,
            -6,
            R.drawable.acc_t1_bactapump,
            R.drawable.acc_t1_combatvembrace,
            R.drawable.acc_t1_emergancyinjector,
            R.drawable.acc_t1_portablemedkit,
            R.drawable.acc_t1_survivalgear,
            -5,
            -6,
            -2,
            -6,
            R.drawable.acc_t2_cyberneticarm,
            R.drawable.acc_t2_extraammunition,
            R.drawable.acc_t2_mandalorianhelmet,
            R.drawable.acc_t2_r5_astromech,
            R.drawable.acc_t2_slicingtools,
            -5,
            -6,
            -3,
            -6,
            R.drawable.acc_t3_combatknife,
            R.drawable.acc_t3_combatvisor,
            R.drawable.acc_t3_concussiongrenades,
            R.drawable.acc_t3_hiddenblade,
            R.drawable.acc_t3_personalshield,
            R.drawable.acc_t3_powercharger,
            R.drawable.acc_t3_reinforcedhelmet,
            R.drawable.acc_t3_supplypack,
            -5,
        )
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity,rewardsPathIds)
        return rewardsView
    }
}

class Armor : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rewardsPathIds = arrayListOf<Int>(
            -6,
            -1,
            -6,
            R.drawable.arm_t1_combatcoat,
            R.drawable.arm_t1_responsivearmor,
            R.drawable.arm_t1_shadowsilkcloak,
            -6,
            -2,
            -6,
            R.drawable.arm_t2_combatcoat,
            R.drawable.arm_t2_environmenthazardsuit,
            R.drawable.arm_t2_laminatearmor,
            -6,
            -3,
            -6,
            R.drawable.arm_t3_admiralsuniform,
            R.drawable.arm_t3_laminatearmor,
            R.drawable.arm_t3_plastoidarmor,
        )
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity,rewardsPathIds)
        return rewardsView
    }
}

class Melee : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rewardsPathIds = arrayListOf<Int>(
            -6,
            -1,
            -6,
            R.drawable.mel_t1_armoredgauntlets,
            R.drawable.mel_t1_gaffistick,
            R.drawable.mel_t1_punchdagger,
            R.drawable.mel_t1_vibroblade,
            R.drawable.mel_t1_vibroknife,
            R.drawable.mel_t1_vibrosword,
            -6,
            -4,
            -6,
            R.drawable.mod_t1_balancedhilt,
            R.drawable.mod_t1_extendedhaft,
            -5,
            -6,
            -2,
            -6,
            R.drawable.mel_t2_bd_1vibroax,
            R.drawable.mel_t2_doublevibrosword,
            R.drawable.mel_t2_polearm,
            R.drawable.mel_t2_stunbaton,
            R.drawable.mel_t2_vibroknucklers,
            -5,
            -6,
            -4,
            -6,
            R.drawable.mod_t2_energizedhilt,
            R.drawable.mod_t2_highimpactguard,
            R.drawable.mod_t2_weightedhead,
            R.drawable.mod_t2_focusingbeam,
            -5,
            -5,
            -6,
            -3,
            -6,
            R.drawable.mel_t3_ancientlightsaber,
            R.drawable.mel_t3_borifle,
            R.drawable.mel_t3_electrostaff,
            R.drawable.mel_t3_forcepike,
            R.drawable.mel_t3_ryykblades,
            -5,
            -6,
            -4,
            -6,
            R.drawable.mod_t3_shockemitter,
            R.drawable.mod_t3_vibrogenerator,
            -5,
        )
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity,rewardsPathIds)
        return rewardsView
    }
}

class Ranged : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rewardsPathIds = arrayListOf<Int>(
            -6,
            -1,
            -6,
            R.drawable.gun_t1_chargedpistol,
            R.drawable.gun_t1_ddcdefender,
            R.drawable.gun_t1_dh17,
            R.drawable.gun_t1_dl44,
            R.drawable.gun_t1_e11,
            R.drawable.gun_t1_handcannon,
            R.drawable.gun_t1_tatooinehuntingrifle,
            -5,
            -5,
            -6,
            -4,
            -6,
            R.drawable.mod_t1_marksmanbarrel,
            R.drawable.mod_t1_sniperscope,
            R.drawable.mod_t1_tacticaldisplay,
            R.drawable.mod_t1_underbarrelhh4,
            R.drawable.mod_t1_chargedammopack,
            -5,
            -6,
            -2,
            -6,
            R.drawable.gun_t2_434deathhammer,
            R.drawable.gun_t2_a280,
            R.drawable.gun_t2_dt12heavyblasterpistol,
            R.drawable.gun_t2_e11d,
            R.drawable.gun_t2_ee3carbine,
            R.drawable.gun_t2_huntersrifle,
            R.drawable.gun_t2_t21,
            -5,
            -5,
            -6,
            -4,
            -6,
            R.drawable.mod_t2_boltupgrade,
            R.drawable.mod_t2_overcharger,
            R.drawable.mod_t2_plasmacell,
            R.drawable.mod_t2_spreadbarrel,
            -5,
            -5,
            -6,
            -3,
            -6,
            R.drawable.gun_t3_a12sniperrifle,
            R.drawable.gun_t3_disruptorpistol,
            R.drawable.gun_t3_dlt19,
            R.drawable.gun_t3_dxr6,
            R.drawable.gun_t3_modifiedenergycannon,
            R.drawable.gun_t3_pulsecannon,
            R.drawable.gun_t3_sportingblaster,
            R.drawable.gun_t3_valken38carbine,
            -5,
            -6,
            -4,
            -6,
            R.drawable.mod_t3_disruptioncell,
            R.drawable.mod_t3_telescopingsights,
            -5,
        )
        val rewardsView = inflater.inflate(R.layout.rewards_fragment2,container,false) as View
        val rewardsgrid = rewardsView.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this.context as Activity,rewardsPathIds)
        return rewardsView
    }
}

class ImageAdapter internal constructor(val mContext: Activity, var imageArray:
ArrayList<Int>) :
    BaseAdapter
    () {

    // References to our images

    override fun getCount(): Int {
        return imageArray.size
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

        if (imageArray[position] > 0){
            gridItem = mContext.layoutInflater.inflate(R.layout.list_item, null, true)
            gridItem.item.setImageResource(imageArray[position])
        }else if (imageArray[position] == -1){
            gridItem = mContext.layoutInflater.inflate(R.layout.tier1_title, null, true)
        }else if (imageArray[position] == -2){
            gridItem = mContext.layoutInflater.inflate(R.layout.tier2_title, null, true)
        }else if (imageArray[position] == -3){
            gridItem = mContext.layoutInflater.inflate(R.layout.tier3_title, null, true)
        }else if (imageArray[position] == -4){
            gridItem = mContext.layoutInflater.inflate(R.layout.mods_divider, null, true)
        }else if (imageArray[position] == -5){
            gridItem = mContext.layoutInflater.inflate(R.layout.empty_item, null, true)
        }else {
            gridItem = mContext.layoutInflater.inflate(R.layout.empty_space, null, true)
        }
        return gridItem
    }
}

class GlideryBullSh constructor(var idArrayList: ArrayList<Int>, var pathToDrawable:
ArrayList<Int>,val activity: Fragment ,val fragment: View) {
    fun doYourThing(){
        for(i in 0..idArrayList.size-1){
            Glide.with(activity)
                .load(pathToDrawable[i])
                .into(fragment.findViewById(idArrayList[i]))
        }
    }
}
