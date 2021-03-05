package com.example.imperialassault;

import android.content.Context;
import android.graphics.Bitmap;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;


import android.util.AttributeSet;
import android.view.View;

public class CharacterImageView extends View implements Runnable{
    Thread thread;

    Bitmap image, glowImage,layer1, layer2;

    boolean imageScaled = false;
    boolean glowScaled = false;
    boolean layer1Scaled = false;
    boolean layer2Scaled = false;

    boolean focused = false;
    boolean hidden = false;
    boolean bleeding = false;
    boolean stunned = false;
    boolean weakened = false;

    boolean animateConditions = true;

    public CharacterImageView( Context context) {
        super(context);
        init();
    }

    public CharacterImageView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CharacterImageView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){


        thread = new Thread(this::run);
        thread.start();
        //focused = true;
        //stunned = true;
        //weakened = true;
        //hidden = true;
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

    public void setLayer2Bitmap(Bitmap bitmap){
        layer2 = bitmap;
        layer2Scaled = false;
    }


    float time = 0;
    Paint paint = new Paint();
    Paint stunPaint = new Paint();
    Paint focusedPaint = new Paint();
    float stunX, stunY;
    float moveY;
    int imageHeight = 0;
    int offsetY = 0;


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(image!=null) {
            if (!imageScaled) {
                if (image != null) {
                    imageHeight =(int)((float)image.getHeight()/image.getWidth()*getWidth());
                    image = Bitmap.createScaledBitmap(image, getWidth(), imageHeight, true);
                    offsetY = Math.max(getHeight()-imageHeight,0);
                }
                imageScaled = true;
            }
            if (!glowScaled) {
                if (glowImage != null) {
                    glowImage = Bitmap.createScaledBitmap(glowImage, getWidth(),
                            imageHeight, true);
                }
                glowScaled = true;
            }


            if (!layer1Scaled) {
                if (layer1 != null) {
                    layer1 = Bitmap.createScaledBitmap(layer1, getWidth(),
                            imageHeight,
                            true);
                }
                layer1Scaled = true;
            }

            if (!layer2Scaled) {
                if (layer2 != null) {
                    layer2 = Bitmap.createScaledBitmap(layer2, getWidth(),
                            imageHeight,
                            true);

                }
                layer2Scaled = true;
            }

            if (focused && glowImage != null && animateConditions) {
                canvas.drawBitmap(glowImage, 0, moveY +offsetY, focusedPaint);
            }
            if (animateConditions) {
                canvas.drawBitmap(image, 0, moveY +offsetY, paint);
            } else {
                canvas.drawBitmap(image, 0, offsetY, null);
            }

            if (layer2 != null) {
                if (animateConditions) {
                    canvas.drawBitmap(layer2, 0, moveY +offsetY, paint);
                } else {
                    canvas.drawBitmap(layer2, 0, offsetY, null);
                }
            }

            if (layer1 != null) {
                if (animateConditions) {
                    canvas.drawBitmap(layer1, 0, moveY +offsetY, paint);
                } else {
                    canvas.drawBitmap(layer1, 0, offsetY, null);
                }
            }


            if (stunned && animateConditions) {
                canvas.drawBitmap(image, stunX, stunY+offsetY, stunPaint);
                if (layer1 != null) {
                    canvas.drawBitmap(layer1, stunX, stunY+offsetY, stunPaint);
                }
                if (layer2 != null) {
                    canvas.drawBitmap(layer2, stunX, stunY+offsetY, stunPaint);
                }
            }

            if (bleeding && animateConditions) {

            }
        }
    }


    void update(int deltaTime){
        if(imageScaled&&glowScaled) {
            if(animateConditions) {
                if (stunned) {
                    stunX = (float) Math.cos(time / 1000 * 4) * image.getWidth() / 20;
                    stunY = (float) Math.sin(time / 1000 * 4) * image.getWidth() / 40;
                }

                if (weakened) {
                    moveY =
                            -(float) (Math.max(Math.min(Math.sin(time / 1000 * 6 + Math.PI), 0.8), -0.8)) / 2 * image.getHeight() / 200;
                    int weakenedColor = Color.rgb(
                            (int) (55 * (1 + Math.sin(time / 1000 * 6 + Math.PI)) / 2 + 200),
                            (int) (55 * (1 + Math.sin(time / 1000 * 6 + Math.PI)) / 2 + 200),
                            255);
                    paint.setColorFilter(new LightingColorFilter(weakenedColor, 0));
                }


                if (focused && glowImage != null) {
                    int alpha = (int) (255 * (1 + Math.sin(time / 1000 * 6)) / 2);
                    focusedPaint.setAlpha(alpha);
                }

                if (hidden) {

                }

                if (bleeding) {
                    int bleedingColor = Color.rgb(255,
                            (int) (100 * (1 + Math.sin(time / 1000 * 6)) / 2 + 155),
                            (int) (100 * (1 + Math.sin(time / 1000 * 6)) / 2 + 155));
                    paint.setColorFilter(new LightingColorFilter(bleedingColor, 0));
                }

                if(!bleeding && !weakened){
                    paint.setColorFilter(new LightingColorFilter(Color.WHITE, 0));
                }
            }
        }
    }

    int fixedDeltaTime = 1000/60;

    @Override
    public void run() {
        while(true) {
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
