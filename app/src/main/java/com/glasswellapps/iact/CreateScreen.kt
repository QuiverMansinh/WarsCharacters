package com.example.imperialassault

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
                    var imagePath = data!!.data!!.path;
                    var image = getBitmap(this, imagePath!!)
                    println("image "+imagePath+""+image)
                    imagePicked.setImageBitmap(image)
                }
            }
        }
    }

    open fun getBitmap(context: Context, path: String): Bitmap? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        for(i in 1..32) {
            try {
                var url = URL(path)
                inputStream = url.openStream()
                options.inSampleSize = i
                bitmap = BitmapFactory.decodeStream(inputStream,null,options)
                break
            } catch (outOfMemoryError:OutOfMemoryError) {

                println("next size"+i)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}