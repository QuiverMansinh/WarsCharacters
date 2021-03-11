package com.example.imperialassault

object Items {
    var rewardsArray: ArrayList<Item>? = null

    var accArray: Array<Item>? = null

    var armorArray: ArrayList<Item>? = null
    var meleeArray: ArrayList<Item>? = null
    var rangedArray: ArrayList<Item>? = null
    var itemsArray: ArrayList<Item>? = null

    //types
    const val reward = 0
    const val acc = 1
    const val armor = 2
    const val melee = 3
    const val ranged = 4
    const val meleeMod = 5
    const val rangedMod = 6

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

    //image change items
    const val mandoHelmetIndex = 14
    const val astromechIndex = 15
    const val combatVisorIndex = 22
    const val reinforcedHelmetIndex = 27
    const val ancientLightSaberIndex = 39
    const val quickDrawHolsterIndex = 18
    const val adrenalImplantsIndex = 0
    const val bardottanShardIndex = 3

    init {

        val titleTier1 = Item(tier1, -1, R.layout.tier1_title)
        val titleTier2 = Item(tier2, -1, R.layout.tier2_title)
        val titleTier3 = Item(tier3, -1, R.layout.tier3_title)
        val modsDivider = Item(mods, -1, R.layout.mods_divider)
        val emptyItem = Item(empty, -1, R.layout.empty_item)
        val emptySpace = Item(space, -1, R.layout.empty_space)


        //******************************************************************************************
        //REWARDS

        rewardsArray = arrayListOf(
            Item(reward, 0, R.drawable.reward1),
            Item(reward, 0, R.drawable.reward2),
            Item(reward, 0, R.drawable.reward3),

            Item(reward, 0, R.drawable.reward4),
            Item(reward, 0, R.drawable.reward5),
            Item(reward, 0, R.drawable.reward6),

            Item(reward, 0, R.drawable.reward7),
            Item(reward, 0, R.drawable.reward8),
            Item(reward, 0, R.drawable.reward9),

            Item(reward, 0, R.drawable.reward10),
            Item(reward, 0, R.drawable.reward11),
            Item(reward, 0, R.drawable.reward12),

            Item(reward, 0, R.drawable.reward13),
            Item(reward, 0, R.drawable.reward14),
            Item(reward, 0, R.drawable.reward15),

            Item(reward, 0, R.drawable.reward16),
            Item(reward, 0, R.drawable.reward17),
            Item(reward, 0, R.drawable.reward18),

            Item(reward, 0, R.drawable.reward19),
            Item(reward, 0, R.drawable.reward20),
            Item(reward, 0, R.drawable.reward21),

            Item(reward, 0, R.drawable.reward22),
            Item(reward, 0, R.drawable.reward23),
            Item(reward, 0, R.drawable.reward24),

            Item(reward, 0, R.drawable.reward25)
        )
        for (i in 0..rewardsArray!!.size - 1) {
            rewardsArray!!.get(i).index = i
        }
        rewardsArray!![5].health = 3
        rewardsArray!![11].health = 10
//
//        //******************************************************************************************
//        //ACCESSORIES
//
//        accArray = arrayOf(
//            emptySpace,
//            titleTier1,
//            emptySpace,
//
//            Item(acc, 1, R.drawable.acc_t1_bactapump),
//            Item(acc, 1, R.drawable.acc_t1_combatvembrace),
//            Item(acc, 1, R.drawable.acc_t1_emergancyinjector),
//
//            Item(acc, 1, R.drawable.acc_t1_portablemedkit),
//            Item(acc, 1, R.drawable.acc_t1_survivalgear),
//            emptyItem,
//
//            emptySpace,
//            titleTier2,
//            emptySpace,
//
//            Item(acc, 2, R.drawable.acc_t2_cyberneticarm),
//            Item(acc, 2, R.drawable.acc_t2_extraammunition,"blaster"),
//            Item(acc, 2, R.drawable.acc_t2_mandalorianhelmet, helmet),
//
//            Item(acc, 2, R.drawable.acc_t2_r5_astromech, "astromech"),
//            Item(acc, 2, R.drawable.acc_t2_slicingtools, "slice"),
//            emptyItem,
//
//            emptySpace,
//            titleTier3,
//            emptySpace,
//
//            Item(acc, 3, R.drawable.acc_t3_combatknife, "slice"),
//            Item(acc, 3, R.drawable.acc_t3_combatvisor),
//            Item(acc, 3, R.drawable.acc_t3_concussiongrenades),
//
//            Item(acc, 3, R.drawable.acc_t3_hiddenblade, "slice"),
//            Item(acc, 3, R.drawable.acc_t3_personalshield),
//            Item(acc, 3, R.drawable.acc_t3_powercharger),
//
//            Item(acc, 3, R.drawable.acc_t3_reinforcedhelmet, helmet),
//            Item(acc, 3, R.drawable.acc_t3_supplypack),
//            emptyItem
//        )
//
//        for (i in 0..accArray!!.size - 1) {
//            accArray!!.get(i).index = i
//        }
//        //Combat Vambrace
//        accArray!!.get(4).health = 1
//        //Cybernetic Arm
//        accArray!!.get(12).health = -2
//        accArray!!.get(12).endurance = 1
//        //Mando Helmet
//        accArray!!.get(14).health = 1
//
//
//        //******************************************************************************************
//        //Armor
//
//        armorArray = arrayOf(
//            emptySpace,
//            titleTier1,
//            emptySpace,
//
//            Item(armor, 1, R.drawable.arm_t1_combatcoat,"clothing"),
//            Item(armor, 1, R.drawable.arm_t1_responsivearmor,"armor"),
//            Item(armor, 1, R.drawable.arm_t1_shadowsilkcloak,"clothing"),
//
//            emptySpace,
//            titleTier2,
//            emptySpace,
//
//            Item(armor, 2, R.drawable.arm_t2_combatcoat,"clothing"),
//            Item(armor, 2, R.drawable.arm_t2_environmenthazardsuit,"clothing"),
//            Item(armor, 2, R.drawable.arm_t2_laminatearmor,"armor"),
//
//            emptySpace,
//            titleTier3,
//            emptySpace,
//
//            Item(armor, 3, R.drawable.arm_t3_admiralsuniform,"clothing"),
//            Item(armor, 3, R.drawable.arm_t3_laminatearmor,"armor"),
//            Item(armor, 3, R.drawable.arm_t3_plastoidarmor,"armor")
//        )
//
//        for (i in 0..armorArray!!.size - 1) {
//            armorArray!!.get(i).index = i
//        }
//
//        //Tier1 Combat Cloak
//        armorArray!!.get(3).health = 2
//        //Responsive Armor
//        armorArray!!.get(4).health = 2
//        //Tier2 Combat Cloak
//        armorArray!!.get(9).health = 2
//        //Tier2 Laminate Armor
//        armorArray!!.get(11).health = 3
//        //Admirals Uniform
//        armorArray!!.get(15).health = 1
//        //Tier3 Laminate Armor
//        armorArray!!.get(16).health = 3
//
//        //******************************************************************************************
//        //MELEE
//
//        meleeArray = arrayOf(
//            emptySpace,
//            titleTier1,
//            emptySpace,
//
//            Item(melee, 1, R.drawable.gun_t1_heroweapon,"impact"),
//            Item(melee, 1, R.drawable.mel_t1_armoredgauntlets,"impact"),
//            Item(melee, 1, R.drawable.mel_t1_gaffistick,"impact"),
//
//            Item(melee, 1, R.drawable.mel_t1_punchdagger,"slice"),
//            Item(melee, 1, R.drawable.mel_t1_vibroblade,"slice"),
//            Item(melee, 1, R.drawable.mel_t1_vibroknife,"slice"),
//
//            Item(melee, 1, R.drawable.mel_t1_vibrosword,"slice"),
//            emptyItem,
//            emptyItem,
//
//            emptySpace,
//            modsDivider,
//            emptySpace,
//
//            Item(melee, 1, R.drawable.mod_t1_balancedhilt, mod),
//            Item(melee, 1, R.drawable.mod_t1_extendedhaft, mod),
//            emptyItem,
//
//            emptySpace,
//            titleTier2,
//            emptySpace,
//
//            Item(melee, 2, R.drawable.mel_t2_bd_1vibroax,"impact"),
//            Item(melee, 2, R.drawable.mel_t2_doublevibrosword,"slice"),
//            Item(melee, 2, R.drawable.mel_t2_polearm,"impact"),
//
//            Item(melee, 2, R.drawable.mel_t2_stunbaton,"electric"),
//            Item(melee, 2, R.drawable.mel_t2_vibroknucklers,"impact"),
//            emptyItem,
//
//            emptySpace,
//            modsDivider,
//            emptySpace,
//
//            Item(melee, 2, R.drawable.mod_t2_energizedhilt, mod),
//            Item(melee, 2, R.drawable.mod_t2_highimpactguard, mod),
//            Item(melee, 2, R.drawable.mod_t2_weightedhead, mod),
//
//            Item(melee, 2, R.drawable.mod_t2_focusingbeam, mod),
//            emptyItem,
//            emptyItem,
//
//            emptySpace,
//            titleTier3,
//            emptySpace,
//
//            Item(melee, 3, R.drawable.mel_t3_ancientlightsaber,"lightSaber"),
//            Item(melee, 3, R.drawable.mel_t3_borifle,"impact"),
//            Item(melee, 3, R.drawable.mel_t3_electrostaff,"electric"),
//
//            Item(melee, 3, R.drawable.mel_t3_forcepike,"impact"),
//            Item(melee, 3, R.drawable.mel_t3_ryykblades,"slice"),
//            emptyItem,
//
//            emptySpace,
//            modsDivider,
//            emptySpace,
//
//            Item(melee, 3, R.drawable.mod_t3_shockemitter, mod),
//            Item(melee, 3, R.drawable.mod_t3_vibrogenerator, mod),
//            emptyItem
//        )
//
//
//        for (i in 0..meleeArray!!.size - 1) {
//            meleeArray!!.get(i).index = i
//        }
//
//        //******************************************************************************************
//        //RANGED
//
//        rangedArray = arrayOf(
//            emptySpace,
//            titleTier1,
//            emptySpace,
//
//            Item(ranged, 1, R.drawable.gun_t1_heroweapon,"blaster"),
//            Item(ranged, 1, R.drawable.gun_t1_chargedpistol,"blaster"),
//            Item(ranged, 1, R.drawable.gun_t1_ddcdefender,"blaster"),
//
//            Item(ranged, 1, R.drawable.gun_t1_dh17,"blaster"),
//            Item(ranged, 1, R.drawable.gun_t1_dl44,"blaster"),
//            Item(ranged, 1, R.drawable.gun_t1_e11,"blaster"),
//
//            Item(ranged, 1, R.drawable.gun_t1_handcannon,"blaster"),
//            Item(ranged, 1, R.drawable.gun_t1_tatooinehuntingrifle,"blaster"),
//            emptyItem,
//
//            emptySpace,
//            modsDivider,
//            emptySpace,
//
//            Item(ranged, 1, R.drawable.mod_t1_marksmanbarrel, mod),
//            Item(ranged, 1, R.drawable.mod_t1_sniperscope, mod),
//            Item(ranged, 1, R.drawable.mod_t1_tacticaldisplay, mod),
//
//            Item(ranged, 1, R.drawable.mod_t1_underbarrelhh4, mod),
//            Item(ranged, 1, R.drawable.mod_t1_chargedammopack, mod),
//            emptyItem,
//
//            emptySpace,
//            titleTier2,
//            emptySpace,
//
//            Item(ranged, 2, R.drawable.gun_t2_434deathhammer,"blaster"),
//            Item(ranged, 2, R.drawable.gun_t2_a280,"blaster"),
//            Item(ranged, 2, R.drawable.gun_t2_dt12heavyblasterpistol,"blaster"),
//
//            Item(ranged, 2, R.drawable.gun_t2_e11d,"blaster"),
//            Item(ranged, 2, R.drawable.gun_t2_ee3carbine,"blaster"),
//            Item(ranged, 2, R.drawable.gun_t2_huntersrifle,"blaster"),
//
//            Item(ranged, 2, R.drawable.gun_t2_t21,"blaster"),
//            emptyItem,
//            emptyItem,
//
//            emptySpace,
//            modsDivider,
//            emptySpace,
//
//            Item(ranged, 2, R.drawable.mod_t2_boltupgrade, mod),
//            Item(ranged, 2, R.drawable.mod_t2_overcharger, mod),
//            Item(ranged, 2, R.drawable.mod_t2_plasmacell, mod),
//
//            Item(ranged, 2, R.drawable.mod_t2_spreadbarrel, mod),
//            emptyItem,
//            emptyItem,
//
//            emptySpace,
//            titleTier3,
//            emptySpace,
//
//            Item(ranged, 3, R.drawable.gun_t3_a12sniperrifle,"blaster"),
//            Item(ranged, 3, R.drawable.gun_t3_disruptorpistol,"blaster"),
//            Item(ranged, 3, R.drawable.gun_t3_dlt19,"blaster"),
//
//            Item(ranged, 3, R.drawable.gun_t3_dxr6,"blaster"),
//            Item(ranged, 3, R.drawable.gun_t3_modifiedenergycannon,"blaster"),
//            Item(ranged, 3, R.drawable.gun_t3_pulsecannon,"blaster"),
//
//            Item(ranged, 3, R.drawable.gun_t3_sportingblaster,"blaster"),
//            Item(ranged, 3, R.drawable.gun_t3_valken38carbine,"blaster"),
//            emptyItem,
//
//            emptySpace,
//            modsDivider,
//            emptySpace,
//
//            Item(ranged, 3, R.drawable.mod_t3_disruptioncell, mod),
//            Item(ranged, 3, R.drawable.mod_t3_telescopingsights, mod),
//            emptyItem
//        )
//
//        for (i in 0..rangedArray!!.size - 1) {
//            rangedArray!!.get(i).index = i + meleeArray!!.size
//        }

        //Main ITEMS Array

        itemsArray = arrayListOf(
            Item(acc, 1, acc, R.drawable.acc_t1_bactapump,""),
            Item(acc, 1, acc, R.drawable.acc_t1_combatvembrace,""),
            Item(acc, 1, acc, R.drawable.acc_t1_emergancyinjector,""),

            Item(acc, 1, acc, R.drawable.acc_t1_portablemedkit,""),
            Item(acc, 1, acc, R.drawable.acc_t1_survivalgear,""),

            Item(acc, 2, acc, R.drawable.acc_t2_cyberneticarm,""),
            Item(acc, 2, acc, R.drawable.acc_t2_extraammunition,"blaster"),
            Item(acc, 2, acc, R.drawable.acc_t2_mandalorianhelmet, helmet,""),

            Item(acc, 2, acc, R.drawable.acc_t2_r5_astromech, "astromech"),
            Item(acc, 2, acc, R.drawable.acc_t2_slicingtools, "slice"),

            Item(acc, 3, acc, R.drawable.acc_t3_combatknife, "slice"),
            Item(acc, 3, acc, R.drawable.acc_t3_combatvisor,""),
            Item(acc, 3, acc, R.drawable.acc_t3_concussiongrenades,""),

            Item(acc, 3, acc, R.drawable.acc_t3_hiddenblade, "slice"),
            Item(acc, 3, acc, R.drawable.acc_t3_personalshield,""),
            Item(acc, 3, acc, R.drawable.acc_t3_powercharger,""),

            Item(acc, 3, acc, R.drawable.acc_t3_reinforcedhelmet, helmet,""),
            Item(acc, 3, acc, R.drawable.acc_t3_supplypack,""),

            Item(armor, 1,armor, R.drawable.arm_t1_combatcoat,"clothing"),
            Item(armor, 1,armor, R.drawable.arm_t1_responsivearmor,"armor"),
            Item(armor, 1,armor, R.drawable.arm_t1_shadowsilkcloak,"clothing"),

            Item(armor, 2,armor, R.drawable.arm_t2_combatcoat,"clothing"),
            Item(armor, 2,armor, R.drawable.arm_t2_environmenthazardsuit,"clothing"),
            Item(armor, 2,armor, R.drawable.arm_t2_laminatearmor,"armor"),

            Item(armor, 3,armor, R.drawable.arm_t3_admiralsuniform,"clothing"),
            Item(armor, 3,armor, R.drawable.arm_t3_laminatearmor,"armor"),
            Item(armor, 3,armor, R.drawable.arm_t3_plastoidarmor,"armor"),

            Item(melee, 1,melee, R.drawable.gun_t1_heroweapon,"impact"),
            Item(melee, 1,melee, R.drawable.mel_t1_armoredgauntlets,"impact"),
            Item(melee, 1,melee, R.drawable.mel_t1_gaffistick,"impact"),

            Item(melee, 1,melee, R.drawable.mel_t1_punchdagger,"slice"),
            Item(melee, 1,melee, R.drawable.mel_t1_vibroblade,"slice"),
            Item(melee, 1,melee, R.drawable.mel_t1_vibroknife,"slice"),

            Item(melee, 1,melee, R.drawable.mel_t1_vibrosword,"slice"),

            Item(meleeMod, 1,melee, R.drawable.mod_t1_balancedhilt, ""),
            Item(meleeMod, 1,melee, R.drawable.mod_t1_extendedhaft, ""),

            Item(melee, 2,melee, R.drawable.mel_t2_bd_1vibroax,"impact"),
            Item(melee, 2,melee, R.drawable.mel_t2_doublevibrosword,"slice"),
            Item(melee, 2,melee, R.drawable.mel_t2_polearm,"impact"),

            Item(melee, 2,melee, R.drawable.mel_t2_stunbaton,"electric"),
            Item(melee, 2,melee, R.drawable.mel_t2_vibroknucklers,"impact"),

            Item(meleeMod, 2,melee, R.drawable.mod_t2_energizedhilt, ""),
            Item(meleeMod, 2,melee, R.drawable.mod_t2_highimpactguard, ""),
            Item(meleeMod, 2,melee, R.drawable.mod_t2_weightedhead, ""),

            Item(meleeMod, 2,melee, R.drawable.mod_t2_focusingbeam, ""),

            Item(melee, 3,melee, R.drawable.mel_t3_ancientlightsaber,"lightSaber"),
            Item(melee, 3,melee, R.drawable.mel_t3_borifle,"impact"),
            Item(melee, 3,melee, R.drawable.mel_t3_electrostaff,"electric"),

            Item(melee, 3,melee, R.drawable.mel_t3_forcepike,"impact"),
            Item(melee, 3,melee, R.drawable.mel_t3_ryykblades,"slice"),

            Item(meleeMod, 3,melee, R.drawable.mod_t3_shockemitter, ""),
            Item(meleeMod, 3,melee, R.drawable.mod_t3_vibrogenerator, ""),

            Item(ranged, 1,ranged, R.drawable.gun_t1_heroweapon,"blaster"),
            Item(ranged, 1,ranged, R.drawable.gun_t1_chargedpistol,"blaster"),
            Item(ranged, 1,ranged, R.drawable.gun_t1_ddcdefender,"blaster"),

            Item(ranged, 1,ranged, R.drawable.gun_t1_dh17,"blaster"),
            Item(ranged, 1,ranged, R.drawable.gun_t1_dl44,"blaster"),
            Item(ranged, 1,ranged, R.drawable.gun_t1_e11,"blaster"),

            Item(ranged, 1,ranged, R.drawable.gun_t1_handcannon,"blaster"),
            Item(ranged, 1,ranged, R.drawable.gun_t1_tatooinehuntingrifle,"blaster"),

            Item(rangedMod, 1,ranged, R.drawable.mod_t1_marksmanbarrel, ""),
            Item(rangedMod, 1,ranged, R.drawable.mod_t1_sniperscope, ""),
            Item(rangedMod, 1,ranged, R.drawable.mod_t1_tacticaldisplay, ""),

            Item(rangedMod, 1,ranged, R.drawable.mod_t1_underbarrelhh4, ""),
            Item(rangedMod, 1,ranged, R.drawable.mod_t1_chargedammopack, ""),

            Item(ranged, 2,ranged, R.drawable.gun_t2_434deathhammer,"blaster"),
            Item(ranged, 2,ranged, R.drawable.gun_t2_a280,"blaster"),
            Item(ranged, 2,ranged, R.drawable.gun_t2_dt12heavyblasterpistol,"blaster"),

            Item(ranged, 2,ranged, R.drawable.gun_t2_e11d,"blaster"),
            Item(ranged, 2,ranged, R.drawable.gun_t2_ee3carbine,"blaster"),
            Item(ranged, 2,ranged, R.drawable.gun_t2_huntersrifle,"blaster"),

            Item(ranged, 2,ranged, R.drawable.gun_t2_t21,"blaster"),

            Item(rangedMod, 2,ranged, R.drawable.mod_t2_boltupgrade, ""),
            Item(rangedMod, 2,ranged, R.drawable.mod_t2_overcharger, ""),
            Item(rangedMod, 2,ranged, R.drawable.mod_t2_plasmacell, ""),

            Item(rangedMod, 2,ranged, R.drawable.mod_t2_spreadbarrel, ""),

            Item(ranged, 3,ranged, R.drawable.gun_t3_a12sniperrifle,"blaster"),
            Item(ranged, 3,ranged, R.drawable.gun_t3_disruptorpistol,"blaster"),
            Item(ranged, 3,ranged, R.drawable.gun_t3_dlt19,"blaster"),

            Item(ranged, 3,ranged, R.drawable.gun_t3_dxr6,"blaster"),
            Item(ranged, 3,ranged, R.drawable.gun_t3_modifiedenergycannon,"blaster"),
            Item(ranged, 3,ranged, R.drawable.gun_t3_pulsecannon,"blaster"),

            Item(ranged, 3,ranged, R.drawable.gun_t3_sportingblaster,"blaster"),
            Item(ranged, 3,ranged, R.drawable.gun_t3_valken38carbine,"blaster"),

            Item(rangedMod, 3,ranged, R.drawable.mod_t3_disruptioncell, ""),
            Item(rangedMod, 3,ranged, R.drawable.mod_t3_telescopingsights, ""),
        )

        //indexing of all items at the array

        for (i in 0..itemsArray!!.size - 1) {
            itemsArray!!.get(i).index = i
        }
    }
}

