package com.android.casopisinterfon.interfon.internet;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.casopisinterfon.interfon.data.ArticlesParser;
import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.internet.events.ListDownloadedEvent;
import com.android.casopisinterfon.interfon.utils.TaskHandler;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Class for building and sending network requests.
 * Downloads data from the server and call DataManager for saving it.
 */
public class NetworkManager {
    private static final String TAG = NetworkManager.class.getSimpleName();

    private static NetworkManager mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }

        return mInstance;
    }

    private NetworkManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Method for downloading new articles from the server.
     * Emits {@link ListDownloadedEvent} event when download is completed of failed.
     * @param pageIndex page number of articles to download from
     * @param freshData boolean that indicates if fresh data is needed for download thus deleting all old data.
     */
    public void downloadArticles(final int pageIndex, final boolean freshData) {
        Uri.Builder builder = Uri.parse(UrlData.GET_POSTS)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_PAGE, Integer.toString(pageIndex));

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        // Process and save response in background thread
                        TaskHandler.submitTask(new Runnable() {
                            @Override
                            public void run() {
                                // Save data
                                DataManager.getInstance().addData(
                                        // Parse data
                                        ArticlesParser.parseResponse(response),
                                        freshData);
                                // Notify UI
                                EventBus.getDefault().post(new ListDownloadedEvent(true));
                                // Log
                                Log.d(TAG, "Data download success, page number: " + pageIndex);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Notify UI
                        EventBus.getDefault().post(new ListDownloadedEvent(true));
                        // Parsing an error
                        NetworkResponse response = error.networkResponse;
                        String errorData = new String(response.data);
                        int statusCode = response.statusCode;
                        // Log
                        Log.e(TAG, String.format("Error downloading data, code:%d, msg:%s", statusCode, errorData));
                    }
                });

        mRequestQueue.add(request);
    }
}
