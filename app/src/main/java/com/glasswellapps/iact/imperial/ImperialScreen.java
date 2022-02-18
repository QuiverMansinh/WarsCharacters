package com.glasswellapps.iact.imperial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.glasswellapps.iact.CardDisplay;
import com.glasswellapps.iact.loading.LoadedCharacter;
import com.glasswellapps.iact.R;
import com.glasswellapps.iact.ShortToast;
import com.glasswellapps.iact.character_screen.controllers.ButtonPressedHandler;
import com.glasswellapps.iact.effects.Sounds;

import java.util.Observable;
import java.util.Observer;

public class ImperialScreen extends AppCompatActivity implements Observer {
    ImageView advertise;
    TextView showStatus;
    SeekBar volumeSlider;
    FrameLayout[] containers = new FrameLayout[4];
    ImageView[] playerState = new ImageView[4];
    TextView[] playerDetected = new TextView[4];
    View[] playerViews = new View[4];
    NetworkPlayer[] playerList = new NetworkPlayer[4];
    ImperialSoundBoard imperialSoundBoard;
    CardDisplay cardDisplay;
    ImperialOptions imperialOptions;
    ImperialPlayerAdder imperialPlayerAdder;
    BluetoothManager bluetoothManager;
    boolean isAddingPlayer = false;
    ImperialSavingController savingController;

