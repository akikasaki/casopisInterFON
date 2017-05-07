package com.android.casopisinterfon.interfon.internet;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsing data retrieved from to server to list of articles.
 */
public class ArticlesParser {
    private static final String TAG = ArticlesParser.class.getSimpleName();

    // Key for array of articles
    private static final String KEY_POSTS = "posts";
    // Keys for article fields
    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_URL = "url";
    private static final String KEY_POST_TITLE = "title";
    static final String KEY_POST_CONTENT = "content";
    private static final String KEY_POST_DATE_C = "date";
    static final String KEY_POST_CATEGORIES = "categories";
    static final String KEY_CUSTOM_FIELDS = "custom_fields";
    static final String KEY_THUMBNAIL_IMAGES = "thumbnail_images";
    static final String KEY_AUTHOR = "author";
    static final String KEY_TAGS = "tags";
    static final String KEY_COMMENTS = "comments";

    /**
     * Method for parsing {@link JSONObject} instance to list of {@link Article} objects.
     *
     * @param response response from the server containing all article categories.
     * @return newly created list containing articles
     */
    @NonNull
    List<Article> parseAll(JSONObject response) {
        List<Article> articleList = new ArrayList<>();
        try {
            JSONArray articleArray = response.getJSONArray(KEY_POSTS);
            int size = articleArray.length();
            // Parse one article
            for (int i = 0; i < size; i++) {
                Article a = parseArticle(articleArray.getJSONObject(i));
                if (a != null)
                    articleList.add(a);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse articles: " + e.getLocalizedMessage(), e);
        }

        return articleList;
    }

    /**
     * Method for parsing json data into {@link Article} object.
     *
     * @param jsonObject json object containing article data.
     * @return newly created article object with data from json object.
     * @throws IllegalArgumentException
     */
    @Nullable
    public Article parseArticle(JSONObject jsonObject) throws IllegalArgumentException {
        if (jsonObject == null)
            throw new IllegalArgumentException("Cannot parse null as Article object.");
        Article a;
        try {
//            a = new Article();
            long _id = jsonObject.getLong(KEY_POST_ID);
            String url = jsonObject.getString(KEY_POST_URL);
            String title = jsonObject.getString(KEY_POST_TITLE);
            // Parse content
            String description;
            StringBuilder builder = new StringBuilder();
            try {
                builder = new StringBuilder(jsonObject.getString(KEY_POST_CONTENT));
                description = builder.substring(0, builder.indexOf("<span id=\"post-ratings"));
            } catch (StringIndexOutOfBoundsException e) {
                Log.w(TAG, "No post ratings.");
                description = builder.toString();
            } catch (JSONException e) {
                Log.w(TAG, "No post description.");
                description = "";
            }
            String date = jsonObject.getString(KEY_POST_DATE_C);

            List<Category> categories = null;
            try {
                categories = parseCategories(jsonObject.getJSONArray(KEY_POST_CATEGORIES));
            } catch (JSONException e) {
                Log.w(TAG, "No categories found.");
            }

            // TODO - add the code to chose the right image to display
            String picUrl = "";
            try {
                picUrl = jsonObject.getJSONObject(KEY_THUMBNAIL_IMAGES).getJSONObject("large").getString("url");
            } catch (JSONException e) {
                Log.w(TAG, "No images found.");
            }
            a = new Article(_id, title, description, date, picUrl, url);
            a.setArticleCategories(categories);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse article: " + jsonObject.toString(), e);
            a = null;
        }

        return a;
    }

    /**
     * Method for parsing article categories.
     *
     * @param array json array containing category data.
     * @return vector of {@link Category} objects.
     */
    private List<Category> parseCategories(JSONArray array) {
        int size = array.length();
        List<Category> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                list.add(Category.getCategoryById(object.getInt("id")));
            } catch (JSONException e) {
                Log.e(TAG, "Failed to parse category.", e);
            }
        }
        return list;
    }
}
