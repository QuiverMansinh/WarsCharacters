//package com.example.imperialassault
//
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.ColorDrawable
//import android.view.Gravity
//import android.view.View
//import android.view.Window
//import android.widget.ImageView
//import android.widget.Toast
//import kotlinx.android.synthetic.main.dialog_show_card.*
//import kotlinx.android.synthetic.main.screen_xp_select.*
//import kotlinx.android.synthetic.main.toast_no_actions_left.view.*
//
//class CharacterXPScreen(val screen: CharacterScreen) {
//    //endregion
//    //************************************************************************************************************
//    //region XP Screen
//    //************************************************************************************************************
//    var xpCardImages = arrayListOf<ImageView>()
//    var xpScreen:Dialog?=null
//    val character = screen.character
//    init{
//
//        initXpSelectScreenDialog()
//    }
//    private fun initXpSelectScreenDialog() {
//        xpScreen = Dialog(screen, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
//        xpScreen!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        xpScreen!!.setCancelable(false)
//        xpScreen!!.setContentView(R.layout.screen_xp_select)
//        xpScreen!!.setCanceledOnTouchOutside(true)
//        xpScreen!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        screen.xpSelectScreen=xpScreen
//    }
//
//    fun initXPScreen() {
//        xpCardImages.add(xpScreen!!.XPCard1)
//        xpCardImages.add(xpScreen!!.XPCard2)
//        xpCardImages.add(xpScreen!!.XPCard3)
//        xpCardImages.add(xpScreen!!.XPCard4)
//        xpCardImages.add(xpScreen!!.XPCard5)
//        xpCardImages.add(xpScreen!!.XPCard6)
//        xpCardImages.add(xpScreen!!.XPCard7)
//        xpCardImages.add(xpScreen!!.XPCard8)
//        xpCardImages.add(xpScreen!!.XPCard9)
//
//        for (i in 0..character.xpCardImages.size - 1) {
//            xpCardImages[i].setImageBitmap(character.xpCardImages[i])
//            xpCardImages[i].setOnLongClickListener {
//                onShowXPCard(xpCardImages[i])
//                true
//            }
//            if (character.xpCardsEquipped[i]) {
//                xpCardImages[i].alpha = 1f
//            } else {
//                xpCardImages[i].alpha = 0.5f
//            }
//            xpCardImages[i].setTag(i)
//        }
//        var xpLeft = character.totalXP - character.xpSpent
//        xpScreen!!.xp_text.setText("XP: " + xpLeft)
//    }
//
//    fun onShowXPCard(view: ImageView) {
//        var image = ((view).drawable as BitmapDrawable).bitmap
//        showCardDialog!!.card_image.setImageBitmap(image)
//        showCardDialog!!.show()
//    }
//
//    fun onXPCard(view: View) {
//        var xpLeft = character.totalXP - character.xpSpent;
//        var cardNo = view.tag as Int
//        if (character.xpCardsEquipped[cardNo]) {
//            Sounds.selectSound()
//            character.unequipXP(cardNo)
//
//            xpCardImages[cardNo].animate().alpha(0.5f).duration = 50
//
//        } else if (character.xpScores[cardNo] <= xpLeft) {
//            character.equipXP(cardNo,screen)
//            if(character.xpCardsEquipped[cardNo]) {
//                xpCardImages[cardNo].animate().alpha(1f).duration = 50
//                Sounds.selectSound()
//            }
//        }
//        else{
//            showNotEnoughXP()
//        }
//        xpLeft = character.totalXP - character.xpSpent
//        xpScreen!!.xp_text.setText("XP: " + xpLeft)
//        character.rewardObtained = character.xpCardsEquipped[8]
//
//        if (character.currentImage != null) {
//            //character.currentImage!!.recycle()
//        }
//
//        screen.updateImages()
//        screen.updateStats()
//
//    }
//
//    private fun showNotEnoughXP() {
//        Sounds.negativeSound()
//        val toast = Toast(screen)
//        toast!!.duration = Toast.LENGTH_SHORT
//        val view = screen.layoutInflater.inflate(
//            R.layout.toast_no_actions_left,
//            null,
//            false
//        )
//        view.toast_text.setText("NOT ENOUGH XP")
//
//
//        toast!!.view = view
//        toast!!.setGravity(Gravity.CENTER, 0, 0)
//        toast!!.show()
//    }
//
//    fun addXP(view: View) {
//        Sounds.selectSound()
//        character.totalXP++
//        var xpLeft = character.totalXP - character.xpSpent;
//        xpScreen!!.xp_text.setText("XP: " + xpLeft)
//    }
//
//    fun minusXP(view: View) {
//        var xpLeft = character.totalXP - character.xpSpent;
//        if (xpLeft > 0) {
//            Sounds.selectSound()
//            character.totalXP--
//        }
//        else{
//            Sounds.negativeSound()
//        }
//        xpLeft = character.totalXP - character.xpSpent;
//        xpScreen!!.xp_text.setText("XP: " + xpLeft)
//    }
//}