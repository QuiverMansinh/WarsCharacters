package com.example.imperialassault

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_show_card.*

class RangedScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_screen)

        var title = findViewById<TextView>(R.id.item_title)
        title.setText("RANGED WEAPONS")

        findViewById<ImageView>(R.id.to_acc).alpha = 0.5f
        findViewById<ImageView>(R.id.to_armor).alpha = 0.5f
        findViewById<ImageView>(R.id.to_melee).alpha = 0.5f
        findViewById<ImageView>(R.id.to_ranged).alpha = 1f

        val rewardsgrid = this.findViewById<ImageView>(R.id.rewards_grid) as GridView
        rewardsgrid.adapter = ImageAdapter(this, Items.rangedArray!!)
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


    fun onToAcc(view:View){
        val intent = Intent(this, AccScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun onToArmor(view:View){
        val intent = Intent(this, ArmorScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun onToMelee(view:View){
        val intent = Intent(this, MeleeScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun onToRanged(view:View){
        //val intent = Intent(this, RangedScreen::class.java)
        //startActivity(intent)
    }


    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}