package com.glasswellapps.iact.bluetooth;
import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class IABluetoothController implements Observer, IBluetoothController {
    private Activity activity;
    private BluetoothManager bluetoothManager;
    private Button listen,send, listDevices, advertise;
    private ListView deviceList;
    private TextView msg_box,status;
    private EditText writeMsg;
    private ArrayAdapter<String> arrayAdapter;
    IABluetoothController(Activity activity,
                          BluetoothManager bluetoothManager,
                          Button listen,
                          Button send,
                          Button listDevices,
                          Button advertise,
                          ListView deviceList,
                          TextView msg_box,
                          TextView status,
                          EditText writeMsg){
        this.activity = activity;
        this.bluetoothManager = bluetoothManager;
        bluetoothManager.addObserver(this::update);
        this.listen = listen;
        this.send = send;
        this.listDevices = listDevices;
        this.advertise = advertise;
        this.deviceList = deviceList;
        this.msg_box = msg_box;
        this.status = status;
        this.writeMsg = writeMsg;
        bindUIToManager();
    }
    public void bindUIToManager() {
        arrayAdapter=new ArrayAdapter<String>(activity.getApplicationContext(),android.R.layout.simple_list_item_1,bluetoothManager.getDeviceNames());
        deviceList.setAdapter(arrayAdapter);
        listDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bluetoothManager.getDevices();
                bluetoothManager.discoverDevices();
                deviceList.setVisibility(View.VISIBLE);
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothManager.startServer();
                advertise.setVisibility(View.VISIBLE);
            }
        });
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                bluetoothManager.startClient(index);
                listen.setVisibility(View.GONE);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= String.valueOf(writeMsg.getText());
                bluetoothManager.writeToStream(string.getBytes());
            }
        });
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothManager.enableDiscovery();
            }
        });
    }
    @Override
    public void update(Observable observable, Object o) {
        Message msg = bluetoothManager.getCurrentMessage();
        if(msg!=null) {
            int state = msg.what;
            switch (state) {
                case BluetoothState.IDLE:
                    status.setText("Idle");
                    break;
                case BluetoothState.LISTENING:
                    status.setText("Listening");
                    break;
                case BluetoothState.CONNECTING:
                    status.setText("Connecting");
                    break;
                case BluetoothState.CONNECTED:
                    status.setText("Connected");
                    break;
                case BluetoothState.CONNECTION_FAILED:
                    status.setText("Connection Failed");
                    break;
                case BluetoothState.MESSAGE_RECEIVED:
                    status.setText("Message Received");
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff, 0, bluetoothManager.getCurrentMessage().arg1);
                    msg_box.setText(tempMsg);
                    break;
                case BluetoothState.ALREADY_CONNECTED:
                    status.setText("Already Connected");
                    break;
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
