package com.android.casopisinterfon.interfon.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.casopisinterfon.interfon.R;


public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,RadioGroup.OnCheckedChangeListener{

    public final static String NOTIFICATION_TOGGLE ="NotificationsOn";
    public final static String NOTIFICATION_STATE ="NotificationsState";
    public final static String FONTS ="fonts";
    public final static String GET_A_FONT ="getAFont";
    public final static String GET_LAST_TOGGLED_STATE ="ToggledState";

    public final static int SMALL_FONT_SIZE =8;
    public final static int MEDIUM_FONT_SIZE =10;
    public final static int LARGE_FONT_SIZE =12;

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
        SharedPreferences buttonState = getSharedPreferences(FONTS, MODE_PRIVATE);
        int i=buttonState.getInt(GET_LAST_TOGGLED_STATE,1);
        switch(i){
            case 0:
                RadioButton rbSmall = (RadioButton) findViewById(R.id.rbSmall);
                rbSmall.setChecked(true);
                break;
            case 1:
                RadioButton rbMedium = (RadioButton) findViewById(R.id.rbMedium);
                rbMedium.setChecked(true);
                break;
            case 2:
                RadioButton rbLarge = (RadioButton) findViewById(R.id.rbLarge);
                rbLarge.setChecked(true);
                break;
        }
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SharedPreferences.Editor fonts = getSharedPreferences(FONTS, MODE_PRIVATE).edit();
        switch(checkedId) {

            case R.id.rbSmall:
                fonts.putFloat(GET_A_FONT, SMALL_FONT_SIZE); // TODO - store values in DIP - TRAMPA
                fonts.putInt(GET_LAST_TOGGLED_STATE,0);
                fonts.apply();
                break;
            case R.id.rbMedium:
                fonts.putFloat(GET_A_FONT, MEDIUM_FONT_SIZE);
                fonts.putInt(GET_LAST_TOGGLED_STATE,1);
                fonts.apply();
                    break;
            case R.id.rbLarge:
                fonts.putFloat(GET_A_FONT, LARGE_FONT_SIZE);
                fonts.putInt(GET_LAST_TOGGLED_STATE,2);
                fonts.apply();
                    break;
        }
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Intent notificationStarter= new Intent(this,NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_TOGGLE, MODE_PRIVATE);
//        if(prefs.getBoolean(NOTIFICATION_STATE, true)){
//            startService(notificationStarter);}
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent notificationStarter= new Intent(this,NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_TOGGLE, MODE_PRIVATE);
//        if(prefs.getBoolean(NOTIFICATION_STATE, true)){
//            stopService(notificationStarter);
//        }
//    }
}
