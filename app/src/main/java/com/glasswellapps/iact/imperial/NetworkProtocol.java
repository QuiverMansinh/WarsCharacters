package com.glasswellapps.iact.imperial;
import android.app.Activity;

import com.glasswellapps.iact.character_screen.controllers.BackgroundController;
import com.glasswellapps.iact.characters.Character;
import com.glasswellapps.iact.inventory.Codes;

import java.util.Arrays;

public class NetworkProtocol {
    public final static int PARTY_FULL = -1;
    public final static int UPDATE = -2;
    public static String createID(){
        String time = String.valueOf(System.currentTimeMillis());
        return time.substring(time.length()-1- MessageLength.ID,time.length()-1);
    }

    public static int onReceiveMessage(byte[] bytes, NetworkPlayer[] playerList,
                                       Activity context) {
        System.out.println("PACKET SIZE: "+bytes.length);
        String id = new String(Arrays.copyOfRange(bytes, 0, MessageLength.ID));

        NetworkPlayer sender = null;
        int emptyIndex = PARTY_FULL;

        for (int i = playerList.length - 1; i >=0; i--) {
            if (playerList[i].id.equals(id)) {
                sender = playerList[i];
                sender.updateCharacter(bytes);
                return UPDATE;
            }
            else if (playerList[i].character == null){
                emptyIndex = i;
            }
        }
        if(sender == null && emptyIndex!=PARTY_FULL){
            playerList[emptyIndex].newCharacter(bytes,context);
            return emptyIndex;
        }
        return PARTY_FULL;
    }

    public static byte[] sendCharacter(String id, Character character, boolean updateImages) {
        byte[] message = new byte[MessageLength.getAll()];
        putID(message,id);
        putCharacter(message,character);
        putActivated(message,character);
        putBackground(message,character);
        putConditions(message,character);
        putXP(message,character);
        putWeapon(message,character);
        putArmour(message,character);
        putAccessories(message,character);
        putRewards(message,character);
        putDamageStrain(message,character);
        message[Codes.UPDATE_IMAGES] = boolToByte(updateImages);
        return message;
    }
    static void putID(byte[] message, String id){
        byte[] byteID = id.getBytes();
        System.arraycopy(byteID, 0, message, 0, MessageLength.ID);
    }

    static void putCharacter(byte[] message, Character character){
        message[Codes.CHARACTER] = (byte)character.getIndex();
    }
    static void putBackground(byte[] message, Character character){
        message[Codes.BACKGROUND] = (byte)BackgroundController.Companion.getBackgroundIndex(character.getBackground());
    }
    static void putActivated(byte[] message, Character character){
        message[Codes.ACTIVATED] = (byte)(character.isActivated()?1:0);
    }
    static void putDamageStrain(byte[] message, Character character){
        message[Codes.DAMAGE_STRAIN] = (byte)character.getDamage();
        message[Codes.DAMAGE_STRAIN+1] = (byte)character.getStrain();
        message[Codes.DAMAGE_STRAIN+2] = (byte)character.getLastEffect();
        character.setLastEffect(0);
    }
    static void putConditions(byte[] message, Character character){
        boolArrayIntoBytes(character.getConditionsActive(), message, Codes.CONDITIONS);
    }
    static void putXP(byte[] message, Character character){
        boolArrayIntoBytes(character.getXpCardsEquipped(), message, Codes.XP);
    }
    static void putWeapon(byte[] message, Character character){
        for(int i = 0; i < MessageLength.WEAPONS; i++) {
            if(i<character.getWeapons().size()){
                message[Codes.WEAPONS + i] = character.getWeapons().get(i).byteValue();
            }
            else{
                message[Codes.WEAPONS + i] = (byte) Codes.EMPTY;
            }
        }
    }
    static void putArmour(byte[] message, Character character){
        if(character.getArmor().isEmpty()){
            message[Codes.ARMOUR] = (byte) Codes.EMPTY;
        }
        else{
            message[Codes.ARMOUR] = character.getArmor().get(0).byteValue();
        }
    }
    static void putAccessories(byte[] message, Character character){
        for(int i = 0; i < MessageLength.ACC; i++) {
            if(i<character.getAccessories().size()){
                message[Codes.ACC + i] = character.getAccessories().get(i).byteValue();
            }
            else{
                message[Codes.ACC + i] = (byte) Codes.EMPTY;
            }
        }
    }
    static void putRewards (byte[] message, Character character){
        boolean heroicEquipped = character.getRewards().contains(5);
        message[Codes.REWARDS] = boolToByte(heroicEquipped);

        boolean legendaryEquipped = character.getRewards().contains(11);
        message[Codes.REWARDS+1] = boolToByte(legendaryEquipped);
    }
    static void boolArrayIntoBytes(boolean[] bools, byte[] bytes, int start){
        for(int i = 0; i < bools.length;i++){
            bytes[start+i] = boolToByte(bools[i]);
        }
    }
    static byte boolToByte(boolean b){
        return (byte)(b?1:0);
    }
}
