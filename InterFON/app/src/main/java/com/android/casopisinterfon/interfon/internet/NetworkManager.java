package com.android.casopisinterfon.interfon.internet;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Class for building json request sent to the server
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
     *
     * @param pageIndex        page number of articles to download from
     * @param callbackListener callback interface to be called when download has completed.
     *                         If null, default listeners would be used.
     */
    public void downloadArticles(int pageIndex, @Nullable final DownloadInterface callbackListener) {
        Uri.Builder builder = Uri.parse(UrlData.GET_POSTS)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_PAGE, Integer.toString(pageIndex));

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (callbackListener != null)
                            callbackListener.onDownloadSuccess(response);

                        // TODO - add default response handling RADOVAN
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Parsing an error
                        NetworkResponse response = error.networkResponse;
                        String errorData = new String(response.data);
                        int statusCode = response.statusCode;

                        if (callbackListener != null)
                            callbackListener.onDownloadFailed(errorData);

                        Log.e(TAG, errorData);
                        // TODO - add default response handling RADOVAN
                    }
                });

        mRequestQueue.add(request);
    }
}
