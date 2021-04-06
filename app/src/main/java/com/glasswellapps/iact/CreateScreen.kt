package com.glasswellapps.iact
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_screen.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import android.graphics.BitmapFactory as GraphicsBitmapFactory


class CreateScreen : AppCompatActivity() {

    var folder:File? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_screen)
        folder = File(filesDir,"/CustomIACharacter")
        var success = true
        if(!folder!!.exists()){
            Toast.makeText(getApplication(),"Directory does not exist", Toast.LENGTH_LONG).show();
            success = folder!!.mkdirs()
            if(success){
                Toast.makeText(getApplication(),"Directory created", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplication(),"Failed to create Directory", Toast.LENGTH_LONG).show();
            }
        }

        loadImage()
    }
    val PICKFILE_RESULT_CODE = 8778

    fun onPickImage(view: View) {
        var chooseImage = Intent(Intent.ACTION_GET_CONTENT)
        chooseImage.setType("image/*")
        chooseImage = Intent.createChooser(chooseImage,"Choose Image")
        startActivityForResult(chooseImage, PICKFILE_RESULT_CODE)
    }

    fun loadImage(){
        val file = File(filesDir, "/CustomIACharacter/tier0image")
        if(file.exists()) {
            var inputStream:FileInputStream? = null
            try{
                inputStream = FileInputStream(file)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
            var bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
            imagePicked.setImageBitmap(bitmap)
        }
        else{
            Toast.makeText(getApplication(),"tier0 image not found", Toast.LENGTH_LONG).show();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            PICKFILE_RESULT_CODE -> {
                if(resultCode == -1){
                    var imageUri = data!!.data!!;
                    var image = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    println("image "+imageUri+""+image)

                    var file = File(filesDir, "/CustomIACharacter/tier0image")
                    if(file.exists()){
                        file.delete()
                    }
                    try{
                        val out = FileOutputStream(file)
                        image.compress(Bitmap.CompressFormat.PNG,100,out)
                        out.flush()
                        out.close()
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }

                    loadImage()
                }
            }
        }
    }


}