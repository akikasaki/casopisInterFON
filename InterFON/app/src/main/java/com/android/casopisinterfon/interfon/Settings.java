package com.android.casopisinterfon.interfon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Aleksa on 9.3.2017.
 */

public class Settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,RadioGroup.OnCheckedChangeListener{
    public final static String NOTIFICATION_TOGGLE ="NotificationsOn";
    public final static String NOTIFICATION_STATE ="NotificationsState";
    ToggleButton tbNotifications;
    RadioGroup fontGroup;
    TextView tvFont;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initialize();

    }

    private void initialize() {
        tbNotifications=(ToggleButton) findViewById(R.id.tbNotifications);
        tvFont= (TextView) findViewById(R.id.tvFont);
        fontGroup=(RadioGroup) findViewById(R.id.rgFont);
        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_TOGGLE, MODE_PRIVATE);
        tbNotifications.setChecked(prefs.getBoolean(NOTIFICATION_STATE, true));
        tbNotifications.setOnCheckedChangeListener(this);
        fontGroup.setOnCheckedChangeListener(this);
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = getSharedPreferences(NOTIFICATION_TOGGLE, MODE_PRIVATE).edit();
        if(isChecked){
            editor.putBoolean(NOTIFICATION_STATE, true);
            editor.apply();
        }

        else {
            editor.putBoolean(NOTIFICATION_STATE, false);
            editor.apply();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent notificationStarter= new Intent(this,NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(Settings.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if(prefs.getBoolean(NOTIFICATION_STATE, true)){
            startService(notificationStarter);}
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent notificationStarter= new Intent(this,NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(Settings.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if(prefs.getBoolean(NOTIFICATION_STATE, true)){
            stopService(notificationStarter);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId) {
            case R.id.rbSmall:
                    tvFont.setTextSize(10);
                    break;
            case R.id.rbMedium:
                tvFont.setTextSize(20);
                    break;
            case R.id.rbLarge:
                tvFont.setTextSize(30);
                    break;
        }
    }
}
