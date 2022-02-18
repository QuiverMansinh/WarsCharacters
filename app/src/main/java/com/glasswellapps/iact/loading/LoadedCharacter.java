package com.glasswellapps.iact.loading;
import com.glasswellapps.iact.characters.Character;

import java.util.ArrayList;
import java.util.List;

public class LoadedCharacter {
    private static Character activeCharacter;
    public static Character getActiveCharacter(){
        return activeCharacter;
    }
    public static void setActiveCharacter(Character c) {
        if(charactersToSave == null){
            charactersToSave = new ArrayList<Character>();
        }
        if(!charactersToSave.contains(c)){
            charactersToSave.add(c);
        }
        activeCharacter = c;
    }
    public static void clearActiveCharacter(){
        activeCharacter = null;
    }


    private static ArrayList<Character> charactersToSave;
    public static  ArrayList<Character> getCharactersToSave(){return charactersToSave;}
    public static void addCharacterToSave(Character character){
        if(charactersToSave == null){
            charactersToSave = new ArrayList<Character>();
        }
        if(charactersToSave.contains(character)){
            return;
        }
        charactersToSave.add(character);
    }
    public static void removeCharacterToSave(Character character){
        if(charactersToSave == null){
            charactersToSave = new ArrayList<Character>();
        }
        charactersToSave.remove(character);
    }
    public static void clearCharactersToSave(){
        if(charactersToSave == null){
            charactersToSave = new ArrayList<Character>();
        }
        charactersToSave.clear();
    }

    static boolean isInteractable = true;
    public static boolean getIsInteractable(){return isInteractable;}
    public static void setIsInteractable(boolean interactable) {
        isInteractable = interactable;
    }
}
