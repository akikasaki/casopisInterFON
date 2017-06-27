package com.interfon.android.receiver;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.interfon.android.activity.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.interfon.android.activity.SettingsActivity.NOTIFICATION_STATE;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences prefs = context.getSharedPreferences(SettingsActivity.NOTIFICATION_PREFS, MODE_PRIVATE);
            boolean enabled = prefs.getBoolean(NOTIFICATION_STATE, false);
            // Set the alarm.
            if(enabled){
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                SettingsActivity.cancelAlarm(context, alarmManager);
                SettingsActivity.setAlarm(context, alarmManager);
            }
        }
    }
}
