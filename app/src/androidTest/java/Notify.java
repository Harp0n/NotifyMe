@SuppressWarnings("unused")

public class Notify {


    private int ID;

    private String name;
    private String discription;
    private String notificationMessage;

    private boolean isOneTime;

    private boolean volumeChangeOn;
    private boolean wifiChangeOn;
    private boolean bluethoothChangeOn;
    private boolean alarmSoundOn;

    private boolean wifiIsOn;
    private boolean bluethoothIsOn;

    private int soundVolume;


    public Notify()
    {
        this.setID(this.hashCode());
    }






    ///////////////////////////// Settery gettery toString - nic ważnego //////////////////////////
    //======================================================================================//////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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

    public boolean isBluethoothChangeOn() {
        return bluethoothChangeOn;
    }

    public void setBluethoothChangeOn(boolean bluethoothChangeOn) {
        this.bluethoothChangeOn = bluethoothChangeOn;
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

    public boolean isBluethoothIsOn() {
        return bluethoothIsOn;
    }

    public void setBluethoothIsOn(boolean bluethoothIsOn) {
        this.bluethoothIsOn = bluethoothIsOn;
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





    @Override
    public String toString() {
        
        return "Notify{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", discription='" + discription + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", isOneTime=" + isOneTime +
                ", volumeChangeOn=" + volumeChangeOn +
                ", wifiChangeOn=" + wifiChangeOn +
                ", bluethoothChangeOn=" + bluethoothChangeOn +
                ", alarmSoundOn=" + alarmSoundOn +
                ", wifiIsOn=" + wifiIsOn +
                ", bluethoothIsOn=" + bluethoothIsOn +
                ", soundVolume=" + soundVolume +
                '}';
    }
}

