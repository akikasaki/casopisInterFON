package com.android.casopisinterfon.interfon.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.receiver.DataCheckReceiver;
import com.android.casopisinterfon.interfon.utils.FontPreferences;
import com.android.casopisinterfon.interfon.utils.FontStyle;


public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "SettingsActivity";


    public final static String NOTIFICATION_PREFS = "notification_prefs";
    public final static String NOTIFICATION_STATE = "NotificationsState";
    public final static String FONTS = "fonts";
    public final static String GET_A_FONT = "getAFont";
    public final static String GET_LAST_TOGGLED_STATE = "ToggledState";

    public final static float SMALL_FONT_SIZE = 8;
    public final static float MEDIUM_FONT_SIZE = 10;
    public final static float LARGE_FONT_SIZE = 12;

    public final static int SMALL_BUTTON_TOGGLED_STATE = 0;
    public final static int MEDIUM_BUTTON_TOGGLED_STATE = 1;
    public final static int LARGE_BUTTON_TOGGLED_STATE = 2;

    ToggleButton tbNotifications;
    RadioGroup fontGroup;
    TextView tvFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initialize();
    }

    private void initialize() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ((TextView) findViewById(R.id.tvHeading)).setText(R.string.settings_activity_heading);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tbNotifications = (ToggleButton) findViewById(R.id.tbNotifications);
        tvFont = (TextView) findViewById(R.id.tvFont);
        fontGroup = (RadioGroup) findViewById(R.id.rgFont);
        //previous notification toggled state
        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_PREFS, MODE_PRIVATE);

        final FontPreferences fontPrefs = new FontPreferences(this);
        FontStyle style = fontPrefs.getFontStyle();
        switch (style) {
            case Small:
                RadioButton rbSmall = (RadioButton) findViewById(R.id.rbSmall);
                rbSmall.setChecked(true);
                break;
            case Medium:
                RadioButton rbMedium = (RadioButton) findViewById(R.id.rbMedium);
                rbMedium.setChecked(true);
                break;
            case Large:
                RadioButton rbLarge = (RadioButton) findViewById(R.id.rbLarge);
                rbLarge.setChecked(true);
                break;
        }
        tbNotifications.setChecked(prefs.getBoolean(NOTIFICATION_STATE, false));
        tbNotifications.setOnCheckedChangeListener(this);
        fontGroup.setOnCheckedChangeListener(this);

    }

    /**
     * For setting Notifications toggle
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_PREFS, MODE_PRIVATE);

        if (isChecked) {
            prefs.edit().putBoolean(NOTIFICATION_STATE, true).apply();
            cancelAlarm(this, am);
            setAlarm(this, am);
        } else {
            prefs.edit().putBoolean(NOTIFICATION_STATE, false).apply();
            cancelAlarm(this, am);
        }
    }

    public static void setAlarm(Context context, AlarmManager am) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 13);

        Intent i = new Intent(context, DataCheckReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HOUR * 4, pi); // Millisec * Second * Minute

    }

    public static void cancelAlarm(Context context, AlarmManager am) {
        Intent intent = new Intent(context, DataCheckReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.cancel(sender);
    }

    /**
     * For setting Font Size
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        final FontPreferences prefs = new FontPreferences(this);
        switch (checkedId) {
            case R.id.rbSmall:
                // Small size selected
                prefs.setFontStyle(FontStyle.Small);
                Log.d(TAG, "1. item selected.");
                FontPreferences.notifyStateChanged();
                break;
            case R.id.rbMedium:
                // Medium size selected
                prefs.setFontStyle(FontStyle.Medium);
                Log.d(TAG, "2. item selected.");
                FontPreferences.notifyStateChanged();
                break;
            case R.id.rbLarge:
                // Large size selected
                prefs.setFontStyle(FontStyle.Large);
                Log.d(TAG, "3. item selected.");
                FontPreferences.notifyStateChanged();
                break;
            default:
                // Set medium
                prefs.setFontStyle(FontStyle.Medium);
                FontPreferences.notifyStateChanged();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Intent notificationStarter= new Intent(this,NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_PREFS, MODE_PRIVATE);
//        if(prefs.getBoolean(NOTIFICATION_STATE, true)){
//            startService(notificationStarter);}
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent notificationStarter= new Intent(this,NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(NOTIFICATION_PREFS, MODE_PRIVATE);
//        if(prefs.getBoolean(NOTIFICATION_STATE, true)){
//            stopService(notificationStarter);
//        }
//    }
}
