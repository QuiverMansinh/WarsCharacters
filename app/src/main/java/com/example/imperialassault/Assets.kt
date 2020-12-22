package com.example.imperialassault

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.lang.Exception


class Assets {
    constructor(){

    }

    companion object{
        val instance = Assets()
    }


}

//getCharacterImages
//getWeaponImages
//getCardImages

fun getBitmap(context: Context, path : String) : Bitmap? {

    try {
        return context.assets.open(path).use { BitmapFactory.decodeStream(it) }
    }
    catch(e: Exception){
        e.printStackTrace()
    }
    return null
}