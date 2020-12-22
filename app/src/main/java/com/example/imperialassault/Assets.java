package com.example.imperialassault;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.ScriptGroup;

import java.io.InputStream;
import java.util.ArrayList;

public class Assets {
    public static Assets instance;

    public Assets(){
        instance = this;
    }
    //tupe: 0=tier, 1=reward,2=card,3=xpCards
    public ArrayList<Bitmap> getCharacterImage(Context context, String name, int type){
        switch (type) {
            case 0: getTierImages(context, name);
            /*
            case 1: getRewardImages(context, name);
            case 2: getCardImages(context, name);
            case 3: getXPCardImages(context, name);
            */

        }
        return null;
    }

    public ArrayList<Bitmap> getTierImages(Context context, String name) {
        ArrayList<Bitmap> images = new ArrayList<Bitmap>();

        for(int i = 0; i < 5; i++) {
            Bitmap image = getBitmap(context, "characters/"+name+"/tier/tier" + i + ".png");
            if(image != null) {
                images.add(image);
            }
        }
        return images;
    }


    private Bitmap getBitmap(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try{
            inputStream = assetManager.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
