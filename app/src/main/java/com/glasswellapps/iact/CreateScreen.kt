package com.glasswellapps.iact

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.glasswellapps.iact.R
import kotlinx.android.synthetic.main.activity_create_screen.*
import java.io.InputStream
import java.net.URL

class CreateScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_screen)
    }
    val PICKFILE_RESULT_CODE = 8778

    fun onLoadImage(view: View) {
        var chooseImage = Intent(Intent.ACTION_GET_CONTENT)
        chooseImage.setType("image/*")
        chooseImage = Intent.createChooser(chooseImage,"Choose Image")
        startActivityForResult(chooseImage, PICKFILE_RESULT_CODE)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            PICKFILE_RESULT_CODE -> {
                if(resultCode == -1){
                    var imageUri = data!!.data!!;
                    var image = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    println("image "+imageUri+""+image)
                    imagePicked.setImageBitmap(image)
                }
            }
        }
    }


}