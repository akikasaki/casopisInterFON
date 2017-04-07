package com.android.casopisinterfon.interfon.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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

    public final static float SMALL_FONT_SIZE =8;
    public final static float MEDIUM_FONT_SIZE =10;
    public final static float LARGE_FONT_SIZE =12;

    public final static int SMALL_BUTTON_TOGGLED_STATE =0;
    public final static int MEDIUM_BUTTON_TOGGLED_STATE =1;
    public final static int LARGE_BUTTON_TOGGLED_STATE =2;

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
            case SMALL_BUTTON_TOGGLED_STATE:
                RadioButton rbSmall = (RadioButton) findViewById(R.id.rbSmall);
                rbSmall.setChecked(true);
                break;
            case MEDIUM_BUTTON_TOGGLED_STATE:
                RadioButton rbMedium = (RadioButton) findViewById(R.id.rbMedium);
                rbMedium.setChecked(true);
                break;
            case LARGE_BUTTON_TOGGLED_STATE:
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

        /**
         * For setting Notifications toggle
         */
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

        /**
         * For setting Font Size
         */
        switch(checkedId) {

            case R.id.rbSmall:
                setFont(SMALL_FONT_SIZE,SMALL_BUTTON_TOGGLED_STATE);
                break;
            case R.id.rbMedium:
                setFont(MEDIUM_FONT_SIZE,MEDIUM_BUTTON_TOGGLED_STATE);
                    break;
            case R.id.rbLarge:
                setFont(LARGE_FONT_SIZE,LARGE_BUTTON_TOGGLED_STATE);
                    break;
        }

    }

    /**
     * Method for setting a Font and remembering toggled state
     * @param size size of the font
     * @param lastState Currently toggled button
     */
    public void setFont(float size, int lastState){
        float dipSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
        SharedPreferences.Editor fonts = getSharedPreferences(FONTS, MODE_PRIVATE).edit();
        fonts.putFloat(GET_A_FONT, dipSize);
        fonts.putInt(GET_LAST_TOGGLED_STATE,lastState);
        fonts.apply();
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
