package com.android.casopisinterfon.interfon.internet;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.casopisinterfon.interfon.data.ArticlesParser;
import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.internet.events.ListDownloadedEvent;
import com.android.casopisinterfon.interfon.model.Category;
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
     * Method for downloading articles from the server.
     * Emits {@link ListDownloadedEvent} event when download is completed of failed.
     *
     * @param pageIndex page number of articles to download from
     * @param freshData boolean that indicates if fresh data is needed for download thus deleting all old data.
     */
    public void downloadArticles(final int pageIndex, final boolean freshData) {
        Uri.Builder builder = Uri.parse(UrlData.GET_POSTS)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_PAGE, Integer.toString(pageIndex))
                // Exclude content
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_POST_CONTENT);

        startDownloadProcess(builder.build().toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                // Process and save response in background thread
                TaskHandler.submitTask(new Runnable() {

                                           @Override
                                           public void run() {
                                               // Save data
                                               DataManager.getInstance().addData(
                                                       // Parse data
                                                       new ArticlesParser().parseAll(response),
                                                       freshData);
                                               // Notify UI
                                               EventBus.getDefault().post(new ListDownloadedEvent(true));
                                               // Log
                                               Log.d(TAG, "Data download success, page number: " + pageIndex);
                                           }
                });
            }
        });
    }

    /**
     * Method for downloading articles from the server by selected category.
     * Emits {@link ListDownloadedEvent} event when download is completed of failed.
     *
     * @param pageIndex page number of articles to download from.
     * @param freshData boolean that indicates if fresh data is needed for download thus deleting all old data.
     * @param category  articles with this category will only be downloaded.
     */
    public void downloadArticles(final int pageIndex, final boolean freshData, @Nullable final Category category) {
        if (category == null) {
            downloadArticles(pageIndex, freshData);
            return;
        }

        Uri.Builder builder = Uri.parse(UrlData.GET_POSTS_BY_CAT)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_CAT_ID, Integer.toString(category.getCatId()))
                .appendQueryParameter(UrlData.PARAM_PAGE, Integer.toString(pageIndex))
                // Exclude content
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_POST_CONTENT);

        startDownloadProcess(builder.build().toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                // Process and save response in background thread
                TaskHandler.submitTask(new Runnable() {
                                           @Override
                                           public void run() {
                                               // Save data
                                               DataManager.getInstance().addData(
                                                       // Parse data
                                                       new ArticlesParser().parseAll(response),
                                                       freshData);
                                               // Notify UI
                                               EventBus.getDefault().post(new ListDownloadedEvent(true));
                                               // Log
                                               Log.d(TAG, String.format("Articles download complete. Page num:%d, Category:%s", pageIndex, category));

                                           }
                });
            }
        });
    }

    /**
     * Creates network request and start download process.
     *
     * @param url             url to download from.
     * @param successListener {@link com.android.volley.Response.Listener} object tht will be called on download complete.
     */
    private void startDownloadProcess(String url, Response.Listener<JSONObject> successListener) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Notify UI
                        EventBus.getDefault().post(new ListDownloadedEvent(false));
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
