package com.stepin.coronaapp.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class ShowCurrentLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PulsatorLayout pulsator;
    private ImageView markerImage;
    private FusedLocationProviderClient mFusedLocationClient;
    private String type = "";
    private LatLng latLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_current_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pulsator = findViewById(R.id.pulsator);
        markerImage = findViewById(R.id.imageView18);
        Button nextBtn = findViewById(R.id.nextBtn);

        type = getIntent().getStringExtra("type");
        nextBtn.setText(type);
        if (type.equals("Report")) { nextBtn.setText("Send Report"); }

        TextView titleTxt = findViewById(R.id.mapTitle);
        titleTxt.setText(type);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            start();
        }


        mMap.setOnCameraMoveListener(() -> latLng = mMap.getCameraPosition().target);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                goToActivity();
//                finish();
//            }
//        }, 8000);

    }

    private void start() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        pulsator.start();
        markerImage.setVisibility(View.VISIBLE);
        //mMap.setMyLocationEnabled(true);

        mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) { return; }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            mMap.animateCamera(cameraUpdate);
        });
    }

    private double getInch() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return Math.sqrt(x+y);
    }

    private void goToActivity() {

        switch (type) {
            case "Report":
                goToReportScreen();
                break;
            case "Check In":
                Intent intent1 = new Intent(this, MapsActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                if (latLng != null) {
                    intent1.putExtra("lat", String.valueOf(latLng.latitude));
                    intent1.putExtra("long", String.valueOf(latLng.longitude));
                }
                startActivity(intent1);
        }

        finish();
    }

    private void goToReportScreen() {
        Log.d("inch", ""+getInch());
        // if (getInch() > 5.5) {
        //     Intent intent = new Intent(this, NewReportCenterActivity.class);
        //     intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //     if (latLng != null) {
        //         intent.putExtra("lat", String.valueOf(latLng.latitude));
        //         intent.putExtra("long", String.valueOf(latLng.longitude));
        //     }
        //     startActivity(intent);
        // } else {
            Intent intent = new Intent(this, ReportActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (latLng != null) {
                intent.putExtra("lat", String.valueOf(latLng.latitude));
                intent.putExtra("long", String.valueOf(latLng.longitude));
            }
            startActivity(intent);
        // }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            start();
        }
    }

    public void backBtnTapped(View view) {
        finish();
    }

    public void nextActivityBtnTapped(View view) {
        goToActivity();
    }
}
