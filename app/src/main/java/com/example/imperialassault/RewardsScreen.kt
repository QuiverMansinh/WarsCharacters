package com.example.imperialassault

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.widget.GridView
import android.widget.ImageView
import kotlinx.android.synthetic.main.dialog_show_card.*

class RewardsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards_screen)


        val rewardsgrid = this.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this, Items.rewardsArray!!)
        rewardsgrid.setFriction(ViewConfiguration.getScrollFriction()/10)

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
    }


    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}