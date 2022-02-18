package com.glasswellapps.iact.imperial;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;

import com.glasswellapps.iact.character_screen.controllers.SavingController;
import com.glasswellapps.iact.characters.Character;
import com.glasswellapps.iact.effects.WorkingAnimations;
import com.glasswellapps.iact.loading.LoadedCharacter;
import com.glasswellapps.iact.loading.LoadingController;

public class ImperialSavingController extends SavingController {
    Activity context;
    SharedPreferences sharedPreferences;
    NetworkPlayer[] playerList;

    public ImperialSavingController(@NonNull Activity context, @NonNull View saving_icon) {
        super(context, saving_icon);
        this.context = context;
        sharedPreferences = getContext().getSharedPreferences("ImperialPlayers",
                Context.MODE_PRIVATE);
    }

    public void loadPlayers(NetworkPlayer[] playerList){
        onSavingStarted();
        this.playerList = playerList;
        int[] playerIDs = getPlayerIDs();
        Character[] loadedCharacters = LoadingController.Companion.loadCharactersByID(playerIDs,
                context);
        System.out.println(getPlayerIDs().length);
        System.out.println("LOAD " + loadedCharacters.length);
        for(int j = 0; j < playerIDs.length;j++) {
            for (int i = 0; i < loadedCharacters.length; i++) {
                if (loadedCharacters[i].getId() == playerIDs[j]) {
                    playerList[j].loadLocalCharacter(loadedCharacters[i], context);
                    System.out.println("LOADED " + loadedCharacters[i].getFile_name());
                }
            }
        }
        onSavingFinished();
    }

    int[] getPlayerIDs(){
        int[] playerIDs = new int[4];
        for(int i = 0; i < 4;i++){
            playerIDs[i] = sharedPreferences.getInt("ImperialPlayer"+i,-1);
            System.out.println("GET ID " + playerIDs[i]);
        }
        return  playerIDs;
    }
    public void savePlayers(NetworkPlayer[] playerList){
        this.playerList = playerList;
        LoadedCharacter.clearCharactersToSave();
        for (int i = 0; i < playerList.length; i++) {
            NetworkPlayer player = playerList[i];
            if(player.isLocal && player.getCharacter()!=null){
                player.getCharacter().setFile_name("Imperial Player "+(i+1));
                LoadedCharacter.addCharacterToSave(player.getCharacter());
            }
        }
        quickSave();

    }

    @Override
    protected void onSavingStarted() {
        WorkingAnimations.Companion.startGearAnimation(getSaving_icon());
    }

    @Override
    protected void onSavingFinished(){
        super.onSavingFinished();
        commitToSharedPreferences(playerList);
    }
    void commitToSharedPreferences(NetworkPlayer[] playerList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < playerList.length; i++) {
            NetworkPlayer player = playerList[i];
            if(player.isLocal && player.getCharacter()!=null){
                editor.putInt("ImperialPlayer"+i, player.getCharacter().getId());
                System.out.println("SAVE ID "+player.getCharacter().getId());
            }
            else{
                editor.putInt("ImperialPlayer"+i, -1);

            }
        }
        editor.commit();
    }
}
