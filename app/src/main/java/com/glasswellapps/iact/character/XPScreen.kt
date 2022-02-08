package com.glasswellapps.iact.character
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import com.glasswellapps.iact.R
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.dialog_show_card.*
import kotlinx.android.synthetic.main.screen_xp_select.*
import kotlinx.android.synthetic.main.toast_no_actions_left.view.*

class XPScreen (var characterScreen: CharacterScreen){
    private var xpCardImages = arrayListOf<ImageView>()
    private var xpSelectScreen: Dialog= Dialog(characterScreen, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    var character = characterScreen.character
    private var showCardDialog = characterScreen.showCardDialog

    init {
        xpSelectScreen
        xpSelectScreen.requestWindowFeature(Window.FEATURE_NO_TITLE)
        xpSelectScreen.setCancelable(false)
        xpSelectScreen.setContentView(R.layout.screen_xp_select)
        xpSelectScreen.setCanceledOnTouchOutside(true)
        xpSelectScreen.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        xpSelectScreen.XPCard1.setOnClickListener { onXPCard(0); }
        xpSelectScreen.XPCard2.setOnClickListener { onXPCard(1); }
        xpSelectScreen.XPCard3.setOnClickListener { onXPCard(2); }
        xpSelectScreen.XPCard4.setOnClickListener { onXPCard(3); }
        xpSelectScreen.XPCard5.setOnClickListener { onXPCard(4); }
        xpSelectScreen.XPCard6.setOnClickListener { onXPCard(5); }
        xpSelectScreen.XPCard7.setOnClickListener { onXPCard(6); }
        xpSelectScreen.XPCard8.setOnClickListener { onXPCard(7); }
        xpSelectScreen.XPCard9.setOnClickListener { onXPCard(8); }

        xpCardImages.add(xpSelectScreen.XPCard1)
        xpCardImages.add(xpSelectScreen.XPCard2)
        xpCardImages.add(xpSelectScreen.XPCard3)
        xpCardImages.add(xpSelectScreen.XPCard4)
        xpCardImages.add(xpSelectScreen.XPCard5)
        xpCardImages.add(xpSelectScreen.XPCard6)
        xpCardImages.add(xpSelectScreen.XPCard7)
        xpCardImages.add(xpSelectScreen.XPCard8)
        xpCardImages.add(xpSelectScreen.XPCard9)

        for (i in 0..8) {
            if (character.xpCardsEquipped[i]) {
                xpCardImages[i].alpha = 1f
            } else {
                xpCardImages[i].alpha = 0.5f
            }
            if (character.xpCardImages.size > i) {
                xpCardImages[i].setImageBitmap(character.xpCardImages[i])
            }
            xpCardImages[i].setOnLongClickListener {
                onShowXPCard(xpCardImages[i])
                true
            }
        }
        var xpLeft = character.totalXP - character.xpSpent
        xpSelectScreen.xp_text.text = "XP: " + xpLeft

        xpSelectScreen.addXP.setOnClickListener { addXP(); }
        xpSelectScreen.minusXP.setOnClickListener { minusXP(); }
    }

    fun onShowXPCard(view: ImageView) {
        var image = ((view).drawable as BitmapDrawable).bitmap
        showCardDialog!!.card_image.setImageBitmap(image)
        showCardDialog!!.show()
    }

    fun onXPCard(cardNo:Int) {
        var xpLeft = character.totalXP - character.xpSpent

        if (character.xpCardsEquipped[cardNo]) {
            Sounds.selectSound()
            character.unequipXP(cardNo)

            xpCardImages[cardNo].animate().alpha(0.5f).duration = 50

        } else if (character.xpScores[cardNo] <= xpLeft) {
            character.equipXP(cardNo,characterScreen)
            if(character.xpCardsEquipped[cardNo]) {
                xpCardImages[cardNo].animate().alpha(1f).duration = 50
                Sounds.selectSound()
            }
        }
        else{
            showNotEnoughXP()
        }
        xpLeft = character.totalXP - character.xpSpent
        xpSelectScreen.xp_text.text = "XP: " + xpLeft
        character.rewardObtained = character.xpCardsEquipped[8]

        if (character.currentImage != null) {
            //character.currentImage.recycle()
        }

        characterScreen.updateImages()
        characterScreen.updateStats()

    }

    private fun showNotEnoughXP() {
        Sounds.negativeSound()
        val toast = Toast( characterScreen)
        toast.duration = Toast.LENGTH_SHORT
        val view = characterScreen.layoutInflater.inflate(
            R.layout.toast_no_actions_left,
            null,
            false
        )
        view.toast_text.text = "NOT ENOUGH XP"
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun addXP() {
        Sounds.selectSound()
        character.totalXP++
        var xpLeft = character.totalXP - character.xpSpent
        xpSelectScreen.xp_text.text = "XP: " + xpLeft
    }

    fun minusXP() {
        var xpLeft = character.totalXP - character.xpSpent
        if (xpLeft > 0) {
            Sounds.selectSound()
            character.totalXP--
        }
        else{
            Sounds.negativeSound()
        }
        xpLeft = character.totalXP - character.xpSpent
        xpSelectScreen.xp_text.text = "XP: " + xpLeft
    }

    fun showDialog() {
        xpSelectScreen.show()
    }
}