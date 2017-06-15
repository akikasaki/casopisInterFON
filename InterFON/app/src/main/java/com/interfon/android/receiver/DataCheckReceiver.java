package com.interfon.android.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.casopisinterfon.interfon.R;
import com.interfon.android.activity.MainActivity;
import com.interfon.android.data.DataManager;
import com.interfon.android.internet.ArticlesParser;
import com.interfon.android.internet.NetworkManager;
import com.interfon.android.model.Article;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class DataCheckReceiver extends BroadcastReceiver {
    private static final String TAG = "DataCheckReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {

        // Create the Handler object (on the main thread by default)
        Handler handler = new Handler();
        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                NetworkManager.getInstance(context).downloadLastArticle(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Article a = null;
                        try {
                            a = new ArticlesParser().parseArticle(
                                    response.getJSONArray("posts").getJSONObject(0));
                        } catch (JSONException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                        }

                        if (a != null && !DataManager.getInstance().checkIfItLastArticle(a.getId(), context)) {
                            fireNotification(context);
                        } else {
                            Log.i(TAG, "No new articles.");
                        }
                    }
                });
            }
        };
        // Run the above code block on the main thread after 1-60min
        handler.postDelayed(runnableCode, 1000 * 60 * (new Random().nextInt(60) + 1));
    }

    private void fireNotification(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_logo_notif)
                        .setAutoCancel(true)
                        .setContentTitle("Časopis InterFON")
                        .setContentText("Novi članci dostupni")
                        .setDefaults(Notification.DEFAULT_SOUND);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        //// The stack builder object will contain an artificial back stack for the
        //// started Activity.
        //// This ensures that navigating backward from the Activity leads out of
        //// your application to the Home screen.
        //        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //// Adds the back stack for the Intent (but not the Intent itself)
        //        stackBuilder.addParentStack(ResultActivity.class);
        //// Adds the Intent that starts the Activity to the top of the stack
        //        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(10, mBuilder.build());

    }
}
