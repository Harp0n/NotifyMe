package com.harp0n.notifyme;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;


public class ManagerService extends Service {
    private static ManagerService sInstance;
    //private PowerManager.WakeLock wakelock;
    private boolean isListening = false;
    private static final String TAG = "ManagerService";
    private static final String CHANNEL_ID = "channel_01";
    private static final String CHANNEL_MAIN_ID = "channel_02";
    private static final int FOREGROUND_ID = 42;
    private static final int NOTIFICATION_ID = 43;
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 7000;
    private static final float LOCATION_DISTANCE = 10f;
    private ArrayList<Notify> notifications;
    private Location lastLocation;
    Map<Integer, Boolean> isInsideMap = new HashMap<Integer, Boolean>();

    public void RefreshNotifies() {
        notifications = Serialization.load(getApplicationContext());
        startLocationListening();
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            if (lastLocation == null)
                lastLocation = new Location(location);
            else
                lastLocation.set(location);
            //notifications = Serialization.load(getApplicationContext());
            if (notifications == null)
                return;
            for (Notify notification : notifications) {
                int ID = notification.getID();

                if (!isInsideMap.containsKey(ID)) {
                    isInsideMap.put(ID, false);
                }

                boolean isInRange = notification.getLocation().distanceTo(location) <= notification.getRadius();
                if (!isInRange && isInsideMap.get(ID)) {
                    isInsideMap.remove(ID);
                    isInsideMap.put(ID, false);
                    Log.d("Wychodze: ", notification.getDescription());
                    continue;
                }

                if (isInRange && isInsideMap.get(ID) == false) {
                    isInsideMap.remove(ID);
                    isInsideMap.put(ID, true);
                    Log.d("Wchodze: ", notification.getDescription());
                    Log.d("Odleglosc od "+notification.getName(), String.valueOf(notification.getLocation().distanceTo(location)));
                    //Wysyłanie powiadomienia
                    if(!notification.getNotificationMessage().isEmpty())
                        sendNotification(notification.getName(),notification.getNotificationMessage());
                    else
                        sendNotification(notification.getName(), "cel osiągnięty");
                    //Przełączenie głośności
                    if(notification.isVolumeChangeOn())
                        setVolume(notification.getSoundVolume());
                    //Przełączenie wifi
                    if(notification.isWifiChangeOn())
                        setWifi(notification.isWifiIsOn());
                    //Przełączenie bluetooth
                    if(notification.isBluetoothChangeOn())
                        setBluetooth(notification.isBluetoothIsOn());
                    //Włączenie alarmu
                    if(notification.isAlarmSoundIsOn())
                        setAlarm();
                    //Usuwanie jeśli jest jednorazowe
                    if(notification.isOneTime()){
                        Serialization.remove(notification, getApplicationContext());
                    }
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }

    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };

    public static ManagerService getInstance() {
        return sInstance;
    }

    //return last location, or null if there was no previous detected location
    public Location getLastLocation() {
        return lastLocation;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        notifications = Serialization.load(this);
        return START_STICKY;
    }


    //CREATE NOTIFICATION
    @SuppressLint("NewApi")
    private void createForegroundNotification() {
        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_MAIN_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            //silence
            mChannel.enableVibration(false);
            mChannel.setSound(null, null);
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }

        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), Main_Activity.class);

        //send source information
        notificationIntent.putExtra("isFromNotification", true);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(Main_Activity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher_foreground))
                .setColor(Color.BLUE)
                .setContentTitle("NotifyMe")
                .setContentIntent(notificationPendingIntent)
                .setContentText("Kliknij aby wrócić do aplikacji");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_MAIN_ID); // Channel ID
        }

        //keep notification in foreground
        startForeground(FOREGROUND_ID, builder.build());
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: " + LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void startLocationListening() {
        if(isListening)
            return;
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
            isListening = true;
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
    }

    private void stopLocationListening() {
        mLocationManager.removeUpdates(mLocationListeners[0]);
    }

    @Override
    @SuppressLint("NewApi")
    public void onCreate() {
        //save local instance
        sInstance = this;
        createForegroundNotification();
//        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
//        wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "notify:wakelock");
//        wakelock.acquire();
        initializeLocationManager();
        startLocationListening();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();

        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listener, ignore", ex);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private void sendNotification(String notificationTitle, String notificationText) {
        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.enableVibration(true);
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }

        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), Main_Activity.class);

        notificationIntent.putExtra("isFromNotification", true);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(Main_Activity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Notification notification = new Notification();

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher_foreground))
                .setColor(Color.RED)
                .setContentTitle(getString(R.string.transition_entered) + " " + notificationTitle)
                .setContentText(notificationText)
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);
        // Issue the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private void setWifi(boolean isOn){
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(isOn);
    }
    private void setVolume(int volume){
        AudioManager audioManager =
                (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        audioManager.setStreamVolume(AudioManager.STREAM_RING,
                (int)(volume/100.0*maxRingVolume), AudioManager.FLAG_SHOW_UI);

//        int maxNotificationVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
//        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,
//                (int)(volume/100.0*maxNotificationVolume), AudioManager.FLAG_SHOW_UI);
    }
    private void setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            bluetoothAdapter.disable();
        }
    }
    private void setAlarm(){
        Intent startIntent = new Intent(this, RingtonePlayingService.class);
        startService(startIntent);
    }
}