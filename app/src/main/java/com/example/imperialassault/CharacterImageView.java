package com.example.imperialassault;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.RenderScript;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CharacterImageView extends View implements Runnable{
    Thread thread;
    public boolean animating = true;
    Bitmap image, glowImage, layer1, layer2;

    boolean imageScaled = false;
    boolean glowScaled = false;
    boolean layer1Scaled = false;
    boolean layer2Scaled = false;

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
        imageScaled = false;
    }
    public void setGlowBitmap(Bitmap bitmap){
        //System.out.println(bitmap);
        glowImage = bitmap;
        glowScaled = false;
        focusedPaint.setColorFilter(new LightingColorFilter(0,Color.rgb(100,255,100)));
    }
    public void setLayer1Bitmap(Bitmap bitmap){
        layer1 = bitmap;
        layer1Scaled = false;
    }


    float time = 0;
    Paint paint = new Paint();
    Paint stunPaint = new Paint();
    Paint focusedPaint = new Paint();
    float stunX, stunY;
    float offsetY;

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!imageScaled) {
            if (image != null) {
                image = Bitmap.createScaledBitmap(image, canvas.getWidth(), canvas.getHeight(),
                        true);
            }
            imageScaled = true;
        }
        if(!glowScaled) {
            if(glowImage!=null) {
                glowImage = Bitmap.createScaledBitmap(glowImage,  canvas.getWidth(), canvas.getHeight(), true);
            }
            glowScaled = true;
        }
        if(!layer1Scaled) {
            if(layer1!=null) {
                layer1 = Bitmap.createScaledBitmap(layer1,  canvas.getWidth(), canvas.getHeight(), true);
            }
            layer1Scaled = true;
        }

        if(focused && glowImage!=null){
            canvas.drawBitmap(glowImage, 0,  offsetY, focusedPaint);
        }

        canvas.drawBitmap(image,0, offsetY, paint);
        if(layer1!=null) {
            canvas.drawBitmap(layer1, 0, offsetY, paint);
        }


        if(stunned) {
            canvas.drawBitmap(image, stunX, stunY, stunPaint);
            if(layer1!=null) {
                canvas.drawBitmap(layer1, stunX, stunY, stunPaint);
            }
        }

        if(bleeding){

        }
    }


    void update(int deltaTime){
        if(imageScaled&&glowScaled) {
            if (stunned) {
                stunX = (float) Math.cos(time / 1000 * 4) * image.getWidth() / 20;
                stunY = (float) Math.sin(time / 1000 * 4) * image.getWidth() / 40;
            }

            if (weakened) {
                offsetY =
                        -(float)(Math.max(Math.min(Math.sin(time / 1000 * 6+Math.PI),0.8),-0.8))/2*image.getHeight()/200;
                int weakenedColor = Color.rgb(
                        (int) (55 * (1 + Math.sin(time / 1000 * 6+Math.PI)) / 2 + 200),
                        (int) (55 * (1 + Math.sin(time / 1000 * 6+Math.PI)) / 2 + 200),
                        255);
                paint.setColorFilter(new LightingColorFilter(weakenedColor, 0));
            }
            else{
                paint.setColorFilter(null);
            }


            if (focused && glowImage != null) {


                int alpha = (int) (255 * (1 + Math.sin(time / 1000 * 6)) / 2);
                focusedPaint.setAlpha(alpha);
            }
            if (hidden) {

            }
            if(bleeding){
                int bleedingColor = Color.rgb(255,
                        (int) (100 * (1 + Math.sin(time / 1000 * 6)) / 2 + 155),
                        (int) (100 * (1 + Math.sin(time / 1000 * 6)) / 2 + 155));
                paint.setColorFilter(new LightingColorFilter(bleedingColor, 0));

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
            postInvalidate();
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
