package com.interfon.android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class ContactUsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String MAIL_RECIPIENT = "info@casopisinterfon.org";
    private static final String PACKAGE_PLAY_SERVICES = "com.google.android.gms.common";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Add functionality to the Map
        super.onCreate(savedInstanceState);
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            setContentView(R.layout.contact_us);

            //Set a map fragment and add some functionality to it
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            setContentView(R.layout.contact_us_map_image);
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ((TextView) findViewById(R.id.tvHeading)).setText(R.string.contact_us_activity_heading);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //For contacting Via email
        Button sendEmail = (Button) findViewById(R.id.bSendEmail);
        sendEmail.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //TODO Map without Google Play Services
        //Set a marker at FON when first loading the map
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(44.7725999, 20.4748169))
                .title("Marker"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSendEmail:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Tema");
                intent.setData(Uri.parse("mailto:" + MAIL_RECIPIENT));
                //So that user returns to InterFON instead the email app on clicking back
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;
        }
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
