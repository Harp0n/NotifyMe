package com.harp0n.notifyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class NotifyEditor_Activity extends AppCompatActivity {

    Notify notification;

    Button btnBt, btnVolume, btnData, btnPlane, btnWifi;

    Switch sBt, sData, sPlane, sWifi, sOneTimeManyTimes;

    EditText etName, etDescription, etMessage;

    String notifyName;
    String notifyDescription;

    boolean isBtClicked = false, isVolumeClicked = false, isDataClicked = false, isPlaneClicked = false, isWifiClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_editor_);
        btnBt = findViewById(R.id.btnBluetooth);
        btnVolume = findViewById(R.id.btnVolume);
        btnData = findViewById(R.id.btnPhoneData);
        btnPlane = findViewById(R.id.btnPlaneMode);
        btnWifi = findViewById(R.id.btnWifi);

        etName = findViewById(R.id.et_Name);
        etDescription = findViewById(R.id.et_Description);
        etMessage = findViewById(R.id.et_NotifyMessage);

        sBt = findViewById(R.id.switchBluetooth);
        sData = findViewById(R.id.switchPhoneData);
        sPlane = findViewById(R.id.switchPlaneMode);
        sWifi = findViewById(R.id.switchWifi);
        sOneTimeManyTimes = findViewById(R.id.switchIsOneTime);








    }


    public Notify createNotify()
    {
        Notify createdNotification = new Notify();

        return createdNotification;
    }



}
