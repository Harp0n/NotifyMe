package com.harp0n.notifyme;

import android.location.Location;

@SuppressWarnings("unused")

public class Notify {


    private int ID;

    private double x_coordinate;
    private double y_coordinate;

    private double radius;

    private String name;
    private String description;
    private String notificationMessage;

    private boolean isOneTime;

    private boolean volumeChangeOn;
    private boolean wifiChangeOn;
    private boolean bluetoothChangeOn;
    private boolean alarmSoundOn;

    private boolean wifiIsOn;
    private boolean bluetoothIsOn;

    private int soundVolume;


    public Notify()
    {
        this.setID(this.hashCode());
    }

    public Notify(double latitude, double longitude, double radius,
                  String name, String description, String notificationMessage,
                  boolean volumeChangeOn, int soundVolume,
                  boolean wifiChangeOn, boolean wifiIsOn,
                  boolean bluetoothChangeOn, boolean bluetoothIsOn,
                  boolean alarmSoundOn,
                  boolean isOneTime)
    {
        this.setID(this.hashCode());
        this.setX_coordinate(longitude);
        this.setY_coordinate(latitude);
        this.setRadius(radius);
        this.setName(name);
        this.setDescription(description);
        this.setNotificationMessage(notificationMessage);
        this.setVolumeChangeOn(volumeChangeOn);
        this.setWifiChangeOn(wifiChangeOn);
        this.setBluetoothChangeOn(bluetoothChangeOn);
        this.setAlarmSoundOn(alarmSoundOn);
        this.setWifiIsOn(wifiIsOn);
        this.setBluetoothIsOn(bluetoothIsOn);
        this.setSoundVolume(soundVolume);
        this.setOneTime(isOneTime);
    }




    ///////////////////////////// Settery gettery toString - nic ważnego //////////////////////////
    //======================================================================================///////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public boolean isOneTime() {
        return isOneTime;
    }

    public void setOneTime(boolean oneTime) {
        isOneTime = oneTime;
    }

    public boolean isVolumeChangeOn() {
        return volumeChangeOn;
    }

    public void setVolumeChangeOn(boolean volumeChangeOn) {
        this.volumeChangeOn = volumeChangeOn;
    }

    public boolean isWifiChangeOn() {
        return wifiChangeOn;
    }

    public void setWifiChangeOn(boolean wifiChangeOn) {
        this.wifiChangeOn = wifiChangeOn;
    }

    public boolean isBluetoothChangeOn() {
        return bluetoothChangeOn;
    }

    public void setBluetoothChangeOn(boolean bluetoothChangeOn) {
        this.bluetoothChangeOn = bluetoothChangeOn;
    }

    public boolean isAlarmSoundOn() {
        return alarmSoundOn;
    }

    public void setAlarmSoundOn(boolean alarmSoundOn) {
        this.alarmSoundOn = alarmSoundOn;
    }

    public boolean isWifiIsOn() {
        return wifiIsOn;
    }

    public void setWifiIsOn(boolean wifiIsOn) {
        this.wifiIsOn = wifiIsOn;
    }

    public boolean isBluetoothIsOn() {
        return bluetoothIsOn;
    }

    public void setBluetoothIsOn(boolean bluetoothIsOn) {
        this.bluetoothIsOn = bluetoothIsOn;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public Location getLocation() { Location location = new  Location("");
                                    location.setLatitude(getY_coordinate());
                                    location.setLongitude(getX_coordinate());
                                    return location; }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {

        return "Notify{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", isOneTime=" + isOneTime +
                ", volumeChangeOn=" + volumeChangeOn +
                ", wifiChangeOn=" + wifiChangeOn +
                ", bluetoothChangeOn=" + bluetoothChangeOn +
                ", alarmSoundOn=" + alarmSoundOn +
                ", wifiIsOn=" + wifiIsOn +
                ", bluetoothIsOn=" + bluetoothIsOn +
                ", soundVolume=" + soundVolume +
                '}';
    }
}

