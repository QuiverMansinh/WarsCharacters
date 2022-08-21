package com.glasswellapps.iact.character_screen.controllers;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.glasswellapps.iact.R;
import com.glasswellapps.iact.character_screen.CharacterScreen;
import com.glasswellapps.iact.character_screen.views.IANumbers;
import com.glasswellapps.iact.characters.Character;

import java.util.Timer;
import java.util.TimerTask;

public class ScreenSaverController {
    CharacterScreen characterScreen;
    Dialog screenSaverDialog;
    int secondsSinceInactivity = 0;
    int turnOnTime = 60;
    float originalBrightness;
    private boolean isOn;
    ImageView activationOn, damage, strain, portrait, wounded;
    TextView name;

    public ScreenSaverController(CharacterScreen characterScreen){
        this.characterScreen = characterScreen;
        initScreenSaverDialog(characterScreen);
        start();
    }

    private void initScreenSaverDialog(CharacterScreen characterScreen) {
        screenSaverDialog = new Dialog(characterScreen);
        screenSaverDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        screenSaverDialog.setCancelable(false);
        View view = LayoutInflater.from(characterScreen).inflate(R.layout.dialog_screensaver, null);
        screenSaverDialog.setContentView(view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                turnOffScreenSaver();
                return false;
            }
        });
        screenSaverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        activationOn = view.findViewById(R.id.activation_image_green);
        damage = view.findViewById(R.id.damage_image);
        strain = view.findViewById(R.id.strain_image);
        portrait = view.findViewById(R.id.portrait_image);
        wounded = view.findViewById(R.id.wounded_image);
        name = view.findViewById(R.id.name_text);
    }
    public void start() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                characterScreen.runOnUiThread(new Runnable() {
                    public void run() {
                        if(!isOn && turnOnTime > 0) {
                            secondsSinceInactivity++;
                            //System.out.println(secondsSinceInactivity);
                            if (secondsSinceInactivity > turnOnTime) {
                                turnOnScreenSaver();
                            }
                        }
                    }
                });
            }
        }, 0, 1000);
    }
    public void onTouchEvent() {
        secondsSinceInactivity = 0;
    }
    public void setTurnOnTime(int turnOnTime){
        this.turnOnTime = turnOnTime;
    }
    private void turnOnScreenSaver() {
        isOn = true;
        secondsSinceInactivity = 0;
        setState();
        originalBrightness = getScreenBrightness();
        setScreenBrightness(0);
        screenSaverDialog.show();
        hideUI();
    }
    private void hideUI(){
        ViewGroup root = (ViewGroup) characterScreen.getWindow().getDecorView().getRootView();
        for(int i = 0; i<root.getChildCount();i++){
            final View child = root.getChildAt(i);
            child.animate().alpha(0f).withEndAction(
                    new Runnable() {
                        @Override
                        public void run() {
                            child.setVisibility(View.GONE);
                        }
                    }
            );
        }
    }
    private void setState(){
        Character character = characterScreen.getCharacter();

        name.setText(character.getName());
        strain.setImageDrawable(IANumbers.getNumber(characterScreen.getResources(),
                character.getStrain()));
        int damageNumber = character.getDamage();

        portrait.setImageDrawable(character.getPortraitImage());

        if(character.isWounded()){
            damageNumber = character.getWounded();
            wounded.setVisibility(View.VISIBLE);
        }
        else{
            wounded.setVisibility(View.INVISIBLE);
        }
        damage.setImageDrawable(IANumbers.getNumber(characterScreen.getResources(),
                damageNumber));
        if(character.isActivated()){
            activationOn.setVisibility(View.VISIBLE);
        }
        else{
            activationOn.setVisibility(View.INVISIBLE);
        }
    }
    private void turnOffScreenSaver(){
        isOn = false;
        ViewGroup root = (ViewGroup) characterScreen.getWindow().getDecorView().getRootView();
        setScreenBrightness(originalBrightness);
        for(int i = 0; i<root.getChildCount();i++){
            View child = root.getChildAt(i);
            child.setVisibility(View.VISIBLE);
            child.animate().alpha(1f);
        }
        screenSaverDialog.dismiss();
    }
    private float getScreenBrightness(){
        WindowManager.LayoutParams layoutParams = characterScreen.getWindow().getAttributes();
        return layoutParams.screenBrightness;
    }
    private void setScreenBrightness(float brightness){
        WindowManager.LayoutParams layoutParams = characterScreen.getWindow().getAttributes();
        layoutParams.screenBrightness = brightness;
        characterScreen.getWindow().setAttributes(layoutParams);
    }

    public int getTurnOnTime() {
        return turnOnTime;
    }
}
