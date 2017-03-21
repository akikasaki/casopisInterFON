package com.android.casopisinterfon.interfon.data;


import android.support.annotation.NonNull;

import com.android.casopisinterfon.interfon.model.Article;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsing data retrieved from to server to list of articles.
 */
public class ArticlesParser {

    /**
     * Method for parsing {@link JSONObject} instance to list of {@link Article} objects.
     * @param response response from the server.
     * @return created list containing articles
     */
    public static @NonNull List<Article> parseResponse(JSONObject response) {
        return new ArrayList<>(0); // TODO - finish method RADOVAN
    }
}
