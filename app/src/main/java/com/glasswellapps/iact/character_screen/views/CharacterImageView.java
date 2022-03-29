package com.glasswellapps.iact.character_screen.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.glasswellapps.iact.ShortToast;
import com.glasswellapps.iact.effects.Sounds;

public class CharacterImageView extends View implements Runnable{
    Thread thread;

    public Bitmap image, glowImage,layer1, layer2, layerLightSaber;

    public boolean imageScaled = false;
    public boolean glowScaled = false;
    public boolean layer1Scaled = false;
    public boolean layer2Scaled = false;
    public boolean lightSaberScaled = false;

    public boolean focused = false;
    public boolean hidden = false;
    public boolean bleeding = false;
    public boolean stunned = false;
    public boolean weakened = false;

    public boolean animateConditions = true;

    private Activity context;
    public CharacterImageView(Activity context) {
        super(context);
        this.context = context;
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
        stunPaint.setAlpha(75);
        turnOffLightSaber();
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

    public void setLightSaberBitmap(Bitmap bitmap){
        layerLightSaber = bitmap;
        lightSaberScaled = false;

    }

    public boolean turnOnLightSaber(int delay){
        System.out.println("LIGHTSABER: "+
                lightSaberOn + " " + lightSaberAlpha + " " + layerLightSaber);
        if(!lightSaberOn) {
            lightSaberOn = true;
            lightSaberAlpha = 1;
            lightSaberDelay = delay;
            update();
            postInvalidate();
            return layerLightSaber!=null;
        }

        return false;
    }
    public void turnOffLightSaber(){
        System.out.println("LIGHTSABER OFF ");
        lightSaberAlpha=1;
        lightSaberPaint.setAlpha(0);
        lightSaberOn = false;
    }

    private float time = 0;
    private Paint paint = new Paint();
    private Paint stunPaint = new Paint();
    private Paint focusedPaint = new Paint();
    private Paint lightSaberPaint = new Paint();
    private boolean lightSaberOn = false;
    private float lightSaberAlpha = 1;
    private float lightSaberDelay = 0;
    private float stunX, stunY;
    private float moveY;
    private int imageHeight = 0;
    private int offsetY = 0;


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(image == null){
            return;
        }
        if(image.isRecycled() || glowImage.isRecycled() ){
            return;
        }
        if(image!=null) {
            if (!imageScaled) {
                if (image != null) {
                    imageHeight =(int)((float)image.getHeight()/image.getWidth()*getWidth());

                    image = Bitmap.createScaledBitmap(image, getWidth(), imageHeight, false);
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

            if (!lightSaberScaled) {
                if (layerLightSaber != null) {
                    layerLightSaber = Bitmap.createScaledBitmap(layerLightSaber, getWidth(),
                            imageHeight,
                            true);
                }
                lightSaberScaled = true;
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

            if(layerLightSaber != null){
                if (animateConditions) {
                    canvas.drawBitmap(layerLightSaber, 0, moveY+offsetY, lightSaberPaint);
                } else {
                    canvas.drawBitmap(layerLightSaber, 0, offsetY, lightSaberPaint);
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


    void update(){
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

        if(lightSaberOn && lightSaberAlpha < 255 && layerLightSaber!=null){
            if(lightSaberDelay > 0) {
                lightSaberDelay-=fixedDeltaTime;
            }
            else {
                if(lightSaberAlpha == 1){
                    Sounds.INSTANCE.play(Sounds.INSTANCE.getLightSaber());
                    Sounds.INSTANCE.play(Sounds.INSTANCE.getLightsaber_hum());
                }
                lightSaberAlpha *= 5f;
                lightSaberAlpha = Math.min(lightSaberAlpha, 255);
                lightSaberPaint.setAlpha((int) lightSaberAlpha);

            }
        }
    }

    int fixedDeltaTime = 1000/60;

    boolean isRunning = true;
    @Override
    public void run() {
        while(isRunning) {
            time+=fixedDeltaTime;
            //System.out.println("tick");
            update();
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
                update();
            }

            try{
                thread.sleep(fixedDeltaTime);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onStop() throws InterruptedException {
        isRunning = false;
        thread.join();
        //thread.interrupt();
    }
}
