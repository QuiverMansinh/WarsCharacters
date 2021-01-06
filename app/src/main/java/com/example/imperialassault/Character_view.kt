package com.example.imperialassault

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_character__view.*

class Character_view : AppCompatActivity() {
    var character:Character = Character();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character__view)

        var load = intent.getBooleanExtra("Load",false)
        var characterName : String = intent.getStringExtra("CharacterName").toString()

        if(!load) {
            when (characterName) {
                "Mak" -> {character = Character_mak(this)
                         character_image.setImageBitmap(character.tierImages.first())}

            }
        }
        else{

        }
    }


}