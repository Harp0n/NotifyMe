package com.harp0n.notifyme;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;

////////////////////////////// To dla ciebie Seba, ale potrzebowałem głównej aktywności żeby sprawdzić moją /////////////////
public class Main_Activity extends Activity {

    private Button btnCreate, btnRemove;
    private static final String TAG = Main_Activity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final int ON_DO_NOT_DISTURB_CALLBACK_CODE = 35;

    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.VIBRATE);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(Main_Activity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.VIBRATE},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(Main_Activity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.VIBRATE},
                    REQUEST_PERMISSIONS_REQUEST_CODE);

            if( Build.VERSION.SDK_INT < 23 ) {
                return;
            }
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if(!notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivityForResult(intent, Main_Activity.ON_DO_NOT_DISTURB_CALLBACK_CODE);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        try {
            boolean isFromNotification = intent.getExtras().getBoolean("isFromNotification");
            if(isFromNotification){
                Intent stopIntent = new Intent(this, RingtonePlayingService.class);
                this.stopService(stopIntent);
            }
        }
        catch(Exception e){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        processIntent(getIntent());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Log.d("extras: ", extras.toString());
        }

        btnCreate = findViewById(R.id.btnCreate);
        btnRemove = findViewById(R.id.btnRemove);
        requestPermissions();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent myIntent = new Intent(Main_Activity.this, NotifyEditor_Activity.class);
              //  Main_Activity.this.startActivity(myIntent);
                Log.d("KLIK","PRZYCISK");
                Intent myIntent = new Intent(Main_Activity.this, ManagerService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // startForegroundService(myIntent);
                    startService(myIntent);
                } else {
                    startService(myIntent);
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KLIK", "WYLACZAM");
                stopService(new Intent(Main_Activity.this, ManagerService.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //requestPermissions();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Log.d("exytas: ", extras.toString());
        }
    }
}
