package com.example.imperialassault

object Items {
    var rewardsArray:Array<Item>? = null

    var accArray:Array<Item>? = null

    var armorArray:Array<Item>? = null
    var meleeArray:Array<Item>? = null
    var rangedArray:Array<Item>? = null

    //types
    const val reward = 0
    const val acc = 1
    const val armor = 2
    const val melee = 3
    const val ranged = 4

    const val tier1 = -1
    const val tier2 = -2
    const val tier3 = -3
    const val mods = -4
    const val empty = -5
    const val space = -6

    //subtype
    const val normal = 0
    const val mod = 1
    const val helmet = 2

    init {

        val titleTier1 = Item(tier1,R.layout.tier1_title)
        val titleTier2 = Item(tier2,R.layout.tier2_title)
        val titleTier3 = Item(tier3,R.layout.tier3_title)
        val modsDivider = Item(mods,R.layout.mods_divider)
        val emptyItem = Item(empty, R.layout.empty_item)
        val emptySpace = Item(space,R.layout.empty_space)




        //******************************************************************************************
        //REWARDS

        rewardsArray= arrayOf(
            Item(reward,R.drawable.reward1),
            Item(reward,R.drawable.reward2),
            Item(reward,R.drawable.reward3),

            Item(reward,R.drawable.reward4),
            Item(reward,R.drawable.reward5),
            Item(reward,R.drawable.reward6),

            Item(reward,R.drawable.reward7),
            Item(reward,R.drawable.reward8),
            Item(reward,R.drawable.reward9),

            Item(reward,R.drawable.reward10),
            Item(reward,R.drawable.reward11),
            Item(reward,R.drawable.reward12),

            Item(reward,R.drawable.reward13),
            Item(reward,R.drawable.reward14),
            Item(reward,R.drawable.reward15,),

            Item(reward,R.drawable.reward16),
            Item(reward,R.drawable.reward17),
            Item(reward,R.drawable.reward18),

            Item(reward,R.drawable.reward19),
            Item(reward,R.drawable.reward20),
            Item(reward,R.drawable.reward21),

            Item(reward,R.drawable.reward22),
            Item(reward,R.drawable.reward23),
            Item(reward,R.drawable.reward24),

            Item(reward,R.drawable.reward25))
        for(i in 0..rewardsArray!!.size-1){
            rewardsArray!!.get(i).index = i
        }
        rewardsArray!![5].health = 3
        rewardsArray!![11].health = 10

        //******************************************************************************************
        //ACCESSORIES

        accArray= arrayOf(
            emptySpace,
            titleTier1,
            emptySpace,

            Item(acc, R.drawable.acc_t1_bactapump),
            Item(acc,R.drawable.acc_t1_combatvembrace),
            Item(acc,R.drawable.acc_t1_emergancyinjector),

            Item(acc, R.drawable.acc_t1_portablemedkit),
            Item(acc, R.drawable.acc_t1_survivalgear),
            emptyItem,

            emptySpace,
            titleTier2,
            emptySpace,

            Item(acc,R.drawable.acc_t2_cyberneticarm),
            Item(acc,R.drawable.acc_t2_extraammunition),
            Item(acc,R.drawable.acc_t2_mandalorianhelmet,helmet),

            Item(acc,R.drawable.acc_t2_r5_astromech),
            Item(acc,R.drawable.acc_t2_slicingtools),
            emptyItem,

            emptySpace,
            titleTier3,
            emptySpace,

            Item(acc,R.drawable.acc_t3_combatknife),
            Item(acc,R.drawable.acc_t3_combatvisor),
            Item(acc,R.drawable.acc_t3_concussiongrenades),

            Item(acc,R.drawable.acc_t3_hiddenblade),
            Item(acc,R.drawable.acc_t3_personalshield),
            Item(acc,R.drawable.acc_t3_powercharger),

            Item(acc,R.drawable.acc_t3_reinforcedhelmet,helmet),
            Item(acc,R.drawable.acc_t3_supplypack),
            emptyItem)

        for(i in 0..accArray!!.size-1){
            accArray!!.get(i).index = i
        }
        //Combat Vambrace
        accArray!!.get(4).health = 1
        //Cybernetic Arm
        accArray!!.get(12).health = -2
        accArray!!.get(12).endurance = 1
        //Mando Helmet
        accArray!!.get(14).health = 1


        //******************************************************************************************
        //Armor

        armorArray= arrayOf(
            emptySpace,
            titleTier1,
            emptySpace,

            Item(armor, R.drawable.arm_t1_combatcoat),
            Item(armor, R.drawable.arm_t1_responsivearmor),
            Item(armor, R.drawable.arm_t1_shadowsilkcloak),

            emptySpace,
            titleTier2,
            emptySpace,

            Item(armor, R.drawable.arm_t2_combatcoat),
            Item(armor, R.drawable.arm_t2_environmenthazardsuit),
            Item(armor, R.drawable.arm_t2_laminatearmor),

            emptySpace,
            titleTier3,
            emptySpace,

            Item(armor, R.drawable.arm_t3_admiralsuniform),
            Item(armor, R.drawable.arm_t3_laminatearmor),
            Item(armor, R.drawable.arm_t3_plastoidarmor))

        for(i in 0..armorArray!!.size-1){
            armorArray!!.get(i).index = i
        }

        //Tier1 Combat Cloak
        armorArray!!.get(3).health = 2
        //Responsive Armor
        armorArray!!.get(4).health = 2
        //Tier2 Combat Cloak
        armorArray!!.get(9).health = 2
        //Tier2 Laminate Armor
        armorArray!!.get(11).health = 3
        //Admirals Uniform
        armorArray!!.get(15).health = 1
        //Tier3 Laminate Armor
        armorArray!!.get(16).health = 3

        //******************************************************************************************
        //MELEE

        meleeArray= arrayOf(
            emptySpace,
            titleTier1,
            emptySpace,

            Item(melee, R.drawable.mel_t1_armoredgauntlets),
            Item(melee, R.drawable.mel_t1_gaffistick),
            Item(melee, R.drawable.mel_t1_punchdagger),

            Item(melee, R.drawable.mel_t1_vibroblade),
            Item(melee, R.drawable.mel_t1_vibroknife),
            Item(melee, R.drawable.mel_t1_vibrosword),

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee, R.drawable.mod_t1_balancedhilt,mod),
            Item(melee, R.drawable.mod_t1_extendedhaft,mod),
            emptyItem,

            emptySpace,
            titleTier2,
            emptySpace,

            Item(melee, R.drawable.mel_t2_bd_1vibroax),
            Item(melee, R.drawable.mel_t2_doublevibrosword),
            Item(melee, R.drawable.mel_t2_polearm),

            Item(melee, R.drawable.mel_t2_stunbaton),
            Item(melee, R.drawable.mel_t2_vibroknucklers),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee, R.drawable.mod_t2_energizedhilt,mod),
            Item(melee, R.drawable.mod_t2_highimpactguard,mod),
            Item(melee, R.drawable.mod_t2_weightedhead,mod),

