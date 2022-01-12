package com.glasswellapps.iact

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

import com.glasswellapps.iact.characters.Character
import com.glasswellapps.iact.database.CharacterData
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Sounds.reset(this)

        newButton.setOnClickListener {
            Sounds.selectSound()
            val intent = Intent(this, Characters_list::class.java)
                startActivity(intent)
        }
        loadButton.setOnClickListener {
            Sounds.selectSound()
            val intent = Intent(this, LoadScreen::class.java)
                startActivity(intent)
        }

        createButton.setOnClickListener {
            Sounds.selectSound()
            val intent = Intent(this, CreateScreen::class.java)
            startActivity(intent)
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}