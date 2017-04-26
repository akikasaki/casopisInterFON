package com.android.casopisinterfon.interfon.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ContactUsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Add functionality to the Map
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ((TextView) findViewById(R.id.tvHeading)).setText(R.string.contact_us_activity_heading);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set a map fragment and add some functionality to it
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //TODO Send email option

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //TODO Map without Google Play Services
        //Set a marker at FON when first loading the map
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(44.7725999,20.4748169))
                .title("Marker"));
    }
}
