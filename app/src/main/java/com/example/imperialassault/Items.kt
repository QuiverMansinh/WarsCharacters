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

        val titleTier1 = Item(tier1,-1,R.layout.tier1_title)
        val titleTier2 = Item(tier2,-1,R.layout.tier2_title)
        val titleTier3 = Item(tier3,-1,R.layout.tier3_title)
        val modsDivider = Item(mods,-1,R.layout.mods_divider)
        val emptyItem = Item(empty,-1, R.layout.empty_item)
        val emptySpace = Item(space,-1,R.layout.empty_space)




        //******************************************************************************************
        //REWARDS

        rewardsArray= arrayOf(
            Item(reward,0,R.drawable.reward1),
            Item(reward,0,R.drawable.reward2),
            Item(reward,0,R.drawable.reward3),

            Item(reward,0,R.drawable.reward4),
            Item(reward,0,R.drawable.reward5),
            Item(reward,0,R.drawable.reward6),

            Item(reward,0,R.drawable.reward7),
            Item(reward,0,R.drawable.reward8),
            Item(reward,0,R.drawable.reward9),

            Item(reward,0,R.drawable.reward10),
            Item(reward,0,R.drawable.reward11),
            Item(reward,0,R.drawable.reward12),

            Item(reward,0,R.drawable.reward13),
            Item(reward,0,R.drawable.reward14),
            Item(reward,0,R.drawable.reward15,),

            Item(reward,0,R.drawable.reward16),
            Item(reward,0,R.drawable.reward17),
            Item(reward,0,R.drawable.reward18),

            Item(reward,0,R.drawable.reward19),
            Item(reward,0,R.drawable.reward20),
            Item(reward,0,R.drawable.reward21),

            Item(reward,0,R.drawable.reward22),
            Item(reward,0,R.drawable.reward23),
            Item(reward,0,R.drawable.reward24),

            Item(reward,0,R.drawable.reward25))
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

            Item(acc,1,R.drawable.acc_t1_bactapump),
            Item(acc,1,R.drawable.acc_t1_combatvembrace),
            Item(acc,1,R.drawable.acc_t1_emergancyinjector),

            Item(acc,1, R.drawable.acc_t1_portablemedkit),
            Item(acc,1, R.drawable.acc_t1_survivalgear),
            emptyItem,

            emptySpace,
            titleTier2,
            emptySpace,

            Item(acc,2,R.drawable.acc_t2_cyberneticarm),
            Item(acc,2,R.drawable.acc_t2_extraammunition),
            Item(acc,2,R.drawable.acc_t2_mandalorianhelmet,helmet),

            Item(acc,2,R.drawable.acc_t2_r5_astromech),
            Item(acc,2,R.drawable.acc_t2_slicingtools),
            emptyItem,

            emptySpace,
            titleTier3,
            emptySpace,

            Item(acc,3,R.drawable.acc_t3_combatknife),
            Item(acc,3,R.drawable.acc_t3_combatvisor),
            Item(acc,3,R.drawable.acc_t3_concussiongrenades),

            Item(acc,3,R.drawable.acc_t3_hiddenblade),
            Item(acc,3,R.drawable.acc_t3_personalshield),
            Item(acc,3,R.drawable.acc_t3_powercharger),

            Item(acc,3,R.drawable.acc_t3_reinforcedhelmet,helmet),
            Item(acc,3,R.drawable.acc_t3_supplypack),
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

            Item(armor,1, R.drawable.arm_t1_combatcoat),
            Item(armor,1, R.drawable.arm_t1_responsivearmor),
            Item(armor,1, R.drawable.arm_t1_shadowsilkcloak),

            emptySpace,
            titleTier2,
            emptySpace,

            Item(armor,2, R.drawable.arm_t2_combatcoat),
            Item(armor,2, R.drawable.arm_t2_environmenthazardsuit),
            Item(armor,2, R.drawable.arm_t2_laminatearmor),

            emptySpace,
            titleTier3,
            emptySpace,

            Item(armor,3, R.drawable.arm_t3_admiralsuniform),
            Item(armor,3, R.drawable.arm_t3_laminatearmor),
            Item(armor,3, R.drawable.arm_t3_plastoidarmor))

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

            Item(melee,1, R.drawable.gun_t1_heroweapon),
            Item(melee,1, R.drawable.mel_t1_armoredgauntlets),
            Item(melee,1, R.drawable.mel_t1_gaffistick),

            Item(melee,1, R.drawable.mel_t1_punchdagger),
            Item(melee,1, R.drawable.mel_t1_vibroblade),
            Item(melee,1, R.drawable.mel_t1_vibroknife),

            Item(melee,1, R.drawable.mel_t1_vibrosword),
            emptyItem,
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee,1, R.drawable.mod_t1_balancedhilt,mod),
            Item(melee,1, R.drawable.mod_t1_extendedhaft,mod),
            emptyItem,

            emptySpace,
            titleTier2,
            emptySpace,

            Item(melee,2, R.drawable.mel_t2_bd_1vibroax),
            Item(melee,2, R.drawable.mel_t2_doublevibrosword),
            Item(melee,2, R.drawable.mel_t2_polearm),

            Item(melee,2, R.drawable.mel_t2_stunbaton),
            Item(melee,2, R.drawable.mel_t2_vibroknucklers),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee,2, R.drawable.mod_t2_energizedhilt,mod),
            Item(melee,2, R.drawable.mod_t2_highimpactguard,mod),
            Item(melee,2, R.drawable.mod_t2_weightedhead,mod),

            Item(melee,2, R.drawable.mod_t2_focusingbeam,mod),
            emptyItem,
            emptyItem,

            emptySpace,
            titleTier3,
            emptySpace,

            Item(melee,3, R.drawable.mel_t3_ancientlightsaber),
            Item(melee,3, R.drawable.mel_t3_borifle),
            Item(melee,3, R.drawable.mel_t3_electrostaff),

            Item(melee,3, R.drawable.mel_t3_forcepike),
            Item(melee,3, R.drawable.mel_t3_ryykblades),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(melee,3, R.drawable.mod_t3_shockemitter,mod),
            Item(melee,3, R.drawable.mod_t3_vibrogenerator,mod),
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

            Item(ranged,1, R.drawable.gun_t1_heroweapon),
            Item(ranged,1, R.drawable.gun_t1_chargedpistol),
            Item(ranged,1, R.drawable.gun_t1_ddcdefender),

            Item(ranged,1, R.drawable.gun_t1_dh17),
            Item(ranged,1, R.drawable.gun_t1_dl44),
            Item(ranged,1, R.drawable.gun_t1_e11),

            Item(ranged,1, R.drawable.gun_t1_handcannon),
            Item(ranged,1, R.drawable.gun_t1_tatooinehuntingrifle),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(ranged,1, R.drawable.mod_t1_marksmanbarrel,mod),
            Item(ranged,1, R.drawable.mod_t1_sniperscope,mod),
            Item(ranged,1, R.drawable.mod_t1_tacticaldisplay,mod),

            Item(ranged,1, R.drawable.mod_t1_underbarrelhh4,mod),
            Item(ranged,1, R.drawable.mod_t1_chargedammopack,mod),
            emptyItem,

            emptySpace,
            titleTier2,
            emptySpace,

            Item(ranged,2, R.drawable.gun_t2_434deathhammer),
            Item(ranged,2, R.drawable.gun_t2_a280),
            Item(ranged,2, R.drawable.gun_t2_dt12heavyblasterpistol),

            Item(ranged,2, R.drawable.gun_t2_e11d),
            Item(ranged,2, R.drawable.gun_t2_ee3carbine),
            Item(ranged,2, R.drawable.gun_t2_huntersrifle),

            Item(ranged,2, R.drawable.gun_t2_t21),
            emptyItem,
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(ranged,2, R.drawable.mod_t2_boltupgrade,mod),
            Item(ranged,2, R.drawable.mod_t2_overcharger,mod),
            Item(ranged,2, R.drawable.mod_t2_plasmacell,mod),

            Item(ranged,2, R.drawable.mod_t2_spreadbarrel,mod),
            emptyItem,
            emptyItem,

            emptySpace,
            titleTier3,
            emptySpace,

            Item(ranged,3, R.drawable.gun_t3_a12sniperrifle),
            Item(ranged,3, R.drawable.gun_t3_disruptorpistol),
            Item(ranged,3, R.drawable.gun_t3_dlt19),

            Item(ranged,3, R.drawable.gun_t3_dxr6),
            Item(ranged,3, R.drawable.gun_t3_modifiedenergycannon),
            Item(ranged,3, R.drawable.gun_t3_pulsecannon),

            Item(ranged,3, R.drawable.gun_t3_sportingblaster),
            Item(ranged,3, R.drawable.gun_t3_valken38carbine),
            emptyItem,

            emptySpace,
            modsDivider,
            emptySpace,

            Item(ranged,3, R.drawable.mod_t3_disruptioncell,mod),
            Item(ranged,3, R.drawable.mod_t3_telescopingsights,mod),
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
    var tier = 0

    constructor(type:Int, tier:Int, resourceId:Int) : this() {
        this.resourceId = resourceId
        this.type = type

    }
    constructor(type:Int,  tier:Int, resourceId:Int,subType:Int) : this() {
        this.resourceId = resourceId
        this.type = type
        this.subType = subType
    }
}