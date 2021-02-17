package com.example.imperialassault

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageView

class RewardsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards_screen)

        val rewardsgrid = this.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this, Items.rewardsArray!!)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}