package com.example.imperialassault

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class Characters_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    fun onSelectMak(view: View) {
        val intent = Intent(this,Character_view::class.java)
        intent.putExtra("CharacterName","mak")
        //intent.putExtra("Load",false)
        startActivity(intent);
    }
}