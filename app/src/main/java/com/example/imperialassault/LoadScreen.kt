package com.example.imperialassault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_load_screen.*
import kotlinx.android.synthetic.main.button_save_file.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)
        val database = AppDatabase.getInstance(this)
        GlobalScope.launch {
            val data = database!!.getCharacterDAO().getAll()
            println(data)
            for (i in 0..data.size - 1) {
                val saveFile = layoutInflater.inflate(R.layout.button_save_file, null)
                saveFile.save_file_name.setText("" + data[i].fileName)
                save_file_list.addView(saveFile)
            }
        }
    }
}