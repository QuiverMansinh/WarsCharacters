package com.example.imperialassault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Character_view : AppCompatActivity() {
    var character:Character = Character();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character__view)

        var load = intent.getBooleanExtra("Load",false)
        var characterName : String = intent.getStringExtra("CharacterName").toString()

        if(!load) {
            when (characterName) {
                "Mak" -> character = Character_mak();
            }
        }
        else{

        }
    }
}