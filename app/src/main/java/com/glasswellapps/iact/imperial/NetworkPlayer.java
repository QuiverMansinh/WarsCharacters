package com.glasswellapps.iact.imperial;
import android.app.Activity;
import android.view.View;

import com.glasswellapps.iact.character_screen.types.EffectTypes;
import com.glasswellapps.iact.effects.Sounds;
import com.glasswellapps.iact.character_screen.CharacterBuilder;
import com.glasswellapps.iact.character_screen.controllers.BackgroundController;
import com.glasswellapps.iact.characters.Character;
import com.glasswellapps.iact.inventory.Codes;

public class NetworkPlayer {
    String id = "";
    Character character;
    Activity context;
    public boolean isLocal;
    NetworkPlayerView networkPlayerView;
    public NetworkPlayer(View view, Activity context) {
        networkPlayerView = new NetworkPlayerView(view, context);
        networkPlayerView.getActivationButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIsNotUpdatable()){return;}
                character.setActivated(!character.isActivated());
                networkPlayerView.activated(character.isActivated());
            }
        });
        networkPlayerView.getDamageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIsNotUpdatable()){return;}
                onAddDamage();
            }
        });
        networkPlayerView.getMinusDamageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIsNotUpdatable()){return;}
                onMinusDamage();
            }
        });

        networkPlayerView.getStrainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIsNotUpdatable()){return;}
                onAddStrain();
            }
        });
        networkPlayerView.getMinusStrainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIsNotUpdatable()){return;}
                onMinusStrain();
            }
        });
    }
    public void newCharacter(byte[] message, Activity context) {
        this.context = context;
        getID(message);
        int characterIndex = message[Codes.CHARACTER];
        character = CharacterBuilder.Companion.create(characterIndex,context);
        character.loadImages(context);
        isLocal = false;
        updateBackground(message,true);
        onNewCharacterAdded();
        updateCharacter(message);
    }
    public NetworkPlayerView getView(){
        return networkPlayerView;
    }
    public Character getCharacter(){
        return  character;
    }
    void onNewCharacterAdded(){
        networkPlayerView.nameView.setText(character.getName());
        networkPlayerView.setDefenceDice(character);
    }

    public void updateCharacter(byte[] message) {
        System.out.println("ID "+id);
        updateXP(message);
        updateWeapons(message);
        updateArmour(message);
        updateAccessories(message);
        updateRewards(message);
        updateBackground(message,false);
        updateIsActivated(message);
        updateConditions(message);
        updateDamageStrain(message);

        networkPlayerView.updateStats(character);
        if(message[Codes.UPDATE_IMAGES]==(byte)1){
            networkPlayerView.updateImages(character);
        }
    }
    void getID(byte[] message){
        byte[] byteID = new byte[MessageLength.ID];
        for(int i = 0; i < 4;i++){
            byteID[i] = message[i];
        }
        id = new String(byteID);
        System.out.println("RECEIVED ID " + id);
    }

    private void updateAccessories(byte[] message) {
        character.getAccessories().clear();
        int acc1 = message[Codes.ACC];
        int acc2 = message[Codes.ACC+1];
        int acc3 = message[Codes.ACC+2];
        if(acc1 != Codes.EMPTY){
            character.getAccessories().add(acc1);
        }
        if(acc2 != Codes.EMPTY){
            character.getAccessories().add(acc2);
        }
        if(acc3 != Codes.EMPTY){
            character.getAccessories().add(acc3);
        }
    }
    private void updateArmour(byte[] message) {
        int armour =  message[Codes.ARMOUR];
        character.getArmor().clear();
        if(armour != Codes.EMPTY){
            character.getArmor().add(armour);
        }
    }

    private void updateWeapons(byte[] message){
        character.getWeapons().clear();
        int weapon1 = message[Codes.WEAPONS];
        int weapon2 = message[Codes.WEAPONS+1];
        if(weapon1 != Codes.EMPTY){
            character.getWeapons().add(weapon1);
        }
        if(weapon2 != Codes.EMPTY){
            character.getWeapons().add(weapon2);
        }
    }

    private void updateRewards(byte[] message){
        character.getRewards().clear();
        boolean heroicEquipped = message[Codes.REWARDS]==(byte)1;
        boolean legendaryEquipped = message[Codes.REWARDS+1]==(byte)1;
        if(heroicEquipped){
            character.getRewards().add(5);
        }
        if(legendaryEquipped){
            character.getRewards().add(11);
        }
    }

    private void updateIsActivated(byte[] message) {
        boolean isActivated = message[Codes.ACTIVATED] ==1;
        if(isActivated != character.isActivated()){
            character.setActivated(isActivated);
            networkPlayerView.activated(isActivated);
        }
        System.out.println("RECEIVED ACTIVATED " + isActivated);
    }
    private void updateBackground(byte[] message, boolean firstUpdate) {
        String background = BackgroundController.Companion.getBackgroundName(message[Codes.BACKGROUND]);
        if(!background.equals(character.getBackground())||firstUpdate) {
            character.setBackground(background);
            networkPlayerView.setBackground(background);
        }
        System.out.println("RECEIVED BACKGROUND " + background);
    }
    private void updateConditions(byte[] message){
        for(int i = 0; i < MessageLength.CONDITIONS; i++){
            boolean isActive = message[Codes.CONDITIONS+i] == (byte)1;
            System.out.println("RECEIVED CONDITION " +i+" "+ isActive);
            character.getConditionsActive()[i] = isActive;
        }
        networkPlayerView.setConditions(character.getConditionsActive());
    }

    private void updateXP(byte[] message){
        boolean hasChanged = false;
        for(int i = 0; i < 9; i++){
            boolean isEquipped = message[Codes.XP+i] == (byte)1;
            System.out.println("RECEIVED XP " +i+" "+ isEquipped);
            if(isEquipped != character.getXpCardsEquipped()[i] ){
                hasChanged = true;
                if(isEquipped){
                    character.equipXP(i,context);
                }
                else{
                    character.unequipXP(i);
                }
            }
        }
        character.setRewardObtained(character.getXpCardsEquipped()[8]);
        System.out.println("HAS CHANGED "+hasChanged);
    }

    private void updateDamageStrain(byte[] message) {
        int damage = message[Codes.DAMAGE_STRAIN];
        if(damage != character.getDamage()) {
            updateDamage(damage);
        }

        System.out.println("RECEIVED DAMAGE " + damage);
        //STRAIN
        int strain = message[Codes.DAMAGE_STRAIN+1];
        if(character.getStrain() != strain){
            updateStrain(strain);
        }
        System.out.println("RECEIVED STRAIN " + strain);

        int effect = message[Codes.DAMAGE_STRAIN+2];
        networkPlayerView.playEffect(effect, character);


    }

    private void onAddStrain(){
        if (character.getStrain() < character.getEndurance()) {
            character.setStrain(character.getStrain()+1);
            character.setStrainTaken(character.getStrainTaken()+1);
            networkPlayerView.damageAnimation.playAnim(EffectTypes.STRAIN,character);
        } else {
            if(addDamage(true)) {
                character.setDamageTaken(character.getDamageTaken()+1);
            }
            else{
                Sounds.INSTANCE.negativeSound();
            }
        }
        updateStrain(character.getStrain());
    }
    private void onMinusStrain(){
        if(character.getStrain()>0) {
            Sounds.INSTANCE.selectSound();
            character.setStrain(character.getStrain()-1);
            updateStrain(character.getStrain());
        }
    }

    private void updateStrain(int strain) {
        character.setStrain(strain);
        networkPlayerView.updateStrain(strain, isLocal);
        //updateMinusButtons();
    }

    private void onAddDamage(){
        if (!addDamage(false)) {
            Sounds.INSTANCE.negativeSound();
        }
    }

    private boolean addDamage(boolean isStrainDamage){
        boolean damageChangeable = character.getDamage() < character.getHealth() * 2;
        if (damageChangeable) {
            networkPlayerView.damageAnimation.playDamageAnim(isStrainDamage, character);
            character.setDamage(character.getDamage() + 1);
            character.setDamageTaken(character.getDamageTaken() + 1);
            updateDamage(character.getDamage());
        }
        return damageChangeable;
    }

    private void onMinusDamage(){
        if(character.getDamage()>0) {
            Sounds.INSTANCE.selectSound();
            character.setDamage(character.getDamage()-1);
            updateDamage(character.getDamage());
        }
    }

    private void updateDamage(int damage) {
        character.setDamage(damage);

        if (damage < character.getHealth()) {
            if (character.isWounded()) {
                onUnwounded();
            }
            if(character.getWithdrawn()) {
                onUnwithdrawn();
            }
        }
        else if (damage < character.getHealth() * 2) {
            character.setWounded(character.getDamage() - character.getHealth());
            if (!character.isWounded()) {
                onWounded();
            }
            if(character.getWithdrawn()) {
                onUnwithdrawn();
            }
        }
        else {
            if(!character.getWithdrawn()) {
                onWithdrawn();
            }
        }
        networkPlayerView.updateDamage(character,isLocal);
        //updateMinusButtons();
    }
    void onUnwithdrawn(){
        character.setWithdrawn(false);
        networkPlayerView.unWithdrawn();
    }
    void onWithdrawn(){
        character.setWithdrawn(true);
        networkPlayerView.withrawn();
    }
    void onWounded(){
        character.setWounded(true);
        networkPlayerView.wounded(character);
    }
    void onUnwounded(){
        character.setWounded(false);
        networkPlayerView.unWounded(character);
    }

    public void loadLocalCharacter(Character character, Activity context) {
        this.context = context;
        this.character = character;
        character.loadImages(context);
        onNewCharacterAdded();
        updateView();
        updateMinusButtons();
        isLocal = true;
    }

    void updateMinusButtons(){
        if(isLocal) {
            networkPlayerView.getMinusDamageView().animate().alpha(character.getDamage() > 0 ? 1f : 0f);
            networkPlayerView.getMinusStrainView().animate().alpha(character.getDamage() > 0 ? 1f : 0f);
        }
    }

    public void updateView(){
        networkPlayerView.updateAll(character,isLocal);
    }
    boolean getIsNotUpdatable(){
        return character==null || !isLocal;
    }

    public void show() {
        networkPlayerView.show();
        updateView();
    }
}