//Main items Class that stores all the values for each individual item

class Item() {
    var resourceId = -1
    var index = -1
    var where = 0
    var subType = 0
    var type = 0
    var health = 0
    var endurance = 0
    var tier = 0
    var soundType = 0 //default sound type

    constructor(type: Int, tier: Int, resourceId: Int) : this() {
        this.resourceId = resourceId
        this.type = type
        this.tier = tier
    }

    constructor(type: Int, tier: Int, resourceId: Int, subType: Int) : this() {
        this.resourceId = resourceId
        this.type = type
        this.subType = subType
        this.tier = tier
    }

    constructor( type: Int, tier: Int, where: Int, imageID: Int, soundType:
    String) : this
        () {
        this.resourceId = imageID
        this.type = type
        this.tier = tier
        this.where = where

        if (soundType == "slice") this.soundType = Sounds.shing
        if (soundType == "lightSaber") this.soundType = Sounds.lightSaber
        if (soundType == "electric") this.soundType =  Sounds.electric
        if (soundType == "blaster") this.soundType =  Sounds.equip_gun
        if (soundType == "impact") this.soundType =  Sounds.equip_impact
        if (soundType == "armor") this.soundType =  Sounds.equip_armor
        if (soundType == "clothing") this.soundType =  Sounds.equip_clothing
        if (soundType == "astromech") this.soundType =  Sounds.droid
    }
    constructor( type: Int, tier: Int, where: Int, imageID: Int,subType: Int, soundType:
    String) : this
        () {
        this.subType = subType
        this.resourceId = imageID
        this.type = type
        this.tier = tier
        this.where = where

        if (soundType == "slice") this.soundType = Sounds.shing
        if (soundType == "lightSaber") this.soundType = Sounds.lightSaber
        if (soundType == "electric") this.soundType =  Sounds.electric
        if (soundType == "blaster") this.soundType =  Sounds.equip_gun
        if (soundType == "impact") this.soundType =  Sounds.equip_impact
        if (soundType == "armor") this.soundType =  Sounds.equip_armor
        if (soundType == "clothing") this.soundType =  Sounds.equip_clothing
        if (soundType == "astromech") this.soundType =  Sounds.droid
    }
}