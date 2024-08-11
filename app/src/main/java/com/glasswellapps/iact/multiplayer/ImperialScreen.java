package com.glasswellapps.iact.multiplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glasswellapps.iact.R;
import com.glasswellapps.iact.ReleaseResources;
import com.glasswellapps.iact.ShortToast;
import com.glasswellapps.iact.character_screen.controllers.ButtonPressedHandler;
import com.glasswellapps.iact.effects.Sounds;

import java.util.Observable;


public class ImperialScreen extends MultiplayerScreen {
    ImageView advertise;
    TextView showStatus;
    BluetoothManager bluetoothManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imperial_screen);
        isImperial = true;
        initBluetooth();
        init();

    }

    public final String[] PLAYER_INSULTS = new String[]{"rebel scum", "meatbags", "bantha fodder"};
    public int currentInsult = 0;
    public final String IMPERIAL_PROMPT = "< SCAN FOR PLAYERS";
    int discoveryTime = 0;
    boolean isDiscovering = false;
    String serverName = "";

    private void initBluetooth() {
        advertise = findViewById(R.id.bluetooth_button);
        showStatus = findViewById(R.id.show_status);
        showStatus.setVisibility(View.VISIBLE);
        bluetoothManager = new BluetoothManager(this, "IATracker", "dd8c0994-aa25-4698-8e30-56f286a98375");
        bluetoothManager.addObserver(this);
        bluetoothManager.isPermitted(this);
        showStatus.setText(IMPERIAL_PROMPT);
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBluetoothButton();
            }
        });
    }
    void onBluetoothButton(){
        if(BluetoothManager.isPermitted(this)) {
            if (!isDiscovering) {
                Sounds.INSTANCE.selectSound();
                ButtonPressedHandler.onButtonPressed(advertise);
                bluetoothManager.enableDiscovery();
            } else {
                ShortToast.show(this, "BLUETOOTH DISCOVERY ALREADY ENABLED");
                Sounds.INSTANCE.negativeSound();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allPermitted = true;
        if (requestCode == BluetoothManager.REQUEST_PERMISSIONS) {
            for(int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == RESULT_CANCELED) {
                    //ShortToast.show(this, permissions[i] + " DENIED");
                    allPermitted = false;
                }
                else{
                    //ShortToast.show(this, permissions[i] + " PERMITTED");
                }
            }
            if(!allPermitted) return;

            if(bluetoothManager.isBluetoothEnabled()){
                //ShortToast.show(this,"BLUETOOTH ENABLED");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BluetoothManager.REQUEST_ENABLE_DISCOVERY) {
            if (resultCode == RESULT_CANCELED) {
                ShortToast.show(this, "DISCOVERY DENIED");
            }
            else {
                bluetoothManager.startServer();
                discoveryTime = bluetoothManager.DISCOVERY_TIME;
                isDiscovering = true;
                Sounds.INSTANCE.play(Sounds.INSTANCE.getTerminal());
                currentInsult = 0;
                if(Math.random()>0.7f){
                    currentInsult = 1;
                    if(Math.random()<0.5f){
                        currentInsult = 2;
                    }
                }
                new CountDownTimer(1000*discoveryTime,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        showStatus.setText( "scanning < "+serverName +" > system for "+ PLAYER_INSULTS[currentInsult]  +
                                "...  "+ String.valueOf(discoveryTime));
                        discoveryTime--;
                    }
                    @Override
                    public void onFinish() {
                        showStatus.setText(IMPERIAL_PROMPT);
                        isDiscovering = false;
                    }
                }.start();
            }
        }
        if (requestCode == BluetoothManager.REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_CANCELED) {
                ShortToast.show(this,"BLUETOOTH NOT ENABLED");
            }
            else {
                ShortToast.show(this,"BLUETOOTH ENABLED");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(bluetoothManager.getBluetoothReciever());
        }catch (Exception e){
            System.out.println("RECEIVER NOT REGISTERED "+e);
        }
    }



    @Override
    public void update(Observable observable, Object o) {
        super.update(observable,o);
        System.out.println("MESSAGE RECEIVED");
        Message msg = bluetoothManager.getCurrentMessage();
        if(msg!=null) {
            int state = msg.what;
            switch (state) {
                case BluetoothState.IDLE:
                    //showStatus.setText("Idle");
                    break;
                case BluetoothState.LISTENING:
                    //showStatus.setText("Listening");
                    break;
                case BluetoothState.CONNECTING:
                    //showStatus.setText("Connecting");
                    break;
                case BluetoothState.CONNECTED:
                    //showStatus.setText("Connected");
                    break;
                case BluetoothState.CONNECTION_FAILED:
                    //showStatus.setText("Connection Failed");
                    break;
                case BluetoothState.MESSAGE_RECEIVED:
                    //showStatus.setText("Message Received");
                    onMessageReceived(msg);
                    break;
                case BluetoothState.ALREADY_CONNECTED:
                    //showStatus.setText("Already Connected");
                    break;
            }
        }
    }
    void onMessageReceived(Message msg){
        byte[] message = (byte[]) msg.obj;
        String str = new String(message ) +" " + message .length;
        System.out.println(str);
        int result = NetworkProtocol.onReceiveMessage(message, playerList, this);
        //ShortToast.show(this, "MESSAGE RECIEVED");
        switch (result){
            case Codes.PARTY_FULL: onPartyFull();break;
            case Codes.UPDATE:break;
            case Codes.DISCONNECT: onPlayerRemoved(NetworkProtocol.getIDFromMessage(message)); break;
            case Codes.PLAYER_ADDED: onBluetoothPlayerAdded();break;
        }
    }

    @Override
    protected void onGameOver() {
        super.onGameOver();
        bluetoothManager.sendMessageToAll(new byte[]{(byte)Codes.GAMEOVER});
    }

    private void onPartyFull() {
        NetworkProtocol.disconnect(bluetoothManager);
        Sounds.INSTANCE.negativeSound();
        ShortToast.show(this, "PARTY FULL");
    }

    private void onBluetoothPlayerAdded(){
        ShortToast.show(this, "PLAYER ADDED");
        Sounds.INSTANCE.buttonSound();
        bluetoothManager.sendMessageToAll(new byte[]{(byte)Codes.PLAYER_ADDED});
    }

    @Override
    public void onPlayerRemoved(int playerNumber) {
        disconnectPlayer(playerNumber);
        super.onPlayerRemoved(playerNumber);

    }
    void onPlayerRemoved(String id){
        int playerNumber=getPlayerNumberFromID(id);
        if(playerNumber>-1)
         onPlayerRemoved(playerNumber);
    }
    public int getPlayerNumberFromID(String id){
        for(int i = 0; i < playerList.length;i++){
            if(playerList[i].id.equals(id)){
               return i;
            }
        }
        return -1;
    }
    public Player getPlayerByID(String id){
        for(int i = 0; i < characterSlots.length;i++){
            if(characterSlots[i].equalsID(id)){
                return characterSlots[i].getPlayer();
            }
        }
        return null;
    }

    public void disconnectPlayer(int playerNumber) {
        Player player = characterSlots[playerNumber].player;
        if(!player.isLocal) {
            bluetoothManager.disconnect(player.getId());
        }
    }

    @Override
    public void onBackPressed() {
        stopService();
        super.onBackPressed();
    }
    void stopService(){
        if(bluetoothManager==null){
            return;
        }
        for(int i = 0; i< characterSlots.length;i++){
            disconnectPlayer(i);
        }
        bluetoothManager.stopService(Codes.DISCONNECT);
    }

}