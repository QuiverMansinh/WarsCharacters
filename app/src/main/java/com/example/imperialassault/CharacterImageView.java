package com.example.imperialassault;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CharacterImageView extends View implements Runnable{
    Thread thread;
    public boolean animating = true;
    Bitmap image, glowImage, camoImage;
    boolean scaled = false;
    boolean focused = false;
    boolean hidden = false;
    boolean bleeding = false;
    boolean stunned = false;
    boolean weakened = false;

    public CharacterImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public CharacterImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CharacterImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){


        thread = new Thread(this::run);
        thread.start();
        //focused = true;
        //stunned = true;
        //weakened = true;
        hidden = true;
        stunPaint.setAlpha(75);
    }

    public void setImageBitmap(Bitmap bitmap){
        image = bitmap;
        scaled = false;
    }
    public void setGlowBitmap(Bitmap bitmap){
        System.out.println(bitmap);
        glowImage = bitmap;
        scaled = false;
    }
    public void setCamoBitmap(Bitmap bitmap){
        System.out.println(bitmap);
       camoImage = bitmap;
        scaled = false;
    }

    float time = 0;
    Paint paint = new Paint();
    Paint stunPaint = new Paint();
    Paint focusedPaint = new Paint();
    float stunX, stunY;


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!scaled){
            if(image!=null) {
                image = Bitmap.createScaledBitmap(image, canvas.getWidth(),  canvas.getHeight(), false);
            }
            if(glowImage!=null) {
                glowImage = Bitmap.createScaledBitmap(glowImage,  canvas.getWidth(),  canvas.getHeight(), false);
            }
            if(camoImage!=null) {
                camoImage = Bitmap.createScaledBitmap(camoImage,  canvas.getWidth(), canvas.getHeight(), false);
            }
            scaled = true;

        }
        if(focused && glowImage!=null){
            canvas.drawBitmap(glowImage, 0, 0, focusedPaint);
        }

        canvas.drawBitmap(image,0,0, paint);

        if(stunned) {
            canvas.drawBitmap(image,
                    stunX,
                    stunY
                    , stunPaint);
        }
        if(hidden && camoImage!=null){
            canvas.drawBitmap(camoImage, 0, 0, null);
        }
        if(bleeding){

        }
    }

    void update(int deltaTime){
        if(scaled) {
            if (stunned) {
                stunX = (float) Math.cos(time / 1000 * 4) * image.getWidth() / 20;
                stunY = (float) Math.sin(time / 1000 * 4) * image.getWidth() / 40;
            }

            if (weakened) {
                int weakenedColor = Color.rgb(255,
                        (int) (255 * (1 + Math.cos(time / 1000 * 4)) / 2),
                        (int) (255 * (1 + Math.cos(time / 1000 * 4) / 4) / 1.5));
                paint.setColorFilter(new LightingColorFilter(weakenedColor, 0));

            }
            if (focused && glowImage != null) {
                int alpha = (int) (255 * (1 + Math.cos(time / 1000 * 4)) / 2);
                focusedPaint.setAlpha(alpha);
            }
            if (hidden) {

            }
        }
    }

    int fixedDeltaTime = 1000/60;
    @Override
    public void run() {
        while(animating) {
            time+=fixedDeltaTime;
            //System.out.println("tick");
            update(fixedDeltaTime);
            float dt = System.currentTimeMillis();
            invalidate();
            dt = System.currentTimeMillis()-dt;
            int lag = (int)(fixedDeltaTime-dt);
            if(lag > 0){
                try{
                    thread.sleep(lag);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            while(lag<0){
                lag+=fixedDeltaTime;
                update(fixedDeltaTime);
            }

            try{
                thread.sleep(fixedDeltaTime);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
