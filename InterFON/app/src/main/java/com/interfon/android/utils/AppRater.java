package com.interfon.android.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.R;

/**
 * Class used for rate this app popup. Allows users to go on internet and rate the app.
 */
public class AppRater {
    private final static String APP_TITLE = "Casopis InterFON";// App Name
    private final static String APP_PNAME = "com.interfon.android";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 7;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 7;//Min number of launches

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = getPrefs(mContext);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showInputDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    private static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences("apprater", 0);
    }

    public static void showInputDialog(final Context context){
        showInputDialog(context, getPrefs(context).edit());
    }

    /**
     * Helper method for showing input dialog for rating.
     */
    public static void showInputDialog(final Context context, final SharedPreferences.Editor editor) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View view = LayoutInflater.from(context).inflate(R.layout.rate_dialog, null);

        ((TextView)view.findViewById(R.id.tvDesc)).setText("Ako ste zadovoljni radom aplikacije "
                + APP_TITLE + ", imate neku kritiku/predlog, molimo Vas odvojite trenutak da je ocenite i ostavite komentar. Hvala na podrsci!");

        builder.setView(view);
        builder.setTitle("Oceni " + APP_TITLE);

        // Set up the buttons
        builder.setPositiveButton("Oceni!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
            }
        });

        builder.setNegativeButton("Kasnije..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (editor != null) {
                    editor.putBoolean("dontshowagain", false);
                    editor.commit();
                }
            }
        });

        builder.setNeutralButton("Ne prikazuj vise :(", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        builder.show();
    }


}