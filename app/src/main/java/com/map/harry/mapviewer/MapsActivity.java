package com.map.harry.mapviewer;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float last_x, last_y, last_z;
    long lastUpdate = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        //set up the accelerometer
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @TargetApi(Build.VERSION_CODES.N)
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];  // x value
            float y = event.values[1];  // y value
            float z = event.values[2];  // z value
            long curTime = System.currentTimeMillis();
            if (Math.abs(curTime - lastUpdate) > 2000)
            {
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                String currentDateTime = date.format(new Date());
                lastUpdate = curTime;
                if(Math.abs(last_x - x)> 10)
                {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.26062, -80.42178))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .title("Hey you have moved on to x axis on"+currentDateTime));
                }
                if(Math.abs(last_y - y)> 10)
                {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.23062, -80.42188))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("Hey you have moved on to y axis on"+currentDateTime));
                }
                if(Math.abs(last_z - z)> 10)
                {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.23062, -80.43178))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title("Hey you have moved on to z axis on"+currentDateTime));
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }
    public void onAccuracyChanged(Sensor sensor , int accuracy){

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void setUpMapIfNeeded()

    {
        // Do a null check to confirm that we have not already instantiated the map.
        if(mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // check if we were successful obtaining the map
            if(mMap != null)
            {
                setUpMap();
            }

        }
    }
    private void setUpMap()
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(37.229,-80.424)).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.229, -80.424), 14.9f));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
