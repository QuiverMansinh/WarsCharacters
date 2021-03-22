//package com.example.imperialassault
//
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.view.View
//import android.view.Window
//import android.widget.TextView
//import kotlinx.android.synthetic.main.screen_stats.*
//
//class CharacterStatScreen (val screen: CharacterScreen){
//    //endregion
//    //************************************************************************************************************
//    //region Stats Screen
//    //************************************************************************************************************
//
//    val character = screen.character
//    var statsScreen:Dialog?=null
//    init{
//        initStatsScreenDialog()
//    }
//
//
//
//
//    fun initStatsScreen() {
//
//
//        statsScreen!!.edit_stat.visibility = View.INVISIBLE
//        statsScreen!!.stats_name.setText("" + character.name)
//        statsScreen!!.stats_portrait_image.setImageDrawable(character.portraitImage)
//        var level = 5
//        if (character.xpSpent <= 1) {
//            level = 1
//        } else if (character.xpSpent <= 4) {
//            level = 2
//        } else if (character.xpSpent <= 7) {
//            level = 3
//        } else if (character.xpSpent <= 10) {
//            level = 4
//        }
//
//        statsScreen!!.stats_level.setText("" + level)
//        statsScreen!!.stats_moves.setText("" + character.movesTaken)
//        statsScreen!!.stats_attacks.setText("" + character.attacksMade)
//        statsScreen!!.stats_interacts.setText("" + character.interactsUsed)
//        statsScreen!!.stats_wounded.setText("" + character.timesWounded)
//        statsScreen!!.stats_rested.setText("" + character.timesRested)
//        statsScreen!!.stats_withdrawn.setText("" + character.timesWithdrawn)
//        statsScreen!!.stats_activated.setText("" + character.activated)
//        statsScreen!!.stats_damaged.setText("" + character.damageTaken)
//        statsScreen!!.stats_strain.setText("" + character.strainTaken)
//        statsScreen!!.stats_specials.setText("" + character.specialActions)
//        statsScreen!!.stats_focused.setText("" + character.timesFocused)
//        statsScreen!!.stats_hidden.setText("" + character.timesHidden)
//        statsScreen!!.stats_stunned.setText("" + character.timesStunned)
//        statsScreen!!.stats_bleeding.setText("" + character.timesBleeding)
//        statsScreen!!.stats_weakened.setText("" + character.timesWeakened)
//        statsScreen!!.stats_crates.setText("" + character.cratesPickedUp)
//
//        if (character.rewardObtained) {
//            statsScreen!!.stats_reward_obtained.setText("Yes")
//        } else {
//            statsScreen!!.stats_reward_obtained.setText("No")
//        }
//
//        setTotalKills()
//
//        statsScreen!!.stats_kill_villain.setText("" + character.killCount[screen.villain])
//        statsScreen!!.stats_kill_vehicle.setText("" + character.killCount[screen.vehicle])
//        statsScreen!!.stats_kill_creature.setText("" + character.killCount[screen.creature])
//        statsScreen!!.stats_kill_leader.setText("" + character.killCount[screen.leader])
//        statsScreen!!.stats_kill_guardian.setText("" + character.killCount[screen.guard])
//        statsScreen!!.stats_kill_droid.setText("" + character.killCount[screen.droid])
//        statsScreen!!.stats_kill_scum.setText("" + character.killCount[screen.scum])
//        statsScreen!!.stats_kill_trooper.setText("" + character.killCount[screen.trooper])
//
//
//        statsScreen!!.stats_assist_villain.setText("" + character.assistCount[screen.villain])
//        statsScreen!!.stats_assist_vehicle.setText("" + character.assistCount[screen.vehicle])
//        statsScreen!!.stats_assist_creature.setText("" + character.assistCount[screen.creature])
//        statsScreen!!.stats_assist_leader.setText("" + character.assistCount[screen.leader])
//        statsScreen!!.stats_assist_guardian.setText("" + character.assistCount[screen.guard])
//        statsScreen!!.stats_assist_droid.setText("" + character.assistCount[screen.droid])
//        statsScreen!!.stats_assist_scum.setText("" + character.assistCount[screen.scum])
//        statsScreen!!.stats_assist_trooper.setText("" + character.assistCount[screen.trooper])
//
//
//        statsScreen!!.stats_moves.setOnClickListener { onTapStat(statsScreen!!.stats_moves) }
//        statsScreen!!.stats_attacks.setOnClickListener { onTapStat(statsScreen!!.stats_attacks) }
//        statsScreen!!.stats_interacts.setOnClickListener { onTapStat(statsScreen!!.stats_interacts) }
//        statsScreen!!.stats_wounded.setOnClickListener { onTapStat(statsScreen!!.stats_wounded) }
//        statsScreen!!.stats_rested.setOnClickListener { onTapStat(statsScreen!!.stats_rested) }
//        statsScreen!!.stats_withdrawn.setOnClickListener { onTapStat(statsScreen!!.stats_withdrawn) }
//        statsScreen!!.stats_activated.setOnClickListener { onTapStat(statsScreen!!.stats_activated) }
//        statsScreen!!.stats_damaged.setOnClickListener { onTapStat(statsScreen!!.stats_damaged) }
//        statsScreen!!.stats_strain.setOnClickListener { onTapStat(statsScreen!!.stats_strain) }
//        statsScreen!!.stats_specials.setOnClickListener { onTapStat(statsScreen!!.stats_specials) }
//        statsScreen!!.stats_focused.setOnClickListener { onTapStat(statsScreen!!.stats_focused) }
//        statsScreen!!.stats_hidden.setOnClickListener { onTapStat(statsScreen!!.stats_hidden) }
//        statsScreen!!.stats_stunned.setOnClickListener { onTapStat(statsScreen!!.stats_stunned) }
//        statsScreen!!.stats_bleeding.setOnClickListener { onTapStat(statsScreen!!.stats_bleeding) }
//        statsScreen!!.stats_weakened.setOnClickListener { onTapStat(statsScreen!!.stats_weakened) }
//        statsScreen!!.stats_crates.setOnClickListener { onTapStat(statsScreen!!.stats_crates) }
//        statsScreen!!.stats_kill_villain.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_kill_villain
//            )
//        }
//        statsScreen!!.stats_kill_vehicle.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_kill_vehicle
//            )
//        }
//        statsScreen!!.stats_kill_creature.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_kill_creature
//            )
//        }
//        statsScreen!!.stats_kill_leader.setOnClickListener { onTapStat(statsScreen!!.stats_kill_leader) }
//        statsScreen!!.stats_kill_guardian.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_kill_guardian
//            )
//        }
//        statsScreen!!.stats_kill_droid.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_kill_droid
//            )
//        }
//        statsScreen!!.stats_kill_scum.setOnClickListener { onTapStat(statsScreen!!.stats_kill_scum) }
//        statsScreen!!.stats_kill_trooper.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_kill_trooper
//            )
//        }
//
//
//        statsScreen!!.stats_assist_villain.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_villain
//            )
//        }
//        statsScreen!!.stats_assist_vehicle.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_vehicle
//            )
//        }
//        statsScreen!!.stats_assist_creature.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_creature
//            )
//        }
//        statsScreen!!.stats_assist_leader.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_leader
//            )
//        }
//        statsScreen!!.stats_assist_guardian.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_guardian
//            )
//        }
//        statsScreen!!.stats_assist_droid.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_droid
//            )
//        }
//        statsScreen!!.stats_assist_scum.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_scum
//            )
//        }
//        statsScreen!!.stats_assist_trooper.setOnClickListener {
//            onTapStat(
//                statsScreen!!
//                    .stats_assist_trooper
//            )
//        }
//
//
//        statsScreen!!.setOnCancelListener {
//            character.movesTaken = Integer.parseInt(statsScreen!!.stats_moves.getText().toString())
//            character.attacksMade =
//                Integer.parseInt(statsScreen!!.stats_attacks.getText().toString())
//            character.interactsUsed =
//                Integer.parseInt(statsScreen!!.stats_interacts.getText().toString())
//            character.timesWounded =
//                Integer.parseInt(statsScreen!!.stats_wounded.getText().toString())
//            character.timesRested =
//                Integer.parseInt(statsScreen!!.stats_rested.getText().toString())
//            character.timesWithdrawn =
//                Integer.parseInt(statsScreen!!.stats_withdrawn.getText().toString())
//            character.activated =
//                Integer.parseInt(statsScreen!!.stats_activated.getText().toString())
//            character.damageTaken =
//                Integer.parseInt(statsScreen!!.stats_damaged.getText().toString())
//            character.strainTaken =
//                Integer.parseInt(statsScreen!!.stats_strain.getText().toString())
//            character.specialActions =
//                Integer.parseInt(statsScreen!!.stats_specials.getText().toString())
//            character.timesFocused =
//                Integer.parseInt(statsScreen!!.stats_focused.getText().toString())
//            character.timesHidden =
//                Integer.parseInt(statsScreen!!.stats_hidden.getText().toString())
//            character.timesStunned =
//                Integer.parseInt(statsScreen!!.stats_stunned.getText().toString())
//            character.timesBleeding =
//                Integer.parseInt(statsScreen!!.stats_bleeding.getText().toString())
//            character.timesWeakened =
//                Integer.parseInt(statsScreen!!.stats_weakened.getText().toString())
//            character.cratesPickedUp =
//                Integer.parseInt(statsScreen!!.stats_crates.getText().toString())
//
//            character.rewardObtained =
//                statsScreen!!.stats_reward_obtained.getText().toString().equals("Yes")
//
//            updateKillTracker()
//            quickSave()
//        }
//        statsScreen!!.addStat.setOnClickListener {
//            if (currentStatEditing != null) {
//                currentStatEditing!!.text = "" + (Integer.parseInt(
//                    (currentStatEditing!!.getText
//                        ().toString())
//                ) + 1)
//                statsScreen!!.stat_text.setText(currentStatEditing!!.text.toString())
//                updateKillTracker()
//            }
//        }
//        statsScreen!!.minusStat.setOnClickListener {
//            if (currentStatEditing != null ) {
//                currentStatEditing!!.text = "" + Math.max(Integer.parseInt(
//                    (currentStatEditing!!.getText()
//                        .toString())
//                ) - 1,0)
//                statsScreen!!.stat_text.setText(currentStatEditing!!.text.toString())
//                updateKillTracker()
//            }
//        }
//        statsScreen!!.edit_stat.setOnClickListener {
//            statsScreen!!.edit_stat.visibility = View.INVISIBLE
//            updateKillTracker()
//        }
//
//
//    }
//
//    var currentStatEditing: TextView? = null
//    fun updateKillTracker() {
//        character.killCount[screen.villain] =
//            Integer.parseInt(statsScreen!!.stats_kill_villain.getText().toString())
//        character.killCount[screen.vehicle] =
//            Integer.parseInt(statsScreen!!.stats_kill_vehicle.getText().toString())
//        character.killCount[screen.creature] =
//            Integer.parseInt(statsScreen!!.stats_kill_creature.getText().toString())
//        character.killCount[screen.leader] =
//            Integer.parseInt(statsScreen!!.stats_kill_leader.getText().toString())
//        character.killCount[screen.guard] =
//            Integer.parseInt(statsScreen!!.stats_kill_guardian.getText().toString())
//        character.killCount[screen.droid] =
//            Integer.parseInt(statsScreen!!.stats_kill_droid.getText().toString())
//        character.killCount[screen.scum] =
//            Integer.parseInt(statsScreen!!.stats_kill_scum.getText().toString())
//        character.killCount[screen.trooper] =
//            Integer.parseInt(statsScreen!!.stats_kill_trooper.getText().toString())
//
//
//
//        character.assistCount[screen.villain] =
//            Integer.parseInt(statsScreen!!.stats_assist_villain.getText().toString())
//        character.assistCount[screen.vehicle] =
//            Integer.parseInt(statsScreen!!.stats_assist_vehicle.getText().toString())
//        character.assistCount[screen.creature] =
//            Integer.parseInt(statsScreen!!.stats_assist_creature.getText().toString())
//        character.assistCount[screen.leader] =
//            Integer.parseInt(statsScreen!!.stats_assist_leader.getText().toString())
//        character.assistCount[screen.guard] =
//            Integer.parseInt(statsScreen!!.stats_assist_guardian.getText().toString())
//        character.assistCount[screen.droid] =
//            Integer.parseInt(statsScreen!!.stats_assist_droid.getText().toString())
//        character.assistCount[screen.scum] =
//            Integer.parseInt(statsScreen!!.stats_assist_scum.getText().toString())
//        character.assistCount[screen.trooper] =
//            Integer.parseInt(statsScreen!!.stats_assist_trooper.getText().toString())
//
//        character.timesWounded =
//            Integer.parseInt(statsScreen!!.stats_wounded.getText().toString())
//        setTotalKills()
//    }
//
//    fun onTapStat(v: TextView) {
//        statsScreen!!.edit_stat.visibility = View.VISIBLE
//        currentStatEditing = v
//        statsScreen!!.stat_text.setText(v.text.toString())
//
//    }
//
//    fun setTotalKills() {
//        var totalKills = 0
//        var totalAssists = 0
//        for (i in 0..character.killCount.size - 1) {
//            totalKills += character.killCount[i]
//            totalAssists += character.assistCount[i]
//        }
//        statsScreen!!.stats_kill_total.setText("" + totalKills)
//        statsScreen!!.stats_assist_total.setText("" + totalAssists)
//        if (character.timesWounded > 0) {
//            statsScreen!!.stats_kill_death_ratio.setText("" + Math.round(10*totalKills.toFloat()
//                    / character.timesWounded).toFloat()/10f)
//        } else {
//            statsScreen!!.stats_kill_death_ratio.setText("-")
//        }
//    }
//
//}