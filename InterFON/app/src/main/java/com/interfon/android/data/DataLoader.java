package com.interfon.android.data;

import android.content.Context;
import com.interfon.android.model.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

public class DataLoader {

    private String ret;
    private Gson mGson;

    Context context;
    public DataLoader(Context context) {
        this.context=context;
    }

    /**
     * Method for reading saved article data
     *
     * @return List of bookmarked Articles
     */
    public List<Article> readData(String file) {
        ret = "";
        FileRead readArticles = new FileRead();
        ret= readArticles.readFile(context,file);

        mGson = new Gson();
        Type type = new TypeToken<List<Article>>() {
        }.getType();
        List<Article> fromJson = mGson.fromJson(ret, type);
        return fromJson;
    }

    /**
     * Method for reading saved article id's
     * @param file from what file we are reading the id's
     * @return list of bookmarked id's
     */
    public List<Long> readId(String file){
        ret = "";
        FileRead readIds = new FileRead();
        ret= readIds.readFile(context,file);

        mGson = new Gson();
        Type type = new TypeToken<List<Long>>() {
        }.getType();
        List<Long> fromJson = mGson.fromJson(ret, type);
        return fromJson;
    }

    /**
     * Checks if the passed article is within a certain List
     *
     * @param articleId the article we are checking
     * @param bookmarks     The List we are checking in
     * @return true if the article is in the List
     */
    public boolean isBookmarked(long articleId, List<Long> bookmarks) {
        if (bookmarks == null) return false;

        Iterator<Long> iter = bookmarks.iterator();

        while (iter.hasNext()) {
            Long a = iter.next();

            if (articleId == a) {
                return true;
            }
        }
        return false;
    }


}

