package com.harp0n.notifyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

public class NotifyEditor_Activity extends AppCompatActivity {

    Notify notification = new Notify("notify", "notify", "notify", false, 100);

    Button btnBt, btnVolume, btnData, btnPlane, btnWifi, btnCreate;

    Switch sBt, sData, sPlane, sWifi, sOneTimeManyTimes;

    EditText etName, etDescription, etMessage;

    SeekBar sbVolume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_editor_);
        btnBt = findViewById(R.id.btnBluetooth);
        btnVolume = findViewById(R.id.btnVolume);
        btnData = findViewById(R.id.btnPhoneData);
        btnPlane = findViewById(R.id.btnPlaneMode);
        btnWifi = findViewById(R.id.btnWifi);

        btnCreate = findViewById(R.id.btnNext);

        sbVolume = findViewById(R.id.seekBar);

        etName = findViewById(R.id.et_Name);
        etDescription = findViewById(R.id.et_Description);
        etMessage = findViewById(R.id.et_NotifyMessage);

        sBt = findViewById(R.id.switchBluetooth);
        sData = findViewById(R.id.switchPhoneData);
        sPlane = findViewById(R.id.switchPlaneMode);
        sWifi = findViewById(R.id.switchWifi);
        sOneTimeManyTimes = findViewById(R.id.switchIsOneTime);


        btnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickButtonToShowSwitch(sBt);
                if(sBt.getVisibility() == View.VISIBLE )
                {
                    notification.setBluethoothChangeOn(true);
                } else notification.setBluethoothChangeOn(false);

            }
        });

        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonToShowSwitch(sWifi);
                if(sWifi.getVisibility() == View.VISIBLE )
                {
                    notification.setWifiChangeOn(true);
                } else notification.setWifiChangeOn(false);
            }
        });

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sbVolume.getVisibility() == View.INVISIBLE)
                {
                    sbVolume.setVisibility(View.VISIBLE);
                } else {
                    sbVolume.setVisibility(View.INVISIBLE);
                }

                if(sbVolume.getVisibility() == View.VISIBLE )
                {
                    notification.setVolumeChangeOn(true);
                } else notification.setVolumeChangeOn(false);

            }
        });
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonToShowSwitch(sData);
                if(sData.getVisibility() == View.VISIBLE )
                {
                    notification.setPhoneDataChangeOn(true);
                } else notification.setPhoneDataChangeOn(false);

            }
        });
        btnPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonToShowSwitch(sPlane);
                if(sPlane.getVisibility() == View.VISIBLE )
                {
                    notification.setPlaneModeChangeOn(true);
                } else notification.setPlaneModeChangeOn(false);

            }
        });



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendingToSerialization();
            }
        });
    }


    public void clickButtonToShowSwitch(Switch s)
    {
        if(s.getVisibility() == View.INVISIBLE)
        {
            s.setVisibility(View.VISIBLE);
        } else {
            s.setVisibility(View.INVISIBLE);
        }
    }

    public void createNotify()
    {
        notification.setName(etName.getText().toString());
        notification.setDescription(etDescription.getText().toString());
        notification.setNotificationMessage(etMessage.getText().toString());


        notification.setOneTime(sOneTimeManyTimes.isChecked());

        if(notification.isVolumeChangeOn())
        {
            notification.setSoundVolume(sbVolume.getProgress());
        }
        if(sBt.getVisibility() == View.VISIBLE)
        {
            notification.setBluethoothIsOn(sBt.isChecked());
        }
        if(sWifi.getVisibility() == View.VISIBLE)
        {
            notification.setWifiIsOn(sWifi.isChecked());
        }
        if(sBt.getVisibility() == View.VISIBLE)
        {
            notification.setBluethoothIsOn(sBt.isChecked());
        }
        if(sData.getVisibility() == View.VISIBLE)
        {
            notification.setPhoneDataIsOn(sBt.isChecked());
        }
        if(sPlane.getVisibility() == View.VISIBLE)
        {
            notification.setPlaneModeIsOn(sPlane.isChecked());
        }

    }

    public void sendingToSerialization()
    {
        createNotify();
    }


}