    static final int REQUEST_COARSE_LOCATION = 3;
    static final int REQUEST_FINE_LOCATION = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imperial_screen);
        init();
    }
    public final String[] PLAYERINSULTS = new String[]{"Rebel scum", "meatbags", "bantha fodder" };
    public int currentInsult = 0;

    public final String PROMPT = "";

    int playerToBeAdded = -1;

    int discoveryTime = 0;
    boolean isDiscovering = false;
    String serverName = "";

    private void init() {
        savingController = new ImperialSavingController(this,
                findViewById(R.id.imperial_saving_icon));
        showStatus = findViewById(R.id.show_status);

        bluetoothManager = new BluetoothManager(this, "IATracker", "dd8c0994-aa25-4698-8e30-56f286a98375");
        bluetoothManager.addObserver(this);
        serverName = bluetoothManager.getBluetoothAdapter().getName();
        serverName = serverName.substring(0,Math.min(serverName.length(),10));
        cardDisplay = new CardDisplay(this);
        imperialOptions = new ImperialOptions(this);
        imperialPlayerAdder = new ImperialPlayerAdder(this);

        containers[0] = findViewById(R.id.player0);
        playerState[0] = findViewById(R.id.imperial_player_state0);
        playerDetected[0] = findViewById(R.id.rebel_detected_text0);
        containers[1] = findViewById(R.id.player1);
        playerState[1] = findViewById(R.id.imperial_player_state1);
        playerDetected[1] = findViewById(R.id.rebel_detected_text1);
        containers[2] = findViewById(R.id.player2);
        playerState[2] = findViewById(R.id.imperial_player_state2);
        playerDetected[2] = findViewById(R.id.rebel_detected_text2);
        containers[3] = findViewById(R.id.player3);
        playerState[3] = findViewById(R.id.imperial_player_state3);
        playerDetected[3] = findViewById(R.id.rebel_detected_text3);

        for(int i = 0; i < 4; i++) {
            playerViews[i] = getLayoutInflater().inflate(R.layout.fragment_bluetooth_character, containers[i]);
            playerViews[i].setAlpha(0);
            playerList[i] = new NetworkPlayer(playerViews[i],this);
            playerViews[i].findViewById(R.id.imperial_options).setTag(i);
            playerViews[i].findViewById(R.id.imperial_options).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAddingPlayer){
                        addPlayer();
                    }
                    else{
                        int playerIndex = Integer.parseInt(view.getTag().toString());
                        NetworkPlayer playerPicked = playerList[playerIndex];

                        if(playerPicked.getCharacter() == null) {
                            playerToBeAdded = playerIndex;
                            imperialPlayerAdder.showDialog(playerToBeAdded);
                        }
                        else if(playerPicked.getView().isVisible){
                            imperialOptions.onShowDialog(playerPicked);
                        }
                        else {
                            onShowPlayer(playerIndex);
                        }
                    }
                }
            });
        }
        savingController.loadPlayers(playerList);
        for(int i = 0; i < 4; i++) {
            if(playerList[i].getCharacter()!=null){
                onNewPlayerAdded(i);
            }
        }


        advertise = findViewById(R.id.bluetooth_button);
        showStatus.setText(PROMPT);
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDiscovering) {
                    Sounds.INSTANCE.selectSound();
                    ButtonPressedHandler.onButtonPressed(advertise);
                    bluetoothManager.enableDiscovery();
                }
                else{
                    Sounds.INSTANCE.negativeSound();
                }
            }
        });

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_COARSE_LOCATION);
            }
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }

        volumeSlider = findViewById(R.id.imperial_volume_slider);
        volumeSlider.setProgress(50);
        Sounds.INSTANCE.setSoundVolume(0.5f);
        volumeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Sounds.INSTANCE.setSoundVolume((float)i/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        imperialSoundBoard = new ImperialSoundBoard(
                findViewById(R.id.sound_blaster),
                findViewById(R.id.sound_impact),
                findViewById(R.id.sound_slice),
                findViewById(R.id.sound_door),
                findViewById(R.id.sound_terminal),
                findViewById(R.id.sound_crate),
                findViewById(R.id.sound_move),
                findViewById(R.id.sound_droid),
                findViewById(R.id.sound_trooper),
                findViewById(R.id.sound_vehicle),
                findViewById(R.id.sound_lightsaber_attack),
                findViewById(R.id.sound_lightsaber));
    }

    private void onShowPlayer(int playerPicked) {
        playerList[playerPicked].show();
        Sounds.INSTANCE.buttonSound();/*
        for(int i = 0; i < playerList.length; i++){
            if(playerList[i].getCharacter()!=null) {
                playerList[i].show();
                Sounds.INSTANCE.buttonSound();
            }
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BluetoothManager.REQUEST_ENABLE_DISCOVERY) {
            if (resultCode == RESULT_CANCELED) {
                //Toast.makeText(getApplicationContext(),"Discovery not enabled",Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(getApplicationContext(),"Discovery enabled",Toast.LENGTH_SHORT)
                // .show();
                bluetoothManager.startServer();
                discoveryTime = bluetoothManager.DISCOVERY_TIME;
                isDiscovering = true;
                Sounds.INSTANCE.play(Sounds.INSTANCE.getTerminal());
                currentInsult = 0;
                if(Math.random()>0.8f){
                    currentInsult = 1;
                    if(Math.random()<0.5f){
                        currentInsult = 2;
                    }
                }
                new CountDownTimer(1000*discoveryTime,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        showStatus.setText( "Scanning the < "+serverName +" > system for "+ PLAYERINSULTS[currentInsult]  +
                                "...  "+ String.valueOf(discoveryTime));
                        discoveryTime--;
                    }
                    @Override
                    public void onFinish() {
                        showStatus.setText(PROMPT);
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

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updateView();
        if(isAddingPlayer){
            playerDetected[playerToBeAdded].animate().setDuration(150).alpha(1f);
        }
    }
    void updateView(){
        if(playerList == null){
            return;
        }
        for(int i = 0;i<playerList.length;i++){
            NetworkPlayer player = playerList[i];
            if(player.getCharacter()!=null){
                player.updateView();
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
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

        switch (result){
            case NetworkProtocol.PARTY_FULL: onPartyFull();break;
            case NetworkProtocol.UPDATE: break;
            default: onNewPlayerAdded(result);
        }
    }

    private void onPartyFull() {
        Sounds.INSTANCE.negativeSound();
        ShortToast.show(this, "PARTY FULL");
    }

    void addPlayer(){
        Sounds.INSTANCE.buttonSound();
        playerList[playerToBeAdded].loadLocalCharacter(LoadedCharacter.getActiveCharacter(),this);
        LoadedCharacter.setActiveCharacter(null);
        onNewPlayerAdded(playerToBeAdded);
        playerToBeAdded = -1;
        isAddingPlayer =false;
    }

    public void onNewPlayerAdded(int newPlayerIndex){
        playerDetected[newPlayerIndex].animate().setDuration(150).alpha(1f);
        savingController.savePlayers(playerList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        savingController.savePlayers(playerList);
        LoadedCharacter.setActiveCharacter(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        savingController.savePlayers(playerList);
    }
}