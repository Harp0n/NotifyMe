package com.harp0n.notifyme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GoogleMaps extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Circle circle;
    private SeekBar radiusBarr;
    private LatLng home;
    private TextView meditText;
    private Button buttonNext;
    private Intent intent;
    private Location currentLocation;
    private class radiusListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            circle.setRadius(progress);
            meditText.setText(progress+"m");
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }


    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }
    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);
                if(marker!=null)
                {
                    marker.remove();
                    circle.remove();
                }
                circle = map.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(radiusBarr.getProgress())
                        .strokeWidth(0)
                        .fillColor(Color.argb(25, 244, 78, 62)));
                marker = map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet));
                marker.setDraggable(true);
            }

        });
    }
    private void setMarkerDrag(final GoogleMap map) {
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                circle.remove();
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                Log.d("System out", "onMarkerDragEnd...");
                LatLng latLng = new LatLng(marker.getPosition().latitude,
                        marker.getPosition().longitude);
                circle = map.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(radiusBarr.getProgress())
                        .strokeWidth(0)
                        .fillColor(Color.argb(25, 244, 78, 62)));

                map.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        marker.getPosition().latitude,
                        marker.getPosition().longitude);
                marker.setSnippet(snippet);
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_maps);
        buttonNext = (Button)findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent next = new Intent(GoogleMaps.this,NotifyEditor_Activity.class);
                  next.putExtra("Lat",marker.getPosition().latitude);
                    next.putExtra("Lng",marker.getPosition().longitude);
                        next.putExtra("radius", radiusBarr.getProgress());
                 startActivity(next);
            }
        });
         intent = getIntent();
        meditText =  findViewById(R.id.textView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        radiusBarr = findViewById(R.id.radiusBar);
        int test = 100;
        radiusBarr.setOnSeekBarChangeListener(new radiusListener());
         test = (intent.getIntExtra("test",10));
        Log.d("testujemy XD",String.valueOf(test));
        float zoom = 15;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
        }
        else mMap.setMyLocationEnabled(false);

        if (ManagerService.getInstance() != null && ManagerService.getInstance().getLastLocation() != null) {
            currentLocation = ManagerService.getInstance().getLastLocation();
            home = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        } else
            home = new LatLng(51.110393, 17.035653);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoom));
        circle = mMap.addCircle(new CircleOptions()
                .center(home)
                .radius(radiusBarr.getProgress())
                .strokeWidth(0)
                .fillColor(Color.argb(25, 244, 78, 62)));
        marker = mMap.addMarker(new MarkerOptions()
                .position(home)
                .title(getString(R.string.dropped_pin)));
        marker.setDraggable(true);
        setMapLongClick(mMap);
        setMarkerDrag(mMap);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
