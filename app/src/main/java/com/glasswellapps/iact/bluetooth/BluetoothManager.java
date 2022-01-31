package com.glasswellapps.iact.bluetooth;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Observable;
import java.util.UUID;

public class BluetoothManager extends Observable {
    public static final int REQUEST_ENABLE_BLUETOOTH=1;
    public static final int REQUEST_ENABLE_DISCOVERY=2;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> foundDevices=new ArrayList<BluetoothDevice>();
    private ArrayList<String> deviceNames=new ArrayList<String>();
    private ArrayList<String> deviceAddresses=new ArrayList<String>();
    private ArrayList<Connection> connections = new ArrayList<>();
    private String appName;
    private UUID uuid;
    private Activity activity;
    private DeviceFinder deviceFinder;
    private Server server;
    private Client client;
    private Message currentMessage;

    public BluetoothManager(Activity activity, String appName, String uuid) {
        this.activity = activity;
        this.appName = appName;
        this.uuid = UUID.fromString(uuid);
        stopService();
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            Toast.makeText(activity.getApplicationContext(), "Bluetooth not supported", Toast.LENGTH_LONG).show();
            UpdateState(BluetoothState.UNSUPPORTED);
        }
        else if(!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableIntent,REQUEST_ENABLE_BLUETOOTH);
        }
        deviceFinder = new DeviceFinder(activity,this);
    }

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            currentMessage = msg;
            onUpdate();
            return true;
        }
    });

    public void enableDiscovery() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
        activity.startActivityForResult(intent, REQUEST_ENABLE_DISCOVERY);
    }
    public void discoverDevices() {
        deviceFinder.discoverDevices();
    }
    public void findBondedDevices() {
        deviceFinder.findBondedDevices();
    }
    public void clearDevices() {
        deviceNames.clear();
        deviceAddresses.clear();
        foundDevices.clear();
    }
    public void addDevice(BluetoothDevice device) {
        if(foundDevices.contains(device)){
            return;
        }
        foundDevices.add(device);
        deviceNames.add(device.getName());
        deviceAddresses.add(device.getAddress());
    }

    public void startServer() {
        if(server == null) {
            server = new Server(this);
            server.start();
            Toast.makeText(activity.getApplicationContext(), "Server started", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(activity.getApplicationContext(), "Already serving", Toast.LENGTH_SHORT).show();
        }
    }
    public void startClient(int index) {
        bluetoothAdapter.cancelDiscovery();
        client = new Client(foundDevices.get(index), this);
        client.start();
        Toast.makeText(activity.getApplicationContext(), "Connecting to server", Toast.LENGTH_SHORT).show();
    }

    public void stopService() {
        if(client!=null) client.stopClient();
        client = null;
        if(server!=null) server.stopServer();
        server = null;
        for (Connection connection: connections)
        {
            connection.stopConnection();
        }
        connections.clear();
        foundDevices.clear();
        deviceNames.clear();
        deviceAddresses.clear();
    }

    public void writeToStream(byte[] data) {
        for (Connection connection: connections) {
            connection.write(data);
        }
    }

    public void recieveMessage(int bytes, byte[] buffer) {
        handler.obtainMessage(BluetoothState.MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
    }
    public Message getCurrentMessage() { return currentMessage; }
    public BroadcastReceiver getBluetoothReciever() { return  deviceFinder.getBluetoothReciever(); }
    public BluetoothAdapter getBluetoothAdapter(){ return bluetoothAdapter;}
    public ArrayList<String> getDeviceNames(){ return deviceNames; }
    public String getAppName(){ return appName; }
    public UUID getMyUuid(){ return uuid; }
    public ArrayList<Connection> getConnections(){ return connections; }
    public void addConnection(Connection connection){ connections.add(connection); }
    public void removeConnection(Connection connection) { connections.remove(connection); }

    public void onUpdate() {
        setChanged();
        notifyObservers();
    }
    public void UpdateState(int state) {
        Message message=Message.obtain();
        message.what=state;
        handler.sendMessage(message);
    }
}

/* ADD TO ACTIVITY
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == BluetoothManager.REQUEST_ENABLE_DISCOVERY)
        {
            if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(),"Discovery not enabled",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Discovery enabled",Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == BluetoothManager.REQUEST_ENABLE_BLUETOOTH)
        {
            if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(),"Bluetooth not enabled",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Bluetooth enabled",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothManager.getBluetoothReciever());
    }
 */