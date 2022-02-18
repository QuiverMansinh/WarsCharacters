package com.glasswellapps.iact.imperial

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.glasswellapps.iact.Characters_list
import com.glasswellapps.iact.LoadScreen
import com.glasswellapps.iact.R
import com.glasswellapps.iact.effects.Sounds
import com.glasswellapps.iact.effects.Sounds.buttonSound
import kotlinx.android.synthetic.main.dialog_imperial_add_player.*

class ImperialPlayerAdder(val imperialScreen: ImperialScreen) {
    private var playerNumber: Int = 0
    private val addPlayerDialog: Dialog = Dialog(imperialScreen)
    init {
        addPlayerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        addPlayerDialog.setCancelable(false)
        addPlayerDialog.setContentView(R.layout.dialog_imperial_add_player)
        addPlayerDialog.setCanceledOnTouchOutside(true)
        addPlayerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addPlayerDialog.imperial_new.setOnClickListener { onNewPlayer() }
        addPlayerDialog.imperial_load.setOnClickListener { onLoadPlayer() }
    }

    fun showDialog(playerNumber : Int){
        Sounds.buttonSound()
        this.playerNumber = playerNumber
        addPlayerDialog.show()
    }
    fun onNewPlayer(){
        Sounds.selectSound()
        val intent = Intent(imperialScreen, Characters_list::class.java)
        intent.putExtra("from","imperial")
        addPlayerDialog.dismiss()
        imperialScreen.isAddingPlayer = true
        imperialScreen.startActivity(intent)
    }
    fun onLoadPlayer(){
        Sounds.selectSound()
        val intent = Intent(imperialScreen, LoadScreen::class.java)
        intent.putExtra("from","imperial")
        addPlayerDialog.dismiss()
        imperialScreen.isAddingPlayer=true
        imperialScreen.startActivity(intent)
    }
}