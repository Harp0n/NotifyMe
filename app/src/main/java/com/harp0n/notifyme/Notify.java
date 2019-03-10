package com.harp0n.notifyme;

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
    private int soundVolume;

    private boolean wifiChangeOn;
    private boolean wifiIsOn;

    private boolean bluethoothChangeOn;
    private boolean bluethoothIsOn;

    private boolean phoneDataChangeOn;
    private boolean phoneDataIsOn;

    private boolean planeModeChangeOn;
    private boolean planeModeIsOn;

    private boolean alarmSoundChangeOn;
    private boolean alarmSoundIsOn;

    public Notify(String name, String description, String notificationMessage, boolean isOneTime) {

        this.x_coordinate = 0;
        this.y_coordinate = 0;
        this.radius = 0;
        this.name = name;
        this.description = description;
        this.notificationMessage = notificationMessage;
        this.isOneTime = isOneTime;
        this.volumeChangeOn = false;
        this.soundVolume = 50;
        this.wifiChangeOn = false;
        this.wifiIsOn = false;
        this.bluethoothChangeOn = false;
        this.bluethoothIsOn = false;
        this.phoneDataChangeOn = false;
        this.phoneDataIsOn = false;
        this.planeModeChangeOn = false;
        this.planeModeIsOn = false;
        this.alarmSoundChangeOn = false;
        this.alarmSoundIsOn = false;

        this.ID = this.hashCode();
    }


///////////////////////////// Settery gettery toString - nic ważnego //////////////////////////
    //======================================================================================///////


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

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

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

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }

    public boolean isWifiChangeOn() {
        return wifiChangeOn;
    }

    public void setWifiChangeOn(boolean wifiChangeOn) {
        this.wifiChangeOn = wifiChangeOn;
    }

    public boolean isWifiIsOn() {
        return wifiIsOn;
    }

    public void setWifiIsOn(boolean wifiIsOn) {
        this.wifiIsOn = wifiIsOn;
    }

    public boolean isBluethoothChangeOn() {
        return bluethoothChangeOn;
    }

    public void setBluethoothChangeOn(boolean bluethoothChangeOn) {
        this.bluethoothChangeOn = bluethoothChangeOn;
    }

    public boolean isBluethoothIsOn() {
        return bluethoothIsOn;
    }

    public void setBluethoothIsOn(boolean bluethoothIsOn) {
        this.bluethoothIsOn = bluethoothIsOn;
    }

    public boolean isPhoneDataChangeOn() {
        return phoneDataChangeOn;
    }

    public void setPhoneDataChangeOn(boolean phoneDataChangeOn) {
        this.phoneDataChangeOn = phoneDataChangeOn;
    }

    public boolean isPhoneDataIsOn() {
        return phoneDataIsOn;
    }

    public void setPhoneDataIsOn(boolean phoneDataIsOn) {
        this.phoneDataIsOn = phoneDataIsOn;
    }

    public boolean isPlaneModeChangeOn() {
        return planeModeChangeOn;
    }

    public void setPlaneModeChangeOn(boolean planeModeChangeOn) {
        this.planeModeChangeOn = planeModeChangeOn;
    }

    public boolean isPlaneModeIsOn() {
        return planeModeIsOn;
    }

    public void setPlaneModeIsOn(boolean planeModeIsOn) {
        this.planeModeIsOn = planeModeIsOn;
    }

    public boolean isAlarmSoundChangeOn() {
        return alarmSoundChangeOn;
    }

    public void setAlarmSoundChangeOn(boolean alarmSoundChangeOn) {
        this.alarmSoundChangeOn = alarmSoundChangeOn;
    }

    public boolean isAlarmSoundIsOn() {
        return alarmSoundIsOn;
    }

    public void setAlarmSoundIsOn(boolean alarmSoundIsOn) {
        this.alarmSoundIsOn = alarmSoundIsOn;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "ID=" + ID +
                ", x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                ", radius=" + radius +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", isOneTime=" + isOneTime +
                ", volumeChangeOn=" + volumeChangeOn +
                ", soundVolume=" + soundVolume +
                ", wifiChangeOn=" + wifiChangeOn +
                ", wifiIsOn=" + wifiIsOn +
                ", bluethoothChangeOn=" + bluethoothChangeOn +
                ", bluethoothIsOn=" + bluethoothIsOn +
                ", phoneDataChangeOn=" + phoneDataChangeOn +
                ", phoneDataIsOn=" + phoneDataIsOn +
                ", planeModeChangeOn=" + planeModeChangeOn +
                ", planeModeIsOn=" + planeModeIsOn +
                ", alarmSoundChangeOn=" + alarmSoundChangeOn +
                ", alarmSoundIsOn=" + alarmSoundIsOn +
                '}';
    }
}

