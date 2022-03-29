package com.glasswellapps.iact;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;
import android.view.View;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BitmapUtils {

    public static Bitmap getBitmap(Context context, String path) {
        return getBitmap(context, path, null, null);
    }
    public static Bitmap getBitmap(Context context, String path, Bitmap oldBitmap) {
        return getBitmap(context, path, null, oldBitmap);
    }
    public static Bitmap getBitmap(Context context, String path, View view) {
        return getBitmap(context, path, view,null);
    }

    public static Bitmap getBitmap(Context context, String path, View view, Bitmap oldBitmap) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inMutable = true;

        if(oldBitmap!=null)options.inBitmap = oldBitmap;
        if(view!=null) options.inSampleSize = getSampleSize(options, view);

        AssetManager assetManager = context.getAssets();
        for(int i = 0; i < 32; i++){
            try {
                inputStream = assetManager.open(path);
                bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                break;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                //println("next size"+i)
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        try {
            inputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return bitmap;
    }


    public static int getSampleSize(BitmapFactory.Options options, View view){
        int width = view.getWidth();
        int height = view.getHeight();
        int sampleSize = 1;

        if(options.outHeight > height || options.outWidth > width){
            int halfHeight = options.outHeight/2;
            int halfWidth = options.outWidth/2;
            while ((halfHeight/sampleSize) >= height && (halfWidth/sampleSize) >= width ){
                sampleSize *= 2;
            }
        }

        return sampleSize;
    }


}
