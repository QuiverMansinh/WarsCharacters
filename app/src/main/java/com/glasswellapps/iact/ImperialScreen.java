package com.glasswellapps.iact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glasswellapps.iact.bluetooth.BluetoothManager;

public class ImperialScreen extends AppCompatActivity {
    ImageView advertise;
    TextView status;

    View[] characterViews = new View[4];
    BluetoothManager bluetoothManager;
    static final int REQUEST_COARSE_LOCATION = 3;
    static final int REQUEST_FINE_LOCATION = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imperial_screen);
        init();
    }

    private void init() {
        bluetoothManager = new BluetoothManager(this, "IATracker", "dd8c0994-aa25-4698-8e30-56f286a98375");

        for(int i = 0; i < 4; i++) {
            characterViews[i] =
                    (getLayoutInflater().inflate(R.layout.fragment_bluetooth_character,
                            findViewById(R.id.bt_characters)));

        }
        advertise = findViewById(R.id.advertise_button);
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothManager.enableDiscovery();
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

    }

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
                bluetoothManager.startServer();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothManager.getBluetoothReciever());
    }
}