package com.glasswellapps.iact.bluetooth;
import android.app.Activity;

public abstract class BluetoothBuilder {
    protected Activity activity;
    protected BluetoothManager bluetoothManager;

    public BluetoothBuilder(Activity activity, BluetoothManager bluetoothManager)
    {
        this.activity = activity;
        this.bluetoothManager = bluetoothManager;
    }

    public abstract IBluetoothController create();
}
