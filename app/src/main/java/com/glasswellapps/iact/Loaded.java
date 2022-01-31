package com.glasswellapps.iact;
import com.glasswellapps.iact.characters.Character;
import com.glasswellapps.iact.database.CharacterData;
import java.util.List;

public class Loaded {
    private static Character character;
    private static List<CharacterData> data;
    public static Character getCharacter(){
        return character;
    }
    public static void setCharacter(Character c) {
        character = c;
    }
    public static List<CharacterData> getData(){
        return data;
    }
    public static void setData( List<CharacterData> d){
        data = d;
    }
}
