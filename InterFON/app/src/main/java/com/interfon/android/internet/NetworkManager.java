package com.interfon.android.internet;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.interfon.android.data.DataManager;
import com.interfon.android.internet.events.ItemDownloadedEvent;
import com.interfon.android.internet.events.ListDownloadedEvent;
import com.interfon.android.model.Article;
import com.interfon.android.model.Category;
import com.interfon.android.utils.TaskHandler;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class for building and sending network requests.
 * Downloads data from the server and call DataManager for saving it.
 */
public class NetworkManager {
    private static final String TAG = NetworkManager.class.getSimpleName();

    /**
     * Starting index for article list on server
     */
    public static final int START_PAGE_INDEX = 1;

    /**
     * Contains page index that are already downloaded.
     */
    private static final int[] mCategoryPageIndex = new int[Category.CATEGORY_COUNT];

//    static {
//        for (int i = 0; i < MainActivity.CATEGORY_COUNT; i++) {
//            mCategoryPageIndex[i] = START_PAGE_INDEX;
//        }
//    }

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
    public void downloadArticles(final int pageIndex, final boolean freshData, final Context context) {
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
                                freshData, Category.ALL, context);
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
    public void downloadArticles(final int pageIndex, final boolean freshData, @Nullable final Category category, final Context context) {
        if (category == null) {
            downloadArticles(pageIndex, freshData, context);
            return;
        }

        Uri.Builder builder = Uri.parse(UrlData.GET_POSTS_BY_CAT)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_CAT_ID, Integer.toString(category.getCatId()))
                .appendQueryParameter(UrlData.PARAM_PAGE, Integer.toString(pageIndex))
                // Exclude content
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_POST_CONTENT)
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_CUSTOM_FIELDS);

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
                                freshData, category, context);
                        // Notify UI
                        EventBus.getDefault().post(new ListDownloadedEvent(true, category));
                        // Log
                        Log.d(TAG, String.format("Articles download complete. Page num:%d, Category:%s", pageIndex, category));

                    }
                });
            }
        });
    }

    /**
     * Method for downloading article description.
     *
     * @param article article which description text will be downloaded.
     */
    public void downloadArticleDesc(final Article article) {
        if (article == null) return;

        Uri.Builder builder = Uri.parse(UrlData.GET_POST)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_ARTICLE_ID, Long.toString(article.getId()))
                // Exclude content
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_THUMBNAIL_IMAGES)
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_COMMENTS)
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_AUTHOR)
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_TAGS)
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_POST_CATEGORIES);

        startDownloadProcess(builder.build().toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Article info download success.");
                ArticlesParser parser = new ArticlesParser();
                // Parse article
                Article a = null;
                try {
                    a = parser.parseArticle(response.getJSONObject("post"));
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }
                // Add info to article
                if (a != null)
                    article.setArticleDescription(a.getArticleDescription());
                // Notify UI
                EventBus.getDefault().post(new ItemDownloadedEvent(true));
            }
        });

    }

    /**
     * Download latest article from the server.
     * @param successListener listener that will be called after download is complete.
     */
    public void downloadLastArticle(Response.Listener<JSONObject> successListener) {
        Uri.Builder builder = Uri.parse(UrlData.GET_POSTS)
                .buildUpon()
                .appendQueryParameter(UrlData.PARAM_PAGE, Integer.toString(0))
                .appendQueryParameter(UrlData.PARAM_COUNT, Integer.toString(1))
                // Exclude content
                .appendQueryParameter(UrlData.PARAM_EXCLUDE_OPTION, ArticlesParser.KEY_POST_CONTENT);

        startDownloadProcess(builder.build().toString(), successListener);
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
                        String errorData = "";
                        int statusCode = -1;
                        try {
                            errorData = new String(response.data);
                            statusCode = response.statusCode;
                        } catch (Exception e) {
                        }
                        // Log
                        Log.e(TAG, String.format("Error downloading data, code:%d, msg:%s", statusCode, errorData));
                    }
                });

        mRequestQueue.add(request);
    }

    /**
     * Return page index for provided category.
     *
     * @param category category to return index for.
     * @return page index of data that has already been downloaded.
     */
    public static int getCategoryPageIndex(Category category) {
        return mCategoryPageIndex[category.ordinal()];
    }

    /**
     * Sets current index for given category.
     *
     * @param category category for index.
     * @param index    index of page data.
     */
    public static void setCategoryPageIndex(Category category, int index) {
        mCategoryPageIndex[category.ordinal()] = index;
    }

    /**
     * Increment page index for given category.
     *
     * @param category category.
     * @return page index after increment.
     */
    public static int incrementCatPageIndex(Category category) {
        return mCategoryPageIndex[category.ordinal()] += 1;
    }
}
