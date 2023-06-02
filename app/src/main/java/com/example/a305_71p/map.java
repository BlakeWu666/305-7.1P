package com.example.a305_71p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a305_71p.sqliteHelper.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class map extends AppCompatActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng mel = new LatLng(Util.melLa, Util.melLo);
        googleMap.addMarker(new MarkerOptions()
                .position(mel)
                .title("Marker in Melbourne"));

        LatLng sydney = new LatLng(Util.syLa, Util.syLo);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,6));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mel,6));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}