            Item(melee, R.drawable.mod_t2_focusingbeam,mod),
            emptyItem,
            emptyItem,

            emptySpace,
            titleTier3,
            emptySpace,

            Item(melee, R.drawable.mel_t3_ancientlightsaber),
            Item(melee, R.drawable.mel_t3_borifle),
            Item(melee, R.drawable.mel_t3_electrostaff),

            Item(melee, R.drawable.mel_t3_forcepike),
            Item(melee, R.drawable.mel_t3_ryykblades),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee, R.drawable.mod_t3_shockemitter,mod),
            Item(melee, R.drawable.mod_t3_vibrogenerator,mod),
            emptyItem)


        for(i in 0..meleeArray!!.size-1){
            meleeArray!!.get(i).index =i
        }

        //******************************************************************************************
        //RANGED

        rangedArray= arrayOf(
            emptySpace,
            titleTier1,
            emptySpace,

            Item(melee, R.drawable.gun_t1_chargedpistol),
            Item(melee, R.drawable.gun_t1_ddcdefender),
            Item(melee, R.drawable.gun_t1_dh17),

            Item(melee, R.drawable.gun_t1_dl44),
            Item(melee, R.drawable.gun_t1_e11),
            Item(melee, R.drawable.gun_t1_handcannon),

            Item(melee, R.drawable.gun_t1_tatooinehuntingrifle),
            emptyItem,
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee, R.drawable.mod_t1_marksmanbarrel,mod),
            Item(melee, R.drawable.mod_t1_sniperscope,mod),
            Item(melee, R.drawable.mod_t1_tacticaldisplay,mod),

            Item(melee, R.drawable.mod_t1_underbarrelhh4,mod),
            Item(melee, R.drawable.mod_t1_chargedammopack,mod),
            emptyItem,

            emptySpace,
            titleTier2,
            emptySpace,

            Item(melee, R.drawable.gun_t2_434deathhammer),
            Item(melee, R.drawable.gun_t2_a280),
            Item(melee, R.drawable.gun_t2_dt12heavyblasterpistol),

            Item(melee, R.drawable.gun_t2_e11d),
            Item(melee, R.drawable.gun_t2_ee3carbine),
            Item(melee, R.drawable.gun_t2_huntersrifle),

            Item(melee, R.drawable.gun_t2_t21),
            emptyItem,
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee, R.drawable.mod_t2_boltupgrade,mod),
            Item(melee, R.drawable.mod_t2_overcharger,mod),
            Item(melee, R.drawable.mod_t2_plasmacell,mod),

            Item(melee, R.drawable.mod_t2_spreadbarrel,mod),
            emptyItem,
            emptyItem,

            emptySpace,
            titleTier3,
            emptySpace,

            Item(melee, R.drawable.gun_t3_a12sniperrifle),
            Item(melee, R.drawable.gun_t3_disruptorpistol),
            Item(melee, R.drawable.gun_t3_dlt19),

            Item(melee, R.drawable.gun_t3_dxr6),
            Item(melee, R.drawable.gun_t3_modifiedenergycannon),
            Item(melee, R.drawable.gun_t3_pulsecannon),

            Item(melee, R.drawable.gun_t3_sportingblaster),
            Item(melee, R.drawable.gun_t3_valken38carbine),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee, R.drawable.mod_t3_disruptioncell,mod),
            Item(melee, R.drawable.mod_t3_telescopingsights,mod),
            emptyItem)

        for(i in 0..rangedArray!!.size-1){
            rangedArray!!.get(i).index =i + meleeArray!!.size
        }
    }
}

class Item (){
    var resourceId = -1
    var index = -1
    var subType = 0
    var type = 0
    var health = 0
    var endurance = 0


    constructor(type:Int,resoureId:Int) : this() {
        this.resourceId = resoureId
        this.type = type

    }
    constructor(type:Int,resoureId:Int,subType:Int) : this() {
        this.resourceId = resoureId
        this.type = type
        this.subType = subType
    }
}