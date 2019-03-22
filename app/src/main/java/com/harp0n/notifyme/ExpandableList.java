package com.harp0n.notifyme;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableList extends BaseExpandableListAdapter {
    private final int TITLE_TXT_SIZE = 40;
    private final int EXP_TXT_SIZE = 16;

    private Context context;
    private Notify[] notifies;

    public ExpandableList(Context context, Notify[] notifies){
        this.context = context;
        this.notifies = notifies;
    }

    @Override
    public int getGroupCount() {
        return notifies.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return notifies[groupPosition].getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return notifies[groupPosition].toString();
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText((String)getGroup(groupPosition));
        tv.setTextSize(TITLE_TXT_SIZE);
        tv.setPadding(100,10,20,10);
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Notify n = notifies[groupPosition];

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout buttons = new LinearLayout(context);
        buttons.setOrientation(LinearLayout.HORIZONTAL);

        final Button deleteButton = new Button(context);
        Button editButton = new Button(context);

        TextView nameView = new TextView(context);
        TextView descriptionView = new TextView(context);
        TextView messageView = new TextView(context);
        TextView isOneTimeView = new TextView(context);
        TextView volumeChangeView = new TextView(context);
        TextView wifiChangeView = new TextView(context);
        TextView bluetoothChangeView = new TextView(context);
        TextView alarmSoundOnView = new TextView(context);
        TextView[] views = {nameView, descriptionView, messageView, isOneTimeView, volumeChangeView, wifiChangeView,
                bluetoothChangeView, alarmSoundOnView};

        nameView.setText(n.getName());
        descriptionView.setText(n.getDescription());
        messageView.setText(n.getNotificationMessage());
        deleteButton.setText("USUN");
        editButton.setText("EDIT");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Serialization.remove(n,context);

                    }
                });            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, NotifyEditor_Activity.class);
                context.startActivity(myIntent);
            }
        });

        //TODO replace hardcoded strings with @res
        if(n.isOneTime()){
            isOneTimeView.setText("Jednorazowa: tak");
        }else{
            isOneTimeView.setText("Jednorazowa: nie");
        }

        if(n.isVolumeChangeOn()){
            volumeChangeView.setText("Ustaw poziom dźwięku na: "+n.getSoundVolume());
        }else{
            volumeChangeView.setText("Nie zmieniaj poziomu dźwięku.");
        }

        if(n.isWifiChangeOn()){
            wifiChangeView.setText("Włącz wifi: tak");
        }else{
            wifiChangeView.setText("Włącz wifi: nie");
        }

        if(n.isBluetoothChangeOn()){
            bluetoothChangeView.setText("Włącz bluetooth: tak");
        }else{
            bluetoothChangeView.setText("Włącz bluetooth: tak");
        }

        if(n.isAlarmSoundIsOn()){
            alarmSoundOnView.setText("Powiadomienie dźwiękowe: tak");
        }else{
            alarmSoundOnView.setText("Powiadomienie dźwiękowe: nie");
        }

        for(TextView v : views){
            v.setTextSize(EXP_TXT_SIZE);
            v.setPadding(50, 10, 50, 10);
            linearLayout.addView(v);
        }
        buttons.addView(deleteButton);
        buttons.addView(editButton);
        linearLayout.addView(buttons);


        return linearLayout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
