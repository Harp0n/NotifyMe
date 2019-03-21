package com.harp0n.notifyme;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

public class Main_Activity extends AppCompatActivity {

    //TODO exchange for load()
    private Notify[] tests = new Notify[20];
    private FloatingActionButton fbEditor;
    private ExpandableListView expList;
    private int lastPosition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        for(int i=0; i<tests.length; i++){
            Notify n = new Notify();
            n.setAlarmSoundOn(true);
            n.setBluethoothChangeOn(false);
            n.setDiscription("TEST TEST TEST" + i);
            n.setName("NOTIFY" + i);
            n.setNotificationMessage("MESSAGE");
            n.setOneTime(false);
            n.setRadius(29.3424);
            n.setVolumeChangeOn(true);
            n.setSoundVolume(69);
            n.setWifiChangeOn(false);
            n.setX_coordinate(69.696969);
            n.setY_coordinate(96.969696);
            tests[i] = n;
        }

        expList = findViewById(R.id.expList);
        ExpandableList el = new ExpandableList(this, tests);
        expList.setAdapter(el);
        expList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastPosition != -1
                        && groupPosition != lastPosition) {
                    expList.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;
            }
        });

        fbEditor = findViewById(R.id.fbEditor);
        fbEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Main_Activity.this, GoogleMaps.class);
                Main_Activity.this.startActivity(myIntent);
            }
        });

    }
}
